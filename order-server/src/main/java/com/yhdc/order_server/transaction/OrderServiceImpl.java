package com.yhdc.order_server.transaction;

import com.yhdc.order_server.transaction.event.OrderProcessEvent;
import com.yhdc.order_server.transaction.object.OrderRequestRecord;
import com.yhdc.order_server.transaction.object.ProductData;
import com.yhdc.order_server.transaction.object.StoreData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final InventoryRestClient inventoryRestClient;
    private KafkaTemplate<String, OrderProcessEvent> kafkaTemplate;


    /**
     * PROCESS ORDER
     *
     * @param orderRequestRecord
     */
    @Override
    @Transactional
    public ResponseEntity<?> processOrder(OrderRequestRecord orderRequestRecord) {
        boolean checkStock = true;
        int totalPrice = 0;
        for (StoreData storeData : orderRequestRecord.storeDataList()) {
            List<ProductData> productDataList = storeData.getProductDataList();
            for (ProductData productData : productDataList) {

                final String response = inventoryRestClient.isInStock(
                        productData.getProductId(),
                        productData.getSkuCode(),
                        productData.getQuantity());

                final boolean isStock = Boolean.parseBoolean(response);
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
            final Order order = orderRepository.save(createOrder(orderRequestRecord, totalPrice));

            // Send message to Kafka topic
            OrderProcessEvent orderProcessEvent = new OrderProcessEvent(
                    order.getId().toString(),
                    orderRequestRecord.userDetail().username(),
                    orderRequestRecord.userDetail().firsName(),
                    orderRequestRecord.userDetail().lastName(),
                    orderRequestRecord.userDetail().userEmail()
            );
            log.info("Order process event sent to kafka topic: [ {} ]", order.getId());

            // Kafka
            kafkaTemplate.send("order-process", orderProcessEvent);

            log.info("Order process event completed: [ {} ]", order.getId());
            return new ResponseEntity<>(order.getId(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(orderRequestRecord, HttpStatus.NOT_ACCEPTABLE);
        }
    }


    /**
     * CREATE ORDER OBJECT
     *
     * @param orderRequestRecord
     */
    protected Order createOrder(OrderRequestRecord orderRequestRecord, int totalPrice) {
        try {
            Order order = new Order();
            order.setId(databaseSequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME));
            order.setBuyerId(orderRequestRecord.userId());
            order.setStoreList(orderRequestRecord.storeDataList());
            order.setTotalPrice(String.valueOf(totalPrice));
            order.setStatus("PROCESSING");
            return order;
        } catch (Exception e) {
            log.error("Error creating order entity!!!", e);
            // TODO: global exception handler
            throw new RuntimeException(e);
        }
    }


    /**
     * CANCEL ORDER
     *
     * @param orderId
     * @param userId
     */
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
