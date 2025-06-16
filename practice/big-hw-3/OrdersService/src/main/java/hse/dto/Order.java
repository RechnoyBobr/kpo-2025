package hse.dto;

import hse.entities.OrderEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record Order(
        @Schema(description = "User id", example = "1") Integer user_id,
        @Schema(description = "Cost of order", example = "120") Double amount,
        @Schema(description = "Order description") String description) {


    public OrderEntity toEntity() {
        OrderEntity entity = new OrderEntity();
        entity.setUserId(user_id);
        entity.setAmount(amount);
        entity.setDescription(description);
        entity.setStatus("NEW");
        return entity;
    }

    public static Order fromEntity(OrderEntity entity) {
        return new Order(entity.getUserId(), entity.getAmount(), entity.getDescription());
    }

    public String format() {
        StringBuilder sb = new StringBuilder();
        sb.append("User id: ").append(user_id);
        sb.append(",\n amount : ").append(amount);
        sb.append(",\n description: ").append(description);
        return sb.toString();
    }
}
