package com.yhdc.product_server.transaction;


import com.yhdc.product_server.transaction.object.ProductCreateRecord;
import com.yhdc.product_server.transaction.object.ProductDto;
import com.yhdc.product_server.transaction.object.ProductPutRecord;
import com.yhdc.product_server.transaction.rest_client.FileRestClientService;
import com.yhdc.product_server.transaction.type.ProductStatus;
import com.yhdc.product_server.transaction.util.DataProcessor;
import com.yhdc.product_server.transaction.util.PageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static com.yhdc.product_server.transaction.type.Constants.PRODUCT_ACTIVE;
import static com.yhdc.product_server.transaction.type.Constants.PRODUCT_INACTIVE;


@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final DatabaseSequenceGeneratorService databaseSequenceGeneratorService;
    private final FileRestClientService fileRestClientService;
    private final DataProcessor dataProcessor;
    private final PageProducer pageProducer;


    /**
     * PRODUCT CREATION
     *
     * @param productCreateRecord
     * @implNote Saves a new product
     * @implSpec
     */
    @Override
    @Transactional
    public ResponseEntity<?> createProduct(ProductCreateRecord productCreateRecord) {
        try {
            Product product = new Product();
            product.setId(databaseSequenceGeneratorService.generateSequence(Product.SEQUENCE_NAME));
            product.setUserId(productCreateRecord.userId());
            product.setStoreId(productCreateRecord.storeId());
            product.setName(productCreateRecord.name());
            product.setDescription(productCreateRecord.description());
            product.setPrice(productCreateRecord.price());

            final String skuCode = UUID.randomUUID().toString();
            product.setSkuCode(skuCode);

            switch (productCreateRecord.status()) {
                case PRODUCT_ACTIVE:
                    product.setStatus(ProductStatus.ACTIVE.selection());
                    break;
                case PRODUCT_INACTIVE:
                    product.setStatus(ProductStatus.INACTIVE.selection());
                    break;
                default:
                    product.setStatus(ProductStatus.SUSPENDED.selection());
                    break;
            }
            final Product newProduct = productRepository.save(product);
            final String productId = String.valueOf(newProduct.getId());

            // Create new inventory through Feign Client
            final boolean inventoryCreated = dataProcessor.inventoryCreator(productId, skuCode, productCreateRecord.quantity());

            if (inventoryCreated) {
                log.info("Product saved successfully!!!");
                return new ResponseEntity<>(productId, HttpStatus.CREATED);
            } else {
                log.error("Failed to save Inventory!!!");
                return new ResponseEntity<>(productId, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error("Unable to save product: {}", e.getMessage());
            return new ResponseEntity<>("Unable to save product!!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * PRODUCT DETAIL
     *
     * @param productId
     * @implNote
     * @implSpec
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> detailProduct(String productId) {
        try {
            Optional<Product> productOptional = productRepository.findById(Long.valueOf(productId));
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                ProductDto productDto = dataProcessor.convertProductToDto(product);
                return new ResponseEntity<>(productDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Unable to load data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * STORE PRODUCT PAGE
     *
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param sortOrder
     * @implNote Lists all products under a store with pageable defined by client-end
     * @implSpec
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> pageStoreProducts(String storeId,
                                               String pageNo,
                                               String pageSize,
                                               String sortBy,
                                               String sortOrder) {
        try {
            final Pageable pageable = pageProducer.getPageable(pageNo, pageSize, sortBy, sortOrder);
            final Page<Product> productPage = productRepository.findAllByStoreId(storeId, pageable);
            final Page<ProductDto> productDtoList = productPage.map(dataProcessor::convertProductToDto);
            return new ResponseEntity<>(productDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * SEARCH PRODUCTS FROM A STORE
     *
     * @param storeId
     * @param keyword
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param sortOrder
     * @implNote Search all products from a store with pageable defined by client-end
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> searchStoreProducts(String storeId,
                                                 String keyword,
                                                 String pageNo,
                                                 String pageSize,
                                                 String sortBy,
                                                 String sortOrder) {
        try {
            final Pageable pageable = pageProducer.getPageable(pageNo, pageSize, sortBy, sortOrder);
            Page<Product> productPage;
            if (keyword.equals("*")) {
                productPage = productRepository.findAllByStoreId(storeId, pageable);
            } else {
                productPage = productRepository.findAllByStoreIdAndNameContainingIgnoreCase(storeId, keyword, pageable);
            }
            if (productPage.getTotalElements() > 0) {
                Page<ProductDto> productDtoList = productPage.map(dataProcessor::convertProductToDto);
                return new ResponseEntity<>(productDtoList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * SEARCH ALL PRODUCTS
     *
     * @param keyword
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param sortOrder
     * @implNote Search by keyword with pageable defined by client-end
     * @implSpec Pageable
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> searchAllProducts(String keyword,
                                               String pageNo,
                                               String pageSize,
                                               String sortBy,
                                               String sortOrder) {
        try {
            final Pageable pageable = pageProducer.getPageable(pageNo, pageSize, sortBy, sortOrder);
            Page<Product> productPage;
            if (keyword.equals("*")) {
                productPage = productRepository.findAll(pageable);
            } else {
                productPage = productRepository.findAllByNameContainingIgnoreCase(keyword, pageable);
            }

            if (productPage.getTotalElements() > 0) {
                Page<ProductDto> productDtoList = productPage.map(dataProcessor::convertProductToDto);
                return new ResponseEntity<>(productDtoList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * UPDATE PRODUCT DETAILS
     *
     * @param productPutRecord
     * @implNote For updating images, use separate service(api)
     * @implSpec
     */
    @Override
    @Transactional
    public ResponseEntity<?> updateProduct(ProductPutRecord productPutRecord) {
        try {
            Optional<Product> productOptional = productRepository.findById(Long.valueOf(productPutRecord.productId()));
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                product.setName(productPutRecord.name());
                product.setDescription(productPutRecord.description());
                product.setPrice(productPutRecord.price());
                product.setSkuCode(productPutRecord.skuCode());
                productRepository.save(product);
                return new ResponseEntity<>(HttpStatus.OK);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * PERMANENT PRODUCT DELETION
     *
     * @param productId
     * @implNote Deletes the product image directory and all its contents along with the product data
     * @implSpec
     */
    @Override
    @Transactional
    public ResponseEntity<?> deleteProduct(String productId) {
        try {
            final ResponseEntity<?> clientResponse = fileRestClientService.deleteProductImages(productId);
            if (clientResponse.getStatusCode() == HttpStatus.OK) {
                productRepository.deleteById(Long.valueOf(productId));
            }
            return clientResponse;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
