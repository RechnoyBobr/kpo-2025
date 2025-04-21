package hse.valueObjects;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public record ScheduleUpdateParams(
    @Schema(description = "Время кормления", example = "12:00")
    @NotNull(message = "Время кормления не может быть пустым")
    LocalTime feedingTime) {
}
