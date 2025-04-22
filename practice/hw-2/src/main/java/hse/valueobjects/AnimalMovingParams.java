package hse.valueobjects;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Animal moving params.
 *
 * @param animalName Name
 * @param from       From
 * @param to         To
 */
public record AnimalMovingParams(
    @Schema(description = "Имя животного", example = "Барсик")
    String animalName,
    @Schema(description = "Номер вольера, из которого перемещают животное", example = "0")
    Integer from,
    @Schema(description = "Номер вольера, в которое перемещают животное", example = "1")
    Integer to
) {
}
