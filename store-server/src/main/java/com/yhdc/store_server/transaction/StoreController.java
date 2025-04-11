package com.yhdc.store_server.transaction;

import com.yhdc.store_server.object.StoreCreateRecord;
import com.yhdc.store_server.object.StorePatchRecord;
import com.yhdc.store_server.object.StorePutRecord;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class StoreController {

    private final StoreServiceImpl storeService;

    /**
     * CREATE STORE
     *
     * @param storeCreateRecord
     * @apiNote
     */
    @PostMapping("/create")
    public ResponseEntity<?> createStore(@RequestBody StoreCreateRecord storeCreateRecord) {
        return storeService.createStore(storeCreateRecord);
    }

    /**
     * STORE DETAIL
     *
     * @param storeId
     * @apiNote
     */
    @GetMapping("/detail")
    public ResponseEntity<?> detailStore(@RequestParam String storeId) {
        return storeService.detailStore(storeId);
    }


    /**
     * SEARCH STORE
     *
     * @param keyword
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param sortOrder
     * @apiNote
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchStore(@RequestParam @NotBlank(message = "Keyword is mandatory field") String keyword,
                                         @RequestParam(required = false, defaultValue = "0", value = "pageNo") String pageNo,
                                         @RequestParam(required = false, defaultValue = "10", value = "pageSize") String pageSize,
                                         @RequestParam(required = false, defaultValue = "DESC", value = "pageBy") String sortBy,
                                         @RequestParam(required = false, defaultValue = "created_at", value = "pageOrder") String sortOrder) {
        return storeService.searchStorePage(keyword, pageNo, pageSize, sortBy, sortOrder);
    }


    /**
     * UPDATE STORE
     *
     * @param storePutRecord
     * @apiNote PUT request
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateStore(@RequestBody StorePutRecord storePutRecord) {
        return storeService.updateStore(storePutRecord);
    }


    /**
     * UPDATE STORE STATUS
     *
     * @param storePatchRecord
     * @apiNote
     */
    @PatchMapping("/patch")
    public ResponseEntity<?> patchStore(@RequestBody StorePatchRecord storePatchRecord) {
        return storeService.patchStoreStatus(storePatchRecord);
    }

    /**
     * DELETE STORE
     *
     * @param storeId
     * @apiNote
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteStore(@RequestParam String storeId) {
        return storeService.deleteStore(storeId);
    }

}
