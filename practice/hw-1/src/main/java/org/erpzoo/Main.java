package org.erpzoo;

import java.util.Optional;
import org.erpzoo.domains.Zoo;
import org.erpzoo.factories.AnimalFactory;
import org.erpzoo.factories.ThingFactory;
import org.erpzoo.parameters.AnimalParams;
import org.erpzoo.parameters.AnimalType;
import org.erpzoo.parameters.ThingParams;
import org.erpzoo.parameters.ThingType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


/**
 * Main class.
 */
@SpringBootApplication
public class Main {
    /**
     * Main function.
     *
     * @param args Main args
     */
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        Zoo zoo = context.getBean(Zoo.class);
        AnimalFactory factory = new AnimalFactory();
        ThingFactory thingFactory = new ThingFactory();
        zoo.processNewAnimal(factory.createAnimal(new AnimalParams(AnimalType.TIGER, 6, 12, Optional.empty())));
        zoo.processNewAnimal(factory.createAnimal(new AnimalParams(AnimalType.MONKEY, 10, 2, Optional.of(10))));
        zoo.processNewAnimal(factory.createAnimal(new AnimalParams(AnimalType.WOLF, 10, 5, Optional.empty())));
        zoo.processNewAnimal(factory.createAnimal(new AnimalParams(AnimalType.WOLF, 4, 5, Optional.empty())));
        zoo.processNewAnimal(factory.createAnimal(new AnimalParams(AnimalType.RABBIT, 6, 1, Optional.of(7))));
        zoo.processNewAnimal(factory.createAnimal(new AnimalParams(AnimalType.MONKEY, 10, 2, Optional.of(4))));
        zoo.processNewInventory(thingFactory.createThing(new ThingParams(ThingType.TABLE)));
        zoo.processNewInventory(thingFactory.createThing(new ThingParams(ThingType.TABLE)));
        zoo.processNewInventory(thingFactory.createThing(new ThingParams(ThingType.COMPUTER)));
        System.out.println(zoo.buildReport());
    }
}