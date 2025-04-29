package org.erpzoo.domains;

import org.erpzoo.parameters.AnimalType;

/**
 * Tiger class.
 */
public class Tiger extends Predator {
    /**
     * Constructor.
     *
     * @param food Amount of food animal consume.
     */
    public Tiger(int food, int health) {
        super(food, health);
    }

    @Override
    public AnimalType toAnimalType() {
        return AnimalType.TIGER;
    }

}
