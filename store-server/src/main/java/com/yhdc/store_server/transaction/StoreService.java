package com.yhdc.store_server.transaction;

import com.yhdc.store_server.object.StoreCreateRecord;
import com.yhdc.store_server.object.StorePatchRecord;
import com.yhdc.store_server.object.StorePutRecord;
import org.springframework.http.ResponseEntity;

public interface StoreService {

    ResponseEntity<?> createStore(StoreCreateRecord storeCreateRecord);


    ResponseEntity<?> searchStorePage(String keyword, String pageNo, String pageSize, String sortBy, String sortOrder);


    ResponseEntity<?> updateStore(StorePutRecord storePutRecord);


    ResponseEntity<?> patchStoreStatus(StorePatchRecord storePatchRecord);


    ResponseEntity<?> deleteStore(String storeId);

}
