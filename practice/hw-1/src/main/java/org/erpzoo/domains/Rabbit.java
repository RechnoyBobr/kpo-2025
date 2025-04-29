package org.erpzoo.domains;


import org.erpzoo.parameters.AnimalType;

/**
 * Rabbit class.
 */
public class Rabbit extends Herbo {
    public Rabbit(int food, int health, int kindness) {
        super(food, health, kindness);
    }


    @Override
    public AnimalType toAnimalType() {
        return AnimalType.RABBIT;
    }
}
