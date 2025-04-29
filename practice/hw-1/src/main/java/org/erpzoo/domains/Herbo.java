package org.erpzoo.domains;

import java.util.Optional;

/**
 * Herbo class.
 */
public abstract class Herbo extends Animal {

    private int kindness;

    Herbo(int food, int health, int kindness) {
        super(food, health);
        this.kindness = kindness;
    }

    @Override
    public Optional<Integer> getKindness() {
        return Optional.of(kindness);
    }
}
