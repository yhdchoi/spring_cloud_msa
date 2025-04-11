package com.yhdc.store_server.util;

import com.yhdc.store_server.object.StoreDto;
import com.yhdc.store_server.transaction.Store;
import com.yhdc.store_server.type.StoreStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@Slf4j
@Service
public class DataConverter {

    /**
     * FORMAT DATE TO FORMATTED STRING
     *
     * @param localDateTime
     */
    public String dateConverter(LocalDateTime localDateTime) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(localDateTime);
    }


    /**
     * CONVERT STORE OBJECT TO DTO
     *
     * @param store
     */
    public StoreDto convertStoreToDto(Store store) {
        try {
            StoreDto storeDto = new StoreDto();
            storeDto.setStoreId(store.getId().toString());
            storeDto.setName(store.getName());
            storeDto.setDescription(store.getDescription());
            storeDto.setStatus(store.getStatus() != null ? store.getStatus().selection() : StoreStatus.SUSPENDED.selection());
            return storeDto;

        } catch (Exception e) {
            log.error("location: {}, error: {}", "Error converting store to DTO...", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
