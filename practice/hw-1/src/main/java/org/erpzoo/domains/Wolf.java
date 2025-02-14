package org.erpzoo.domains;


import org.erpzoo.parameters.AnimalType;

/**
 * Wolf class.
 */
public class Wolf extends Predator {
    /**
     * Constructor.
     *
     * @param food Amount of food animal consume.
     */
    public Wolf(int food, int health) {
        super(food, health);
    }

    @Override
    public AnimalType toAnimalType() {
        return AnimalType.WOLF;
    }
}
