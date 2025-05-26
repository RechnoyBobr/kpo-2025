package hse.infrastructure.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import hse.domain.Animal;
import hse.domain.Enclosure;
import hse.domain.FeedingSchedule;
import hse.domain.events.AnimalMovedEvent;
import hse.domain.events.FeedingTimeEvent;
import hse.infrastructure.storage.EnclosureStorage;
import hse.infrastructure.storage.EventsStorage;
import hse.infrastructure.storage.ScheduleStorage;
import hse.valueobjects.AnimalParams;
import hse.valueobjects.AnimalType;
import hse.valueobjects.EnclosureParams;
import hse.valueobjects.FeedingScheduleParams;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ZooFacadeTest {
    @Autowired
    private ZooFacade zooFacade;

    private AnimalParams animalParams;
    private EnclosureParams enclosureParams;
    private FeedingScheduleParams feedingScheduleParams;

    @AfterEach
    void flush() {
        zooFacade.flush();
    }

    @BeforeEach
    void setUp() {
        // Clear storage before each test
        EnclosureStorage.getEnclosures().clear();
        EventsStorage.getMoveEvents().clear();
        EventsStorage.getFeedEvents().clear();
        ScheduleStorage.getSchedules().clear();

        // Setup test data
        animalParams = new AnimalParams(
            "TestAnimal",
            "CARNIVORE",
            "Lion",
            LocalDate.now(),
            true,
            "Meat",
            true
        );

        enclosureParams = new EnclosureParams(
            "CARNIVORE",
            5
        );

        feedingScheduleParams = new FeedingScheduleParams(
            "TestAnimal",
            LocalTime.of(12, 0),
            true
        );
    }

    @Test
    void addAnimal_ShouldCreateAnimalAndAddToEnclosure() {
        // Arrange
        Animal expectedAnimal = new Animal(
            animalParams.name(),
            AnimalType.CARNIVORE,
            animalParams.specie(),
            animalParams.birthDate(),
            animalParams.isMale(),
            animalParams.favouriteFood(),
            animalParams.isHealthy()
        );
        zooFacade.createEnclosure(enclosureParams);
        Animal result = zooFacade.addAnimal(animalParams);

        // Assert
        assertNotNull(result);
        assertEquals(expectedAnimal.getName(), zooFacade.getAnimal(animalParams.name()).getName());
    }

    @Test
    void createEnclosure_ShouldCreateAndAddEnclosure() {
        // Act
        Enclosure result = zooFacade.createEnclosure(enclosureParams);

        // Assert
        assertNotNull(result);
        assertEquals(AnimalType.CARNIVORE, result.getType());
        assertEquals(5, result.getMaxAnimals());
        assertTrue(EnclosureStorage.getEnclosures().contains(result));
    }

    @Test
    void createFeedingSchedule_ShouldCreateSchedule() {
        // Arrange
        Animal animal = new Animal(
            "TestAnimal",
            AnimalType.CARNIVORE,
            "Lion",
            LocalDate.now(),
            true,
            "Meat",
            true
        );
        EnclosureStorage.addEnclosure(new Enclosure(AnimalType.CARNIVORE, 5));
        EnclosureStorage.getEnclosure(0).addAnimal(animal);

        FeedingSchedule expectedSchedule = new FeedingSchedule(
            animal,
            LocalTime.of(12, 0),
            true
        );

        // Act
        FeedingSchedule result = zooFacade.createFeedingSchedule(feedingScheduleParams);

        // Assert
        assertNotNull(result);
        assertEquals(expectedSchedule.getAnimal().getName(), result.getAnimal().getName());

    }

    @Test
    void transferAnimal_ShouldCreateMoveEvent() {
        // Arrange
        Animal animal = new Animal("TestAnimal", AnimalType.CARNIVORE, "Lion", LocalDate.now(), true, "Meat", true);
        Enclosure enc = new Enclosure(AnimalType.CARNIVORE, 5);
        zooFacade.createEnclosure(enclosureParams);
        zooFacade.createEnclosure(enclosureParams);
        zooFacade.addAnimal(animalParams);
        AnimalMovedEvent expectedEvent = new AnimalMovedEvent(
            animal,
            enc,
            enc,
            LocalDateTime.MAX
        );

        // Act
        AnimalMovedEvent result = zooFacade.transferAnimal("TestAnimal", 0, 1);

        // Assert
        assertNotNull(result);
        assertEquals(expectedEvent.animal().getName(), result.animal().getName());
    }

    @Test
    void getFeedingEvents_ShouldReturnFormattedEvents() {
        // Arrange
        FeedingTimeEvent event = new FeedingTimeEvent(
            new Enclosure(AnimalType.CARNIVORE, 5),
            new Animal("TestAnimal", AnimalType.CARNIVORE, "Lion", LocalDate.now(), true, "Meat", true),
            new FeedingSchedule(
                new Animal("TestAnimal", AnimalType.CARNIVORE, "Lion", LocalDate.now(), true, "Meat", true),
                LocalTime.of(12, 0),
                true
            ),
            LocalDateTime.now(),
            "Meat"
        );
        EventsStorage.addFeedEvent(event);

        // Act
        String result = zooFacade.getFeedingEvents();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("Feeding events"));
        assertTrue(result.contains("TestAnimal"));
    }

    @Test
    void getMovedEvents_ShouldReturnFormattedEvents() {
        // Arrange
        AnimalMovedEvent event = new AnimalMovedEvent(
            new Animal("TestAnimal", AnimalType.CARNIVORE, "Lion", LocalDate.now(), true, "Meat", true),
            new Enclosure(AnimalType.CARNIVORE, 5),
            new Enclosure(AnimalType.CARNIVORE, 5),
            LocalDateTime.now()
        );
        EventsStorage.addMoveEvent(event);

        // Act
        String result = zooFacade.getMovedEvents();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("Moving events"));
        assertTrue(result.contains("TestAnimal"));
    }
} 