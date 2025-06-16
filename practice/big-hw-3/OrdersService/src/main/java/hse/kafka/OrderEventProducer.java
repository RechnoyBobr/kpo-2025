package hse.kafka;

import hse.dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;


    public void sendOrderToPayment(OrderEvent event) {
        System.out.println("Sending order to payment service:" + event);
        kafkaTemplate.send(
                "orders-topic",
                event.orderId().toString(),
                event
        );
    }

}
