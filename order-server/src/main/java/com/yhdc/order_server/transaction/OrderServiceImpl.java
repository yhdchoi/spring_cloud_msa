package com.yhdc.order_server.transaction;

import com.yhdc.order_server.transaction.object.OrderCreateRecord;
import com.yhdc.order_server.transaction.object.ProductData;
import com.yhdc.order_server.transaction.object.StoreData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DatabaseSequenceGeneratorService databaseSequenceGeneratorService;
    private final InventoryFeignClient inventoryFeignClient;


    /**
     * PROCESS ORDER
     *
     * @param orderCreateRecord
     */
    @Override
    @Transactional
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
    @Transactional
    protected Order createOrder(OrderCreateRecord orderCreateRecord, int totalPrice) {
        try {
            Order order = new Order();
            order.setId(databaseSequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME));
            order.setBuyerId(orderCreateRecord.userId());
            order.setStoreList(orderCreateRecord.storeDataList());
            order.setTotalPrice(String.valueOf(totalPrice));
            order.setStatus("PROCESSING");
            return order;
        } catch (Exception e) {
            log.error("Error creating order entity!!!", e);
            // TODO: global exception handler
            throw new RuntimeException(e);
        }
    }


    @Override
    @Transactional
    public ResponseEntity<?> cancelOrder(String orderId, String userId) {
        try {
            Optional<Order> orderOptional = orderRepository.findByIdAndBuyerId(Long.parseLong(orderId), userId);
            if (orderOptional.isPresent()) {
                orderOptional.get().setStatus("CANCEL");
                return new ResponseEntity<>(orderOptional.get().getId(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Can't cancel order!!!", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
