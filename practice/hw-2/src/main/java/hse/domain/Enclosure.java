package hse.domain;

import hse.valueObjects.AnimalType;
import java.util.ArrayList;
import java.util.List;

/**
 * Enclosure class.
 */
public class Enclosure {

    /**
     * Enclosure type for animal.
     */
    private final AnimalType type;

    /**
     * Current animals in this enclosure.
     */
    private int currentAnimals = 0;

    /**
     * Maximal animal capacity.
     */
    private final int maxAnimals;

    private final List<Animal> animals;

    /**
     * Constructor.
     *
     * @param type       Enclosure type
     * @param maxAnimals Maximal capacity
     */
    public Enclosure(final AnimalType type, final int maxAnimals) {
        this.type = type;
        this.maxAnimals = maxAnimals;
        animals = new ArrayList<Animal>(maxAnimals);
    }

    /**
     * Add animal to enclosure.
     *
     * @param animal Animal to add
     */
    public void addAnimal(final Animal animal) {
        if (currentAnimals + 1 > maxAnimals) {
            throw new UnsupportedOperationException(
                "Can't add animal to enclosure. There is no space"
            );
        }
        if (this.type != animal.getType()) {
            throw new IllegalArgumentException(
                String.format(
                    "Can't add animal of type %s to enclosure with type %s.",
                    animal.getType().toString(),
                    this.type.toString()
                )
            );
        }
        animals.add(animal);
        currentAnimals++;
    }

    /**
     * Remove animal.
     *
     * @param animal Animal to remove.
     */
    public void removeAnimal(final Animal animal) {
        boolean isDeleted = this.animals.remove(animal);
        if (!isDeleted) {
            throw new IllegalArgumentException(
                "There is no such animal in this enclosure"
            );
        }
    }
}
