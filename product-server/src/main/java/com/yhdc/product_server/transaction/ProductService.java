package com.yhdc.product_server.transaction;

import com.yhdc.product_server.transaction.object.ProductCreateRecord;
import com.yhdc.product_server.transaction.object.ProductPutRecord;
import org.springframework.http.ResponseEntity;

public interface ProductService {


    ResponseEntity<?> createProduct(ProductCreateRecord productCreateRecord);

    ResponseEntity<?> detailProduct(String productId);

    ResponseEntity<?> pageStoreProducts(String storeId,
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


    ResponseEntity<?> searchAllProducts(String keyword,
                                        String pageNo,
                                        String pageSize,
                                        String sortBy,
                                        String sortOrder);

    ResponseEntity<?> updateProduct(ProductPutRecord productPutRecord);


    ResponseEntity<?> deleteProduct(String productId);
}
