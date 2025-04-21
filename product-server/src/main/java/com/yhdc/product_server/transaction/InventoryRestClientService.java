package com.yhdc.product_server.transaction;

import com.yhdc.product_server.transaction.object.InventoryCommonRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class InventoryRestClientService {

    private static final String inventoryServerUrl = "http://localhost:8085/inventory";

    private final RestClient restClient;


    /**
     * CHECK STOCK
     *
     * @param productId
     * @param skuCode
     * @param quantity
     * @implNote
     */
    public String isInStock(String productId,
                            String skuCode,
                            String quantity) {

        Map<String, String> params = new HashMap<>();
        params.put("productId", productId);
        params.put("skuCode", skuCode);
        params.put("quantity", quantity);

        return restClient.get()
                .uri(inventoryServerUrl + "/stock-check", params)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(String.class);
    }


    /**
     * CREATE INVENTORY FOR A NEW PRODUCT
     *
     * @param inventoryCommonRecord
     */
    public ResponseEntity<?> createStock(InventoryCommonRecord inventoryCommonRecord) {
        return restClient.post()
                .uri(inventoryServerUrl + "/create")
                .body(inventoryCommonRecord)
                .accept(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is4xxClientError()) {
                        // TODO: exception handling
                        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
                    } else if (response.getStatusCode().is5xxServerError()) {
                        // TODO: exception handling
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    } else {
                        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
                    }
                });
    }


    /**
     * GET CURRENT INVENTORY QUANTITY FRO A PRODUCT
     *
     * @param productId
     * @param skuCode
     */
    public String getInventoryStock(String productId, String skuCode) {

        Map<String, String> params = new HashMap<>();
        params.put("productId", productId);
        params.put("skuCode", skuCode);

        return restClient.get()
                .uri(inventoryServerUrl + "/stock", params)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(String.class);
    }


    /**
     * INCREASE PRODUCT INVENTORY
     *
     * @param inventoryCommonRecord
     */
    public ResponseEntity<?> increaseStock(@RequestBody InventoryCommonRecord inventoryCommonRecord) {
        return restClient.patch()
                .uri(inventoryServerUrl + "/increase")
                .body(inventoryCommonRecord)
                .accept(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is4xxClientError()) {
                        // TODO: exception handling
                        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
                    } else if (response.getStatusCode().is5xxServerError()) {
                        // TODO: exception handling
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    } else {
                        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
                    }
                });
    }


    /**
     * DECREASE PRODUCT INVENTORY
     *
     * @param inventoryCommonRecord
     */
    ResponseEntity<?> decreaseStock(@RequestBody InventoryCommonRecord inventoryCommonRecord) {
        return restClient.patch()
                .uri(inventoryServerUrl + "/decrease")
                .body(inventoryCommonRecord)
                .accept(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is4xxClientError()) {
                        // TODO: exception handling
                        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
                    } else if (response.getStatusCode().is5xxServerError()) {
                        // TODO: exception handling
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    } else {
                        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
                    }
                });
    }


    /**
     * UPDATE PRODUCT SKU
     *
     * @param productId
     * @param skuCode
     * @param newSkuCode
     */
    public ResponseEntity<?> patchSku(String productId, String skuCode, String newSkuCode) {

        Map<String, String> params = new HashMap<>();
        params.put("productId", productId);
        params.put("skuCode", skuCode);
        params.put("newSkuCode", newSkuCode);

        return restClient.patch()
                .uri(inventoryServerUrl + "/patch/sku", params)
                .accept(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is4xxClientError()) {
                        // TODO: exception handling
                        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
                    } else if (response.getStatusCode().is5xxServerError()) {
                        // TODO: exception handling
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    } else {
                        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
                    }
                });
    }


    /**
     * @param productId
     * @param skuCode
     * @return
     */
    public ResponseEntity<?> deleteStock(String productId, String skuCode) {

        Map<String, String> params = new HashMap<>();
        params.put("productId", productId);
        params.put("skuCode", skuCode);

        return restClient.delete()
                .uri(inventoryServerUrl + "/delete", params)
                .accept(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is4xxClientError()) {
                        // TODO: exception handling
                        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
                    } else if (response.getStatusCode().is5xxServerError()) {
                        // TODO: exception handling
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    } else {
                        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
                    }
                });
    }
}
