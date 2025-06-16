package hse.kafka;

import hse.entities.PaymentTask;
import hse.repositories.PaymentTasksRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class PaymentsProcessor {

    private final PaymentTasksRepository tasksRepository;

    private final PaymentProducer producer;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void processNewEvents() {
        List<PaymentTask> tasks = tasksRepository.findAllByIsFinishedFalse();
        for (PaymentTask task : tasks) {
            try {
                PaymentResultEvent event = new PaymentResultEvent(task.getId(), task.getStatus());
                producer.sendPaymentResult(event);
                task.setIsFinished(true);
                tasksRepository.save(task);
            } catch (Exception e) {
                log.error("Failed to process outbox event ID: {}", task.getId(), e);
                throw e;
            }
        }
    }
}
