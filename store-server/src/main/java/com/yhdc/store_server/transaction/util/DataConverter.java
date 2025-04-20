package com.yhdc.store_server.transaction.util;

import com.yhdc.store_server.transaction.Store;
import com.yhdc.store_server.transaction.object.StoreDto;
import com.yhdc.store_server.transaction.type.StoreStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

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
