package hse.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import hse.domain.Animal;
import hse.domain.Enclosure;
import hse.domain.events.AnimalMovedEvent;
import hse.infrastructure.storage.EnclosureStorage;
import hse.infrastructure.storage.EventsStorage;
import hse.valueobjects.AnimalType;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AnimalTransferServiceTest {

    @InjectMocks
    private AnimalTransferService service;

    private Animal animal;
    private Enclosure sourceEnclosure;
    private Enclosure targetEnclosure;

    @BeforeEach
    void setUp() {
        // Clear storage before each test
        EnclosureStorage.getEnclosures().clear();
        EventsStorage.getMoveEvents().clear();
        EventsStorage.getFeedEvents().clear();

        // Create test animal
        animal = new Animal(
            "TestAnimal",
            AnimalType.CARNIVORE,
            "Lion",
            LocalDate.now(),
            true,
            "Meat",
            true
        );

        // Create test enclosures
        sourceEnclosure = new Enclosure(
            AnimalType.CARNIVORE,
            20
        );

        targetEnclosure = new Enclosure(
            AnimalType.CARNIVORE,
            20
        );

        // Add enclosures to storage
        EnclosureStorage.addEnclosure(sourceEnclosure);
        EnclosureStorage.addEnclosure(targetEnclosure);
    }

    @Test
    void transfer_ShouldMoveAnimalAndCreateEvent() {
        // Arrange
        sourceEnclosure.addAnimal(animal);
        int sourceIndex = EnclosureStorage.getEnclosures().indexOf(sourceEnclosure);
        int targetIndex = EnclosureStorage.getEnclosures().indexOf(targetEnclosure);

        // Act
        AnimalMovedEvent event = service.transfer(animal.getName(), sourceIndex, targetIndex);

        // Assert
        assertNotNull(event);
        assertEquals(animal, event.animal());
        assertEquals(sourceEnclosure, event.fromEnclosure());
        assertEquals(targetEnclosure, event.toEnclosure());
        assertTrue(targetEnclosure.hasAnimal(animal.getName()) != -1);
        assertTrue(sourceEnclosure.hasAnimal(animal.getName()) == -1);
        assertTrue(EventsStorage.getMoveEvents().contains(event));
    }

    @Test
    void transfer_WhenAnimalNotFound_ShouldThrowException() {
        // Arrange
        int sourceIndex = EnclosureStorage.getEnclosures().indexOf(sourceEnclosure);
        int targetIndex = EnclosureStorage.getEnclosures().indexOf(targetEnclosure);

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
            () -> service.transfer("NonExistentAnimal", sourceIndex, targetIndex));
    }

    @Test
    void addAnimal_ShouldAddAnimalToEnclosure() {
        // Arrange
        int enclosureIndex = EnclosureStorage.getEnclosures().indexOf(sourceEnclosure);

        // Act
        service.addAnimal(animal, enclosureIndex);

        // Assert
        assertTrue(sourceEnclosure.hasAnimal(animal.getName()) != -1);
    }

    @Test
    void removeAnimal_ShouldRemoveAnimalFromEnclosure() {
        // Arrange
        sourceEnclosure.addAnimal(animal);

        // Act
        service.removeAnimal(animal.getName());

        // Assert
        assertTrue(sourceEnclosure.hasAnimal(animal.getName()) == -1);
    }

    @Test
    void removeAnimal_WhenAnimalNotFound_ShouldThrowException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
            () -> service.removeAnimal("NonExistentAnimal"));
    }
} 