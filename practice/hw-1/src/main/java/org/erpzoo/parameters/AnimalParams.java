package org.erpzoo.parameters;


import java.util.Optional;

/**
 * Parameters of animal.
 *
 * @param type Animal type
 *
 * @param health Health (1-10)
 *
 * @param food Food consumption
 *
 * @param kindness Level of kindness (1-10)
 */
public record AnimalParams(AnimalType type, int health, int food, Optional<Integer> kindness) {
}
