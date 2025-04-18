package com.yhdc.order_server.transaction;

import com.yhdc.order_server.object.OrderCreateRecord;
import com.yhdc.order_server.object.ProductData;
import com.yhdc.order_server.object.StoreData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl {

    private final OrderRepository orderRepository;
    private final InventoryFeignClient inventoryFeignClient;


    /**
     * PROCESS ORDER
     *
     * @param orderCreateRecord
     */
    public ResponseEntity<?> processOrder(OrderCreateRecord orderCreateRecord) {
        boolean checkStock = true;
        int totalPrice = 0;
        for (StoreData storeData : orderCreateRecord.storeDataList()) {
            List<ProductData> productDataList = storeData.getProductDataList();
            for (ProductData productData : productDataList) {
                boolean isStock = inventoryFeignClient.isInStock(productData.getProductId(), productData.getQuantity());
                if (!isStock) {
                    productData.setStock(false);
                    checkStock = false;
                    log.warn("Out of stack: {} / {}", productData.getName(), productData.getQuantity());
                } else {
                    productData.setStock(true);
                    totalPrice += Integer.parseInt(productData.getPrice());
                }
            }
        }

        log.info("Check stock: {}", checkStock);
        log.info("Total price: {}", totalPrice);

        if (checkStock) {
            // Save order and process
            Order order = orderRepository.save(createOrder(orderCreateRecord, totalPrice));
            return new ResponseEntity<>(order.getId(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(orderCreateRecord, HttpStatus.NOT_ACCEPTABLE);
        }
    }


    /**
     * CREATE ORDER OBJECT
     *
     * @param orderCreateRecord
     */
    private Order createOrder(OrderCreateRecord orderCreateRecord, int totalPrice) {
        Order order = new Order();
        order.setUserId(orderCreateRecord.userId());
        order.setStoreList(orderCreateRecord.storeDataList());
        order.setTotal(String.valueOf(totalPrice));
        order.setStatus("PROCESSING");
        return order;
    }




}
