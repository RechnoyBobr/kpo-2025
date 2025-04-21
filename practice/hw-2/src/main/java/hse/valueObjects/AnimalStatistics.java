package hse.valueObjects;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;


public record AnimalStatistics(
    @Schema(description = "Список всех животных")
    List<String> animals,
    @Schema(description = "Количество животных по типу")
    Map<AnimalType, Integer> animalsByType,
    @Schema(description = "Имена всех больных животных")
    List<String> SickAnimals,
    @Schema(description = "Количество животных по виду")
    Map<String, Integer> animalsBySpecie,
    @Schema(description = "Количество всех животных в зоопарке")
    int totalAnimals
) {
}
