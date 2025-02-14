package org.erpzoo.domains;

import lombok.Getter;
import org.erpzoo.interfaces.Alive;
import org.erpzoo.parameters.AnimalType;

/**
 * Animal class.
 */
@Getter
public abstract class Animal implements Alive {

    private final int food;

    private final int health;

    /**
     * Constructor.
     *
     * @param food Amount of food animal consume.
     */
    public Animal(int food, int health) {
        this.food = food;
        this.health = health;
    }

    public abstract AnimalType toAnimalType();

}
