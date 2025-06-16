package hse.kafka;

import hse.entities.PaymentTask;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentProducer {
    private final KafkaTemplate<String, PaymentResultEvent> kafkaTemplate;

    public void sendPaymentResult(PaymentResultEvent paymentResult) {
        System.out.println("Sending payment result: " + paymentResult);
        kafkaTemplate.send(
                "payments-topic",
                paymentResult.orderId().toString(),
                paymentResult
        );
    }
}
