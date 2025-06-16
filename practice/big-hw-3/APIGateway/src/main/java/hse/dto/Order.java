package hse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record Order(
        @Schema(description = "User id", example = "1") Integer user_id,
        @Schema(description = "Cost of order", example = "120") Double amount,
        @Schema(description = "Order description") String description) {

}
