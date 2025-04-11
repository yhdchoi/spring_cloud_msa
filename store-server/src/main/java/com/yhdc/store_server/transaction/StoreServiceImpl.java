package com.yhdc.store_server.transaction;

import com.yhdc.store_server.object.StoreCreateRecord;
import com.yhdc.store_server.object.StoreDto;
import com.yhdc.store_server.object.StorePatchRecord;
import com.yhdc.store_server.object.StorePutRecord;
import com.yhdc.store_server.type.StoreStatus;
import com.yhdc.store_server.util.DataConverter;
import com.yhdc.store_server.util.PageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static com.yhdc.store_server.type.Constants.*;


@Slf4j
@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final DataConverter dataConverter;
    private final PageProducer pageProducer;

    /**
     * CREATE STORE
     *
     * @param storeCreateRecord
     * @implNote Create new store
     */
    @Transactional
    public ResponseEntity<?> createStore(StoreCreateRecord storeCreateRecord) {

        try {
            Store store = new Store();
            store.setUserId(storeCreateRecord.userId());
            store.setName(storeCreateRecord.name());
            store.setDescription(storeCreateRecord.description());
            store.setStatus(StoreStatus.ACTIVE);
            Store newStore = storeRepository.save(store);

            newStore.setImagePath(IMAGE_BASE_DIR + "/" + newStore.getId());
            return new ResponseEntity<>(newStore.getId().toString(), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>("Unable to create store",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * STORE DETAIL
     *
     * @param storeId
     * @implNote
     * @implSpec
     */
    @Transactional(readOnly = true)
    public ResponseEntity<StoreDto> detailStore(String storeId) {
        try {
            Optional<Store> store = storeRepository.findById(UUID.fromString(storeId));
            if (store.isPresent()) {
                Store storeData = store.get();
                StoreDto storeDto = dataConverter.convertStoreToDto(storeData);
                return new ResponseEntity<>(storeDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


    /**
     * LIST STORE
     *
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param sortOrder
     * @implNote
     */
    @Transactional(readOnly = true)
    public ResponseEntity<?> searchStorePage(String keyword, String pageNo, String pageSize, String sortBy, String sortOrder) {
        try {
            Pageable pageable = pageProducer.getPageable(pageNo, pageSize, sortBy, sortOrder);
            Page<Store> storePage = null;
            if (keyword == null || keyword.isEmpty()) {
                storePage = storeRepository.findAll(pageable);
            } else {
                storePage = storeRepository.searchStore(keyword, pageable);
            }
//            assert storePage != null;
            Page<StoreDto> userDtoPage = storePage.map(dataConverter::convertStoreToDto);
            return new ResponseEntity<>(userDtoPage, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Unable to find and list content", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * UPDATE STORE STATUS
     *
     * @param storePutRecord
     * @return
     */
    @Transactional
    public ResponseEntity<?> updateStore(StorePutRecord storePutRecord) {

        try {
            Optional<Store> storeOptional = storeRepository.findById(UUID.fromString(storePutRecord.id()));
            if (storeOptional.isPresent()) {
                Store store = storeOptional.get();
                store.setName(storePutRecord.name());
                return new ResponseEntity<>(HttpStatus.OK);

            } else {
                return new ResponseEntity<>("Store not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Unable to update store", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * PATCH STORE STATUS
     *
     * @param storePatchRecord
     * @implNote Admin ONLY
     */
    @Transactional
    public ResponseEntity<?> patchStoreStatus(StorePatchRecord storePatchRecord) {

        try {
            Optional<Store> storeOptional = storeRepository.findById(UUID.fromString(storePatchRecord.storeId()));
            if (storeOptional.isPresent()) {
                Store store = storeOptional.get();

                switch (storePatchRecord.status()) {
                    case STORE_ACTIVE:
                        store.setStatus(StoreStatus.ACTIVE);
                        break;
                    case STORE_INACTIVE:
                        store.setStatus(StoreStatus.INACTIVE);
                        break;
                    default:
                        store.setStatus(StoreStatus.SUSPENDED);
                        break;
                }

                storeRepository.save(store);
                return new ResponseEntity<>(HttpStatus.OK);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>("Unable to change store status", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * DELETE STORE
     *
     * @param storeId
     * @implNote Deletes store
     */
    @Transactional
    public ResponseEntity<?> deleteStore(String storeId) {
        try {
            storeRepository.deleteById(UUID.fromString(storeId));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Unable to delete store", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
