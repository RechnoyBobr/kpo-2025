package hse.valueObjects;

import io.swagger.v3.oas.annotations.media.Schema;

public record AnimalMovingParams(
    @Schema(description = "Имя животного", example = "Барсик")
    String animalName,
    @Schema(description = "Номер вольера, из которого перемещают животное", example = "0")
    Integer from,
    @Schema(description = "Номер вольера, в которое перемещают животное", example = "1")
    Integer to
) {
}
