package org.erpzoo.builder;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.erpzoo.domains.Animal;
import org.erpzoo.domains.Thing;
import org.erpzoo.parameters.AnimalType;
import org.erpzoo.parameters.Report;
import org.erpzoo.parameters.ThingType;
import org.springframework.stereotype.Component;

/**
 * Builder for reports.
 */
@Component
public class ReportBuilder {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    StringBuilder content;

    /**
     * Standard constructor.
     */
    public ReportBuilder() {
        content = new StringBuilder();
    }

    /**
     * Append animals to report.
     *
     * @param animals Animals to append
     * @return Self
     */
    public ReportBuilder addAnimals(List<Animal> animals) {
        content.append("Животные в зоопарке: ");
        animals.stream().map(Animal::toAnimalType).distinct().forEach(animalType -> {
            long count = animals.stream().filter(animal -> animal.toAnimalType() == animalType).count();
            content.append(String.format("%s x %d  ", AnimalType.toString(animalType), count));
        });
        content.append("\n");
        return this;
    }

    /**
     * Append animals from contact zoo to report.
     *
     * @param animals Animals to append
     * @return Self
     */
    public ReportBuilder addContactZooAnimals(List<Animal> animals) {
        content.append("Животные в контактном зоопарке: ");
        animals.stream().map(Animal::toAnimalType).distinct().forEach(animalType -> {
            long count = animals.stream().filter(animal -> animal.toAnimalType() == animalType).count();
            content.append(String.format("%s x %d  ", AnimalType.toString(animalType), count));
        });
        content.append("\n");
        return this;
    }

    /**
     * Append animals food consumption to report.
     *
     * @param animals Animals to append food consumption
     * @return Self
     */
    public ReportBuilder addFoodConsumption(List<Animal> animals) {
        int sum = animals.stream().mapToInt(Animal::getFood).sum();
        content.append(String.format("Потребляемость пищи в кг - %d", sum));
        content.append("\n");
        return this;
    }

    /**
     * Append things to report.
     *
     * @param things Things to append
     * @return Self
     */
    public ReportBuilder addInventory(List<Thing> things) {
        content.append("Инвентарь в зоопарке: ");
        things.stream().map(Thing::toThingType).distinct().forEach(thingType -> {
            long count = things.stream().filter(thing -> thing.toThingType() == thingType).count();
            content.append(String.format("%s x %d  ", ThingType.toString(thingType), count));
            content.append("ID: ( ");
            things.stream().filter(thing -> thing.toThingType() == thingType).forEach(thing -> {
                content.append(String.format("%d, ", thing.getId()));
            });
            content.append(")\n");
        });
        return this;
    }

    /**
     * Build report.
     *
     * @return Report
     */
    public Report build() {
        return new Report(String.format("Отчет за %s", ZonedDateTime.now().format(DATE_TIME_FORMATTER)),
            content.toString());
    }
}
