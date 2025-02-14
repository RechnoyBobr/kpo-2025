package org.erpzoo.builder;

import java.util.ArrayList;
import org.erpzoo.domains.Animal;
import org.erpzoo.domains.Computer;
import org.erpzoo.domains.Monkey;
import org.erpzoo.domains.Thing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Report builder tests for {@link ReportBuilder}.
 */
@SpringBootTest
public class ReportBuilderTest {

    @Autowired
    private ReportBuilder reportBuilder;

    @Test
    void providesReport() {
        ArrayList<Animal> animals = new ArrayList<>();
        animals.add(new Monkey(1, 2, 3));
        ArrayList<Thing> things = new ArrayList<>();
        things.add(new Computer(1));
        String result = reportBuilder
            .addAnimals(animals)
            .addInventory(things)
            .addFoodConsumption(animals)
            .addContactZooAnimals(animals)
            .build().toString();
        Assertions.assertAll(
            () -> Assertions.assertTrue(result.contains("Обезьяна")),
            () -> Assertions.assertTrue(result.contains("Компьютер")),
            () -> Assertions.assertTrue(result.contains("2"))
        );
    }
}
