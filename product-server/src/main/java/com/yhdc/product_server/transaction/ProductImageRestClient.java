package com.yhdc.product_server.transaction;

import com.yhdc.product_server.transaction.object.CommonResponseRecord;
import com.yhdc.product_server.transaction.object.ImageInfoListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.yhdc.product_server.transaction.type.Constants.FILE_SERVER_URI;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductImageRestClient {

    private final RestTemplate restTemplate;


    /**
     * LOAD PRODUCT IMAGES' INFORMATION
     *
     * @param productId
     */
    public ResponseEntity<ImageInfoListDto> loadAllProductImageInfo(String productId) {

        log.info("Loading image information for Product id {}", productId);
        final URI uri = UriComponentsBuilder
                .fromUriString(FILE_SERVER_URI)
                .path("/load/image-info")
                .queryParam("dirId", productId)
                .encode()
                .build()
                .toUri();

        log.info("Uri: {}", uri);
        final ResponseEntity<ImageInfoListDto> response = restTemplate.getForEntity(uri, ImageInfoListDto.class);
        log.info("Response received: {}", response);
        return response;
    }


    /**
     * DELETE ALL PRODUCT IMAGES
     *
     * @param productId
     */
    public ResponseEntity<CommonResponseRecord> deleteProductImages(String productId) {

        log.info("Deleting images for Product id {}", productId);
        URI uri = UriComponentsBuilder
                .fromUriString(FILE_SERVER_URI)
                .path("/delete/image-dir")
                .queryParam("dirId", productId)
                .encode()
                .build()
                .toUri();

        log.info("Uri: {}", uri);
        final ResponseEntity<CommonResponseRecord> response
                = restTemplate.exchange(uri, HttpMethod.DELETE, null, CommonResponseRecord.class);
        log.info("Response received: {}", response);
        return response;
    }

}
