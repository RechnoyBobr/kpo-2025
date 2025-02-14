package org.erpzoo.domains;

import org.erpzoo.parameters.AnimalType;

/**
 * Monkey class.
 */
public class Monkey extends Herbo {

    /**
     * Constructor.
     *
     * @param food Amount of food animal consume.
     */
    public Monkey(int food, int health, int kindness) {
        super(food, health, kindness);
    }

    @Override
    public AnimalType toAnimalType() {
        return AnimalType.MONKEY;
    }
}
