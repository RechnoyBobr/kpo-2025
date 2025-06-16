package hse.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public record OrderEvent(
        Integer orderId,
        Integer userId,
        Double amount) {
}
