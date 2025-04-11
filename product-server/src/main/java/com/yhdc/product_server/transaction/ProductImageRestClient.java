package com.yhdc.product_server.transaction;

import com.yhdc.product_server.object.CommonResponseRecord;
import com.yhdc.product_server.object.ImageInfoListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.yhdc.product_server.type.Constants.FILE_SERVER_URI;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductImageRestClient {

    private final RestTemplate restTemplate;

    /**
     * SAVE PRODUCT IMAGES
     *
     * @param productId
     * @param fileArray
     */
    public ResponseEntity<CommonResponseRecord> saveProductImages(String productId, MultipartFile[] fileArray) {

        log.info("Product id {}", productId);
        final URI uri = UriComponentsBuilder
                .fromUriString(FILE_SERVER_URI)
                .path("/save/product-image")
                .queryParam("productId", productId)
                .queryParam("fileArray", (Object) fileArray)
                .encode()
                .build()
                .toUri();

        log.info("Uri: {}", uri);
        final ResponseEntity<CommonResponseRecord> response = restTemplate.getForEntity(uri, CommonResponseRecord.class);
        log.info("Response received: {}", response);
        return response;
    }


    /**
     * PATCH PRODUCT IMAGES
     *
     * @param productId
     * @param fileArray
     */
    public ResponseEntity<CommonResponseRecord> patchProductImages(String productId, MultipartFile[] fileArray) {

        log.info("Product id {}", productId);
        final URI uri = UriComponentsBuilder
                .fromUriString(FILE_SERVER_URI)
                .path("/patch/product-image")
                .queryParam("productId", productId)
                .queryParam("fileArray", (Object) fileArray)
                .encode()
                .build()
                .toUri();

        log.info("Uri: {}", uri);
        final ResponseEntity<CommonResponseRecord> response = restTemplate.getForEntity(uri, CommonResponseRecord.class);
        log.info("Response received: {}", response);
        return response;
    }


    /**
     * LOAD PRODUCT IMAGES' INFORMATION
     *
     * @param productId
     */
    public ResponseEntity<ImageInfoListDto> loadProductImagesInfo(String productId) {

        log.info("Product id {}", productId);
        final URI uri = UriComponentsBuilder
                .fromUriString(FILE_SERVER_URI)
                .path("/load/product-info")
                .queryParam("productId", productId)
                .encode()
                .build()
                .toUri();

        log.info("Uri: {}", uri);
        final ResponseEntity<ImageInfoListDto> response = restTemplate.getForEntity(uri, ImageInfoListDto.class);
        log.info("Response received: {}", response);
        return response;
    }


    /**
     * DOWNLOAD SELECTED PRODUCT IMAGE
     *
     * @param productId
     * @param fileName
     */
    public ResponseEntity<Resource> downloadProductImage(String productId, String fileName) {

        log.info("Product id {}", productId);
        log.info("File name id {}", fileName);
        final URI uri = UriComponentsBuilder
                .fromUriString(FILE_SERVER_URI)
                .path("/download/product-image")
                .queryParam("productId", productId)
                .queryParam("fileName", fileName)
                .encode()
                .build()
                .toUri();

        log.info("Uri: {}", uri);
        final ResponseEntity<Resource> response = restTemplate.getForEntity(uri, Resource.class);
        log.info("Response received: {}", response);
        return response;

    }


    /**
     * DOWNLOAD ZIP OF ALL PRODUCT IMAGES
     *
     * @param productId
     */
    public ResponseEntity<Resource> downloadProductImageZip(String productId) {

        log.info("Product id {}", productId);
        final URI uri = UriComponentsBuilder
                .fromUriString(FILE_SERVER_URI)
                .path("/download/product-image-zip")
                .queryParam("productId", productId)
                .encode()
                .build()
                .toUri();

        log.info("Uri: {}", uri);
        final ResponseEntity<Resource> response = restTemplate.getForEntity(uri, Resource.class);
        log.info("Response received: {}", response);
        return response;


    }


    /**
     * DELETE SELECTED PRODUCT IMAGES
     *
     * @param productId
     * @param deletedFileNameList
     */
    public ResponseEntity<CommonResponseRecord> deleteSelectedProductImages(String productId,
                                                                            List<String> deletedFileNameList) {

        log.info("Product id {}", productId);
        final URI uri = UriComponentsBuilder
                .fromUriString(FILE_SERVER_URI)
                .path("/delete/product-image-selected")
                .queryParam("productId", productId)
                .queryParam("deletedFileNameList", deletedFileNameList)
                .encode()
                .build()
                .toUri();

        log.info("Uri: {}", uri);
        final ResponseEntity<CommonResponseRecord> response = restTemplate.getForEntity(uri, CommonResponseRecord.class);
        log.info("Response received: {}", response);
        return response;
    }


    /**
     * DELETE ALL PRODUCT IMAGES
     *
     * @param productId
     */
    public ResponseEntity<CommonResponseRecord> deleteProductImages(String productId) {

        log.info("Product id {}", productId);
        URI uri = UriComponentsBuilder
                .fromUriString(FILE_SERVER_URI)
                .path("/delete/product-image")
                .queryParam("productId", productId)
                .encode()
                .build()
                .toUri();

        log.info("Uri: {}", uri);
        final ResponseEntity<CommonResponseRecord> response = restTemplate.getForEntity(uri, CommonResponseRecord.class);
        log.info("Response received: {}", response);
        return response;
    }


}
