package com.yhdc.product_server.transaction;

import com.yhdc.product_server.object.ImageInfoListDto;
import com.yhdc.product_server.object.ProductCreateRecord;
import com.yhdc.product_server.object.ProductImageDeleteRecord;
import com.yhdc.product_server.object.ProductPutRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ProductRestController {

    private final ProductServiceImpl productService;
    private final ProductImageRestClient productImageRestClient;


    /**
     * CREATE PRODUCT
     *
     * @param productCreateRecord
     * @param fileArray
     * @apiNote
     */
    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestPart ProductCreateRecord productCreateRecord,
                                           @RequestPart MultipartFile[] fileArray) {
        return productService.createProduct(productCreateRecord, fileArray);
    }


    /**
     * SAVE PRODUCT IMAGE
     *
     * @param productId
     * @param fileArray
     * @apiNote
     */
    @PostMapping("/save/image")
    public ResponseEntity<?> saveImage(@RequestPart(value = "productId") String productId,
                                       @RequestPart(value = "fileArray") MultipartFile[] fileArray) {
        return productImageRestClient.saveProductImages(productId, fileArray);
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
     * DOWNLOAD A PRODUCT IMAGE
     *
     * @param productId
     * @param fileName
     * @apiNote
     */
    @GetMapping("/download/image")
    public ResponseEntity<Resource> downloadImage(@RequestParam String productId,
                                                  @RequestParam String fileName) {
        return productImageRestClient.downloadProductImage(productId, fileName);
    }

    /**
     * DOWNLOAD ALL IMAGES FOR A PRODUCT
     *
     * @param productId
     * @apiNote
     */
    @GetMapping("/download/image/all")
    public ResponseEntity<ImageInfoListDto> downloadImageAll(@RequestParam String productId) {
        return productImageRestClient.loadProductImagesInfo(productId);
    }


    /**
     * DOWNLOAD PRODUCT IMAGES ZIP
     *
     * @param productId
     * @apiNote
     */
    @GetMapping("/download/image/zip")
    public ResponseEntity<Resource> downloadProductImage(@RequestParam String productId) {
        return productImageRestClient.downloadProductImageZip(productId);
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
//    @GetMapping("/list/str")
    public ResponseEntity<?> listStoreProduct(@RequestParam String storeId,
                                              @RequestParam String pageNo,
                                              @RequestParam String pageSize,
                                              @RequestParam String sortBy,
                                              @RequestParam String sortOrder) {
        return productService.listStoreProducts(storeId, pageNo, pageSize, sortBy, sortOrder);
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
    @GetMapping("/search/str")
    public ResponseEntity<?> searchStoreProduct(@RequestParam String storeId,
                                                @RequestParam String keyword,
                                                @RequestParam String pageNo,
                                                @RequestParam String pageSize,
                                                @RequestParam String sortBy,
                                                @RequestParam String sortOrder) {
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
    public ResponseEntity<?> searchStoreProduct(@RequestParam String keyword,
                                                @RequestParam String pageNo,
                                                @RequestParam String pageSize,
                                                @RequestParam String sortBy,
                                                @RequestParam String sortOrder) {
        return productService.searchProducts(keyword, pageNo, pageSize, sortBy, sortOrder);
    }

    /**
     * UPDATE PRODUCT DETAIL
     *
     * @param productPutRecord
     * @apiNote Update ONLY the product detail without the images
     */
    @PutMapping("/put")
    public ResponseEntity<?> updateProduct(@RequestBody ProductPutRecord productPutRecord) {
        return productService.updateProduct(productPutRecord);
    }


    /**
     * PATCH PRODUCT IMAGE
     *
     * @param productId
     * @param fileArray
     */
    @PatchMapping("/patch/image")
    public ResponseEntity<?> patchProductImage(@RequestParam(value = "productId", required = true) String productId,
                                               @RequestParam(value = "fileArray", required = true) MultipartFile[] fileArray) {
        return productImageRestClient.patchProductImages(productId, fileArray);
    }


    /**
     * DELETE PRODUCT IMAGE(S)
     *
     * @param productImageDeleteRecord
     * @apiNote
     */
    @DeleteMapping("/delete/image")
    public ResponseEntity<?> deleteSelectedProductImages(@RequestBody ProductImageDeleteRecord productImageDeleteRecord) {
        return productImageRestClient.deleteSelectedProductImages(productImageDeleteRecord.productId(),
                productImageDeleteRecord.fileNameList());
    }


    /**
     * DELETE PRODUCT
     *
     * @param productId
     * @apiNote Delete product data permanently
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProduct(@RequestParam String productId) {
        return productService.deleteProduct(productId);
    }


}
