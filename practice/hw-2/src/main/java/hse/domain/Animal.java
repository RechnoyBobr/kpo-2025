package hse.domain;

import hse.valueObjects.AnimalType;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Animal domain class.
 */
@Log4j2
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class Animal {

    /**
     * Animal name.
     */
    private final String name;

    /**
     * Animal type.
     */
    private final AnimalType type;

    /**
     * Animal specie.
     */
    private final String specie;

    /**
     * Date of birth.
     */
    private final LocalDate birthDate;

    /**
     * Sex of animal.
     */
    private final Boolean isMale;

    /**
     * Animal favourite food.
     */
    private final String favouriteFood;

    /**
     * Is animal healthy.
     */
    private Boolean isHealthy;


    /**
     * Feed animal.
     */
    public void feed() {
        log.info(String.format("Feeding animal %s", this.name));
    }

    /**
     * Heal animal.
     */
    public void healAnimal() {
        log.info(String.format("Healing animal %s", this.name));
        isHealthy = true;
    }

    @Override
    public String toString() {
        return String.format(
            "Animal:\n\t Name: %s,\n\t Type: %s\n\t Birthdate: %s,\n\t Sex: %s,\n\t Specie: %s,\n\t Favourite food: %s,\n\t health: %s",
            this.name, this.type.toString(), this.birthDate.toString(), this.isMale ? "Male" : "Female", this.specie,
            this.favouriteFood, this.isHealthy ? "Healthy" : "Ill"
        );
    }
}
