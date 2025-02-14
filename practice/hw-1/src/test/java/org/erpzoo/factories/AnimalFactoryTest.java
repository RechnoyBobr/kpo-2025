package org.erpzoo.factories;

import java.util.Optional;
import org.erpzoo.domains.Animal;
import org.erpzoo.parameters.AnimalParams;
import org.erpzoo.parameters.AnimalType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * Tests for {@link AnimalFactory}.
 */
@SpringBootTest
public class AnimalFactoryTest {

    @Autowired
    private AnimalFactory animalFactory;

    @Test
    void createsAnimal() {
        Animal monkey = animalFactory.createAnimal(
            new AnimalParams(AnimalType.MONKEY, 10, 2, Optional.of(2))
        );
        Animal tiger = animalFactory.createAnimal(
            new AnimalParams(AnimalType.TIGER, 10, 2, Optional.empty())
        );
        Animal wolf = animalFactory.createAnimal(
            new AnimalParams(AnimalType.WOLF, 10, 2, Optional.empty())
        );
        Animal rabbit = animalFactory.createAnimal(
            new AnimalParams(AnimalType.RABBIT, 10, 2, Optional.of(2))
        );
        Assertions.assertAll(
            () -> Assertions.assertEquals(monkey.getHealth(), 10),
            () -> Assertions.assertEquals(AnimalType.toString(monkey.toAnimalType()), "Обезьяна"),
            () -> Assertions.assertEquals(AnimalType.toString(tiger.toAnimalType()), "Тигр"),
            () -> Assertions.assertEquals(AnimalType.toString(wolf.toAnimalType()), "Волк"),
            () -> Assertions.assertEquals(AnimalType.toString(rabbit.toAnimalType()), "Кролик")

        );
    }
}
