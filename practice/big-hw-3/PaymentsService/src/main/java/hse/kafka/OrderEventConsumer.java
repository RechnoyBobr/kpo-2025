package hse.kafka;

import hse.dto.OrderEvent;
import hse.services.PaymentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventConsumer {
    private final PaymentsService service;

    @KafkaListener(topics = "orders-topic", groupId = "kpo")
    public void listen(OrderEvent order) {
        System.out.println("Got an order: " + order);
        service.processPayment(order);

    }
}
