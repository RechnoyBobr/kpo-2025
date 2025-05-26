package hse.valueobjects;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

/**
 * Enclosure params.
 *
 * @param type        Type
 * @param maxCapacity Max capacity
 */
public record EnclosureParams(
    @Schema(description = "Тип животного, подходящего для вольера", example = "CARNIVORE")
    @Pattern(regexp = "CARNIVORE|HERBIVORE|OMNIVORE|BIRD|FISH")
    String type,
    @Schema(description = "Количество мест в вольере", example = "20")
    Integer maxCapacity
) {
}
