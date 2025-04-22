package hse.valueobjects;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;

/**
 * Animal statistics record.
 *
 * @param animals Animals
 * @param animalsByType Animals by type
 * @param sickAnimals Sick animals
 * @param animalsBySpecie Animals by specie
 * @param totalAnimals Total animals
 */
public record AnimalStatistics(
    @Schema(description = "Список всех животных")
    List<String> animals,
    @Schema(description = "Количество животных по типу")
    Map<AnimalType, Integer> animalsByType,
    @Schema(description = "Имена всех больных животных")
    List<String> sickAnimals,
    @Schema(description = "Количество животных по виду")
    Map<String, Integer> animalsBySpecie,
    @Schema(description = "Количество всех животных в зоопарке")
    int totalAnimals
) {
}
