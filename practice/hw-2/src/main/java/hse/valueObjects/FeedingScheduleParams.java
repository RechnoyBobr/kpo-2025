package hse.valueObjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import lombok.Builder;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Builder
public record FeedingScheduleParams(
    @Schema(description = "Имя животного", example = "Барсик")
    @NotNull(message = "Имя животного не может быть пустым")
    String animalName,
    
    @Schema(description = "Время кормления", example = "12:00")
    @NotNull(message = "Время кормления не может быть пустым")
    LocalTime feedingTime,
    
    @Schema(description = "Тип животного (хищник/травоядное)", example = "true")
    @DefaultValue(value = "false")
    Boolean isCarnivore
) {
} 