package com.yhdc.store_service.transaction;

import com.yhdc.store_service.transaction.object.StoreCreateRecord;
import com.yhdc.store_service.transaction.object.StorePatchRecord;
import com.yhdc.store_service.transaction.object.StorePutRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class StoreApiController {

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
    public ResponseEntity<?> searchStore(@RequestParam(required = false, defaultValue = "*", value = "keyword") String keyword,
                                         @RequestParam(defaultValue = "0", value = "pageNo") String pageNo,
                                         @RequestParam(defaultValue = "10", value = "pageSize") String pageSize,
                                         @RequestParam(defaultValue = "DESC", value = "pageBy") String sortBy,
                                         @RequestParam(defaultValue = "createdAt", value = "pageOrder") String sortOrder) {
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
