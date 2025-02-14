package org.erpzoo.domains;

import org.springframework.stereotype.Component;

/**
 * Vet clinic class.
 */
@Component
public class VetClinic {
    public boolean checkAnimal(Animal animal) {
        return animal.getHealth() > 5;
    }
}
