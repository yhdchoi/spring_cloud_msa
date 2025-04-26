package com.yhdc.store_service.transaction;

import com.yhdc.store_service.transaction.object.StoreCreateRecord;
import com.yhdc.store_service.transaction.object.StoreDto;
import com.yhdc.store_service.transaction.object.StorePatchRecord;
import com.yhdc.store_service.transaction.object.StorePutRecord;
import org.springframework.http.ResponseEntity;

public interface StoreService {

    ResponseEntity<?> createStore(StoreCreateRecord storeCreateRecord);

    ResponseEntity<StoreDto> detailStore(String storeId);

    ResponseEntity<?> searchStorePage(String keyword, String pageNo, String pageSize, String sortBy, String sortOrder);

    ResponseEntity<?> updateStore(StorePutRecord storePutRecord);

    ResponseEntity<?> patchStoreStatus(StorePatchRecord storePatchRecord);

    ResponseEntity<?> deleteStore(String storeId);

}
