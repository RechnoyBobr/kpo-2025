package org.erpzoo.factories;

import org.erpzoo.domains.Animal;
import org.erpzoo.domains.Monkey;
import org.erpzoo.domains.Rabbit;
import org.erpzoo.domains.Tiger;
import org.erpzoo.domains.Wolf;
import org.erpzoo.parameters.AnimalParams;
import org.springframework.stereotype.Component;

/**
 * Factory to create animals.
 */
@Component
public class AnimalFactory {
    /**
     * Creates animal.
     *
     * @param parameters Animal parameters.
     * @return Animal
     */
    public Animal createAnimal(AnimalParams parameters) {
        return switch (parameters.type()) {
            case WOLF -> new Wolf(parameters.food(), parameters.health());
            case TIGER -> new Tiger(parameters.food(), parameters.health());
            case RABBIT -> new Rabbit(parameters.food(), parameters.health(), parameters.kindness().get());
            case MONKEY -> new Monkey(parameters.food(), parameters.health(), parameters.kindness().get());
        };
    }
}
