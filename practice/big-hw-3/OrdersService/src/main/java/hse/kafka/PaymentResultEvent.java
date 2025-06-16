package hse.kafka;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public record PaymentResultEvent(Integer orderId, Boolean success) {
}
