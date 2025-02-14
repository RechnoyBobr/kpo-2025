package org.erpzoo.domains;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for {@link Zoo}.
 */
@SpringBootTest
public class ZooTest {

    @Autowired
    Zoo zoo;

    @Test
    void insertAnimal() {
        zoo.processNewAnimal(new Tiger(10, 12));
        Assertions.assertEquals(zoo.animals.size(), 1);
    }

    @Test
    void insertInventory() {
        zoo.processNewInventory(new Computer(1));
        Assertions.assertEquals(zoo.inventory.size(), 1);
    }
}
