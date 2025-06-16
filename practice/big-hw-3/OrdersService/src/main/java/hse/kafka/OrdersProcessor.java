package hse.kafka;

import hse.dto.OrderEvent;
import hse.entities.OrderEventEntity;
import hse.repositories.OrderTaskRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrdersProcessor {

    private final OrderEventProducer orderProducer;

    private final OrderTaskRepository repository;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void processOrders() {
        List<OrderEventEntity> orders = repository.findAllByIsFinishedFalse();
        for (OrderEventEntity order : orders) {
            try {
                OrderEvent event = new OrderEvent(order.getId(), order.getUserId(), order.getAmount());
                orderProducer.sendOrderToPayment(event);
                order.setIsFinished(true);
                repository.save(order);
            } catch (Exception e) {
                log.error("Error while sending order to payment", e);
                throw e;
            }
        }
    }
}
