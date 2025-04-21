package com.yhdc.store_server.transaction;

import com.yhdc.store_server.transaction.data.DatabaseSequenceGeneratorService;
import com.yhdc.store_server.transaction.data.Store;
import com.yhdc.store_server.transaction.data.StoreRepository;
import com.yhdc.store_server.transaction.object.StoreCreateRecord;
import com.yhdc.store_server.transaction.object.StoreDto;
import com.yhdc.store_server.transaction.object.StorePatchRecord;
import com.yhdc.store_server.transaction.object.StorePutRecord;
import com.yhdc.store_server.transaction.type.StoreStatus;
import com.yhdc.store_server.transaction.util.DataConverter;
import com.yhdc.store_server.transaction.util.PageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.yhdc.store_server.transaction.type.Constants.STORE_ACTIVE;
import static com.yhdc.store_server.transaction.type.Constants.STORE_INACTIVE;


@Slf4j
@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final DatabaseSequenceGeneratorService databaseSequenceGeneratorService;
    private final DataConverter dataConverter;
    private final PageProducer pageProducer;

    /**
     * CREATE STORE
     *
     * @param storeCreateRecord
     * @implNote Create new store
     */
    @Override
    @Transactional
    public ResponseEntity<?> createStore(StoreCreateRecord storeCreateRecord) {

        try {
            Store store = new Store();
            store.setId(databaseSequenceGeneratorService.generateSequence(Store.SEQUENCE_NAME));
            store.setSellerId(storeCreateRecord.userId());
            store.setName(storeCreateRecord.name());
            store.setDescription(storeCreateRecord.description());
            store.setStatus(StoreStatus.ACTIVE.selection());
            final Store newStore = storeRepository.save(store);
            return new ResponseEntity<>(newStore, HttpStatus.CREATED);

        } catch (Exception e) {
            log.error("Unable to create store!!!", e);
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
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<StoreDto> detailStore(String storeId) {
        try {
            Optional<Store> store = storeRepository.findById(Long.valueOf(storeId));
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
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> searchStorePage(String keyword, String pageNo, String pageSize, String sortBy, String sortOrder) {
        try {
            Pageable pageable = pageProducer.getPageable(pageNo, pageSize, sortBy, sortOrder);
            Page<Store> storePage = null;
            if (keyword.equals("*")) {
                storePage = storeRepository.findAll(pageable);
            } else {
                storePage = storeRepository.findAllByNameContainingIgnoreCase(keyword, pageable);
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
     */
    @Override
    @Transactional
    public ResponseEntity<?> updateStore(StorePutRecord storePutRecord) {

        try {
            Optional<Store> storeOptional = storeRepository.findById(Long.valueOf(storePutRecord.id()));
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
    @Override
    @Transactional
    public ResponseEntity<?> patchStoreStatus(StorePatchRecord storePatchRecord) {

        try {
            Optional<Store> storeOptional = storeRepository.findById(Long.valueOf(storePatchRecord.storeId()));
            if (storeOptional.isPresent()) {
                Store store = storeOptional.get();

                switch (storePatchRecord.status()) {
                    case STORE_ACTIVE:
                        store.setStatus(StoreStatus.ACTIVE.selection());
                        break;
                    case STORE_INACTIVE:
                        store.setStatus(StoreStatus.INACTIVE.selection());
                        break;
                    default:
                        store.setStatus(StoreStatus.SUSPENDED.selection());
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
    @Override
    @Transactional
    public ResponseEntity<?> deleteStore(String storeId) {
        try {
            storeRepository.deleteById(Long.valueOf(storeId));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Unable to delete store", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
