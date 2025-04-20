package hse.domain;

import hse.valueObjects.AnimalType;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;


/**
 * Enclosure class.
 */
@Getter
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
    public void removeAnimal(int ind) {
        this.animals.remove(ind);
    }

    public boolean hasAnimal(final Animal animal) {
        return this.animals.contains(animal);
    }

    /**
     * Returns animal by his name.
     *
     * @param name Name of animal.
     * @return Animal.
     */
    public int hasAnimal(final String name) {
        for (int i = 0; i < animals.size(); i++) {
            if (animals.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public Animal getAnimal(final int ind) {
        return animals.get(ind);
    }
}
