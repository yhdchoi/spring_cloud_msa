package com.yhdc.product_server.transaction;


import com.yhdc.product_server.object.CommonResponseRecord;
import com.yhdc.product_server.object.ProductCreateRecord;
import com.yhdc.product_server.object.ProductDto;
import com.yhdc.product_server.object.ProductPutRecord;
import com.yhdc.product_server.type.ProductStatus;
import com.yhdc.product_server.util.DataConverter;
import com.yhdc.product_server.util.PageProducer;
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

import static com.yhdc.product_server.type.Constants.PRODUCT_ACTIVE;
import static com.yhdc.product_server.type.Constants.PRODUCT_INACTIVE;


@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRestClient productImageRestClient;
    private final DataConverter dataConverter;
    private final PageProducer pageProducer;


    /**
     * PRODUCT CREATION
     *
     * @param productCreateRecord
     * @implNote Saves a new product
     * @implSpec
     */
    @Transactional
    public ResponseEntity<?> createProduct(ProductCreateRecord productCreateRecord) {

        try {
            Product product = new Product();
            product.setUserId(UUID.fromString(productCreateRecord.userId()));
            product.setStoreId(UUID.fromString(productCreateRecord.storeId()));
            product.setName(productCreateRecord.name());
            product.setDescription(productCreateRecord.description());
            product.setPrice(productCreateRecord.price());
            switch (productCreateRecord.status()) {
                case PRODUCT_ACTIVE:
                    product.setStatus(ProductStatus.ACTIVE);
                    break;
                case PRODUCT_INACTIVE:
                    product.setStatus(ProductStatus.INACTIVE);
                    break;
                default:
                    product.setStatus(ProductStatus.SUSPENDED);
                    break;
            }
            product.setStock(productCreateRecord.stock());
            Product newProduct = productRepository.save(product);

            final String productId = newProduct.getId().toString();
            log.info("Product saved successfully!!!");
            return new ResponseEntity<>(productId, HttpStatus.CREATED);

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
    @Transactional(readOnly = true)
    public ResponseEntity<?> detailProduct(String productId) {
        try {
            Optional<Product> productOptional = productRepository.findById(UUID.fromString(productId));
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                ProductDto productDto = dataConverter.convertProductToDto(product);
                return new ResponseEntity<>(productDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>("Unable to load data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * SELLER'S PRODUCT PAGE
     *
     * @param userId
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param sortOrder
     * @implNote For seller to browse products
     */
    @Transactional(readOnly = true)
    public ResponseEntity<?> pageSellerProducts(String userId,
                                                String pageNo,
                                                String pageSize,
                                                String sortBy,
                                                String sortOrder) {
        try {
            final Pageable pageable = pageProducer.getPageable(pageNo, pageSize, sortBy, sortOrder);
            final Page<Product> productPage = productRepository.findAllByUserId(UUID.fromString(userId), pageable);
            final Page<ProductDto> productDtoList = productPage.map(dataConverter::convertProductToDto);
            return new ResponseEntity<>(productDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
    @Transactional(readOnly = true)
    public ResponseEntity<?> pageStoreProducts(String storeId,
                                               String pageNo,
                                               String pageSize,
                                               String sortBy,
                                               String sortOrder) {
        try {
            final Pageable pageable = pageProducer.getPageable(pageNo, pageSize, sortBy, sortOrder);
            final Page<Product> productPage = productRepository.findAllByStoreId(UUID.fromString(storeId), pageable);
            final Page<ProductDto> productDtoList = productPage.map(dataConverter::convertProductToDto);
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
    @Transactional(readOnly = true)
    public ResponseEntity<?> searchStoreProducts(String storeId,
                                                 String keyword,
                                                 String pageNo,
                                                 String pageSize,
                                                 String sortBy,
                                                 String sortOrder) {
        try {
            final Pageable pageable = pageProducer.getPageable(pageNo, pageSize, sortBy, sortOrder);
            Page<Product> productPage = null;
            if (keyword.equals("*")) {
                productPage = productRepository.findAllByStoreId(UUID.fromString(storeId), pageable);
            } else {
                productPage = productRepository.findAllByStoreIdAndKeyword(UUID.fromString(storeId), keyword, pageable);
            }
            if (productPage.getTotalElements() > 0) {
                Page<ProductDto> productDtoList = productPage.map(dataConverter::convertProductToDto);
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
    @Transactional(readOnly = true)
    public ResponseEntity<?> searchAllProducts(String keyword,
                                               String pageNo,
                                               String pageSize,
                                               String sortBy,
                                               String sortOrder) {
        try {
            final Pageable pageable = pageProducer.getPageable(pageNo, pageSize, sortBy, sortOrder);
            Page<Product> productPage = null;
            if (keyword.equals("*")) {
                productPage = productRepository.findAll(pageable);
            } else {
                productPage = productRepository.findAllByKeyword(keyword, pageable);
            }

            if (productPage.getTotalElements() > 0) {
                Page<ProductDto> productDtoList = productPage.map(dataConverter::convertProductToDto);
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
    @Transactional
    public ResponseEntity<?> updateProduct(ProductPutRecord productPutRecord) {

        try {
            Optional<Product> productOptional = productRepository.findById(UUID.fromString(productPutRecord.productId()));
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                product.setName(productPutRecord.name());
                product.setDescription(productPutRecord.description());
                product.setPrice(productPutRecord.price());
                product.setStock(productPutRecord.stock());
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
    @Transactional
    public ResponseEntity<?> deleteProduct(String productId) {
        try {
            final ResponseEntity<CommonResponseRecord> imageResponse = productImageRestClient.deleteProductImages(productId);
            if (imageResponse.getStatusCode() == HttpStatus.OK) {
                productRepository.deleteById(UUID.fromString(productId));
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Image directory not removed", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
