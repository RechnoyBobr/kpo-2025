package hse.domain;

import hse.valueObjects.AnimalType;
import java.time.LocalDate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Animal domain class.
 */
@RequiredArgsConstructor
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
     * Is animal hungry.
     */
    private Boolean isHungry;

    /**
     * Feed animal.
     */
    public void feed() {
        isHungry = false;
    }

    /**
     * Heal animal.
     */
    public void healAnimal() {
        isHealthy = true;
    }
}
