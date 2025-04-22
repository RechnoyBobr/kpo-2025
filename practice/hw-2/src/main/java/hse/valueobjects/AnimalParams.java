package hse.valueobjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.Builder;

/**
 * Animal params.
 *
 * @param name          Name
 * @param type          Type
 * @param specie        Specie
 * @param birthDate     Birthdate
 * @param isMale        Sex
 * @param favouriteFood Favourite food
 * @param isHealthy     Is healthy
 */
@Builder
public record AnimalParams(
    @Schema(description = "Кличка животного", example = "Барсик")
    String name,
    @Schema(description = "Тип животного", example = "CARNIVORE")
    @Pattern(regexp = "CARNIVORE|HERBIVORE|OMNIVORE|BIRD|FISH")
    String type,
    @Schema(description = "Вид животного", example = "Рысь")
    String specie,
    @Schema(description = "Дата рождения", example = "15.05.2012")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    LocalDate birthDate,
    @Schema(description = "Пол рождения (is male?)", example = "true")
    Boolean isMale,
    @Schema(description = "Любимая еда", example = "Арбузы")
    String favouriteFood,
    @Schema(description = "Здоровье животного(is healthy?)", example = "true")
    Boolean isHealthy
) {
}
