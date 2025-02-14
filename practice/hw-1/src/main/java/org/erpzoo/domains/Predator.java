package org.erpzoo.domains;

import java.util.Optional;

/**
 * Predator class.
 */
public abstract class Predator extends Animal {

    /**
     * Constructor.
     *
     * @param food Amount of food animal consume.
     */
    Predator(int food, int health) {
        super(food, health);
    }

    @Override
    public Optional<Integer> getKindness() {
        return Optional.empty();
    }
}
