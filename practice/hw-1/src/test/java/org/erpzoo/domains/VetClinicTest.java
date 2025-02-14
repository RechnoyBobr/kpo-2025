package org.erpzoo.domains;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for {@link VetClinic}.
 */
@SpringBootTest
public class VetClinicTest {

    @Autowired
    private VetClinic vetClinic;

    @Test
    void animalProceed() {
        Animal monkey = new Monkey(1, 6, 10);
        Assertions.assertTrue(vetClinic.checkAnimal(monkey));
    }

    @Test
    void animalNotProceed() {
        Animal monkey = new Monkey(1, 4, 10);
        Assertions.assertFalse(vetClinic.checkAnimal(monkey));
    }
}
