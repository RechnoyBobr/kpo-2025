package hse.kafka;

import hse.entities.OrderEntity;
import hse.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentResultConsumer {

    private final OrderRepository orderRepository;

    @KafkaListener(topics = "payments-topic", groupId = "kpo")
    public void listener(PaymentResultEvent event) {
        System.out.println("Received payment result: " + event);
        OrderEntity entity = orderRepository.findFirstById(event.orderId());
        if (event.success()) {
            entity.setStatus("FINISHED");
        } else {
            entity.setStatus("FAILED");
        }
        entity = orderRepository.save(entity);
    }
}
