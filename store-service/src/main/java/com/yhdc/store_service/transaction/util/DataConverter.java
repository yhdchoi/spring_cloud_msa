package com.yhdc.store_service.transaction.util;

import com.yhdc.store_service.transaction.data.Store;
import com.yhdc.store_service.transaction.object.StoreDto;
import com.yhdc.store_service.transaction.type.StoreStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DataConverter {

    /**
     * CONVERT STORE OBJECT TO DTO
     *
     * @param store
     */
    public StoreDto convertStoreToDto(Store store) {
        try {
            StoreDto storeDto = new StoreDto();
            storeDto.setStoreId(String.valueOf(store.getId()));
            storeDto.setName(store.getName());
            storeDto.setDescription(store.getDescription());
            storeDto.setStatus(store.getStatus() != null ? store.getStatus() : StoreStatus.SUSPENDED.selection());
            return storeDto;

        } catch (Exception e) {
            log.error("location: {}, error: {}", "Error converting store to DTO...", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
