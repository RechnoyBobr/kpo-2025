package org.erpzoo.domains;

import java.util.ArrayList;
import java.util.List;
import org.erpzoo.builder.ReportBuilder;
import org.erpzoo.parameters.AnimalType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Zoo class.
 */
@Component
public class Zoo {

    List<Animal> animals = new ArrayList<>();

    List<Animal> kindAnimals = new ArrayList<>();

    List<Thing> inventory = new ArrayList<>();
    @Autowired
    VetClinic clinic;
    @Autowired
    ReportBuilder reportBuilder;

    /**
     * Tries to add new animal to zoo.
     *
     * @param animal Animal to add
     */
    public void processNewAnimal(Animal animal) {
        if (this.clinic.checkAnimal(animal)) {
            this.animals.add(animal);
            if (animal.getKindness().isPresent() && animal.getKindness().get() > 5) {
                this.kindAnimals.add(animal);
            }

        } else {
            System.out.println(
                String.format(
                    "Животное: %s имеет слишком серьёзные проблемы"
                        + " со здоровьем, чтобы его принять в зоопарк\n",
                    AnimalType.toString(animal.toAnimalType())
                )
            );
        }
    }

    /**
     * Adds new thing to zoo inventory.
     *
     * @param thing Thing to add
     */
    public void processNewInventory(Thing thing) {
        this.inventory.add(thing);
    }

    /**
     * Build report.
     *
     * @return report.
     */
    public String buildReport() {
        return reportBuilder
            .addAnimals(this.animals)
            .addContactZooAnimals(this.kindAnimals)
            .addFoodConsumption(this.animals).addInventory(this.inventory)
            .build().toString();
    }
}
