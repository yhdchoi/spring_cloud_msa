package com.yhdc.product_server.transaction;

import com.yhdc.product_server.object.ProductCreateRecord;
import com.yhdc.product_server.object.ProductPutRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {


    ResponseEntity<?> createProduct(ProductCreateRecord productCreateRecord, MultipartFile[] fileArray);

    ResponseEntity<?> detailProduct(String productId);

    ResponseEntity<?> listStoreProducts(String storeId,
                                        String pageNo,
                                        String pageSize,
                                        String sortBy,
                                        String sortOrder);

    ResponseEntity<?> searchStoreProducts(String storeId,
                                          String keyword,
                                          String pageNo,
                                          String pageSize,
                                          String sortBy,
                                          String sortOrder);


    ResponseEntity<?> searchProducts(String keyword,
                                     String pageNo,
                                     String pageSize,
                                     String sortBy,
                                     String sortOrder);

    ResponseEntity<?> updateProduct(ProductPutRecord productPutRecord);



    ResponseEntity<?> deleteProduct(String productId);
}
