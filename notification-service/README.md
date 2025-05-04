# Notification Microservice

## Messaging - Kafka

As a part of Event-Driven Architecture, Kafka has been implemented for notification service
since it can handle massive amount of data in real-time through event streaming and stream processing.

<img src="../readme/image/kafka_diagram.png" width="500" height="200" />

```properties
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.template.default-topic=order-process
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonDeserializer
spring.kafka.producer.properties.schema.registry.url=http://127.0.0.1:8201
```
Below, the topic sent from the Order service is received, and from the topic the order information is used to sent confirmation email to the buyer.

```java

public class NotificationServiceImpl {

    private final JavaMailSender mailSender;

    @KafkaListener(topics = {"order-process"})
    public void listen(OrderProcessEvent orderProcessEvent) {
        log.info("Received order process event: {}", orderProcessEvent);

        // Compose email to the customer
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("yhdc@yhdc.com");
            messageHelper.setTo(orderProcessEvent.getEmail());
            messageHelper.setSubject(String.format("Order with ID %s is processed successfully",
                    orderProcessEvent.getOrderId()));
            messageHelper.setText(String.format(
                    """
                            Hello.
                            Your order has been processed successfully.
                            
                            OrderID: %s
                            
                            Best regards,
                            Cloud Store staff
                            
                            """
                    , orderProcessEvent.getOrderId()));
        };

        // Send mail
        try {
            mailSender.send(messagePreparator);
            log.info("Mail has been sent for order process event ID: {}", orderProcessEvent.getOrderId());

        } catch (MailException me) {
            throw new RuntimeException(me);
        }
    }

}
```

## Email - SMTP

> To send email to the users, we need SMTP server. For this project I am using mailtrap.io server. 

```properties
mail.host=sandbox.smtp.mailtrap.io
mail.port=${MAIL_PORT}
mail.username=${MAIL_USERNAME}
mail.password=${MAIL_PASSWORD}
```

## Monitoring - Actuator

Spring Boot Actuator is a sub-project of Spring Boot that provides a set of built-in production-ready features to help
you monitor and manage your application.
Actuator includes several endpoints that allow you to interact with the application, gather metrics, check the health,
and perform various management tasks.

```properties
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
management.info.env.enabled=true
# For the actuator/info page
info.app.name=Notification Service
info.app.description=Notification Management Service
info.app.version=1.0.0
info.app.author=Daniel Choi
```
