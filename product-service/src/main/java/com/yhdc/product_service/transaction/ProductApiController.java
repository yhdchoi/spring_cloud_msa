package com.yhdc.product_service.transaction;

import com.yhdc.product_service.transaction.object.ProductCreateRecord;
import com.yhdc.product_service.transaction.object.ProductPutRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ProductApiController {

    private final ProductServiceImpl productService;


    /**
     * CREATE PRODUCT
     *
     * @param productCreateRecord
     * @apiNote
     */
    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductCreateRecord productCreateRecord) {
        return productService.createProduct(productCreateRecord);
    }


    /**
     * GET PRODUCT DETAIL
     *
     * @param productId
     * @apiNote
     */
    @GetMapping("/detail")
    public ResponseEntity<?> getProductDetail(@RequestParam(value = "productId") String productId) {
        return productService.detailProduct(productId);
    }


    /**
     * LIST ALL PRODUCTS IN THE STORE
     *
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param sortOrder
     * @apiNote
     */
    @GetMapping("/list/store")
    public ResponseEntity<?> listStoreProducts(@RequestParam(value = "storeId") String storeId,
                                               @RequestParam(defaultValue = "0", value = "pageNo") String pageNo,
                                               @RequestParam(defaultValue = "10", value = "pageSize") String pageSize,
                                               @RequestParam(defaultValue = "DESC", value = "pageBy") String sortBy,
                                               @RequestParam(defaultValue = "createdAt", value = "pageOrder") String sortOrder) {
        return productService.pageStoreProducts(storeId, pageNo, pageSize, sortBy, sortOrder);
    }


    /**
     * SEARCH ALL PRODUCTS IN THE STORE
     *
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param sortOrder
     * @apiNote
     */
    @GetMapping("/search/store")
    public ResponseEntity<?> searchStoreProducts(@RequestParam(value = "storeId") String storeId,
                                                 @RequestParam(required = false, defaultValue = "*", value = "keyword") String keyword,
                                                 @RequestParam(defaultValue = "0", value = "pageNo") String pageNo,
                                                 @RequestParam(defaultValue = "10", value = "pageSize") String pageSize,
                                                 @RequestParam(defaultValue = "DESC", value = "pageBy") String sortBy,
                                                 @RequestParam(defaultValue = "createdAt", value = "pageOrder") String sortOrder) {
        return productService.searchStoreProducts(storeId, keyword, pageNo, pageSize, sortBy, sortOrder);
    }


    /**
     * SEARCH ALL PRODUCTS IN THE DB
     *
     * @param keyword
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param sortOrder
     * @apiNote Search all products in the
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchAllProducts(@RequestParam(required = false, defaultValue = "*", value = "keyword") String keyword,
                                               @RequestParam(defaultValue = "0", value = "pageNo") String pageNo,
                                               @RequestParam(defaultValue = "10", value = "pageSize") String pageSize,
                                               @RequestParam(defaultValue = "DESC", value = "pageBy") String sortBy,
                                               @RequestParam(defaultValue = "createdAt", value = "pageOrder") String sortOrder) {
        return productService.searchAllProducts(keyword, pageNo, pageSize, sortBy, sortOrder);
    }

    /**
     * UPDATE PRODUCT DETAIL
     *
     * @param productPutRecord
     * @apiNote Update ONLY the product detail without the images
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody ProductPutRecord productPutRecord) {
        return productService.updateProduct(productPutRecord);
    }


    /**
     * DELETE PRODUCT
     *
     * @param productId
     * @apiNote Delete product data permanently
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProduct(@RequestParam(value = "productId") String productId) {
        return productService.deleteProduct(productId);
    }


}
