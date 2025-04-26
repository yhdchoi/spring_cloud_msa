package com.yhdc.notification_service.transaction;

import com.yhdc.notification_service.transaction.event.OrderProcessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
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
