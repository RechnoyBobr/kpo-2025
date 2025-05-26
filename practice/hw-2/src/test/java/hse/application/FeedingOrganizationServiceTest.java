package hse.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import hse.domain.Animal;
import hse.domain.Enclosure;
import hse.domain.FeedingSchedule;
import hse.infrastructure.storage.EnclosureStorage;
import hse.infrastructure.storage.EventsStorage;
import hse.infrastructure.storage.ScheduleStorage;
import hse.valueobjects.AnimalType;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FeedingOrganizationServiceTest {

    @InjectMocks
    private FeedingOrganizationService service;

    private Animal animal;
    private Enclosure enclosure;
    private LocalTime feedingTime;

    @BeforeEach
    void setUp() {
        // Clear storage before each test
        EnclosureStorage.getEnclosures().clear();
        EventsStorage.getMoveEvents().clear();
        EventsStorage.getFeedEvents().clear();
        ScheduleStorage.getSchedules().clear();

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

        // Create test enclosure
        enclosure = new Enclosure(
            AnimalType.CARNIVORE,
            20
        );

        // Add animal to enclosure
        enclosure.addAnimal(animal);

        // Add enclosure to storage
        EnclosureStorage.addEnclosure(enclosure);

        // Set test feeding time
        feedingTime = LocalTime.now().withSecond(0).withNano(0);
    }

    @Test
    void createFeedingSchedule_ShouldCreateAndStoreSchedule() {
        // Act
        FeedingSchedule schedule = service.createFeedingSchedule(animal, feedingTime, true);

        // Assert
        assertNotNull(schedule);
        assertEquals(animal, schedule.getAnimal());
        assertEquals(feedingTime, schedule.getFeedingTime());
        assertTrue(schedule.getIsCarnivore());
        assertTrue(ScheduleStorage.getSchedules().contains(schedule));
    }

    @Test
    void updateFeedingSchedule_ShouldUpdateScheduleTime() {
        // Arrange
        service.createFeedingSchedule(animal, feedingTime, true);
        LocalTime newTime = feedingTime.plusHours(1);

        // Act
        service.updateFeedingSchedule(animal, newTime);

        // Assert
        assertEquals(newTime, ScheduleStorage.getFeedingTime(animal));
    }

    @Test
    void updateFeedingSchedule_WhenScheduleNotFound_ShouldThrowException() {
        // Arrange
        LocalTime newTime = feedingTime.plusHours(1);

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
            () -> service.updateFeedingSchedule(animal, newTime));
    }

    @Test
    void processFeeding_ShouldCreateFeedingEvent() {
        // Arrange
        service.createFeedingSchedule(animal, feedingTime, true);

        // Act
        String result = service.processFeeding();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(EventsStorage.getFeedEvents().stream()
            .anyMatch(event -> event.animal().equals(animal)));
    }

    @Test
    void getSchedule_ShouldReturnCorrectSchedule() {
        // Arrange
        service.createFeedingSchedule(animal, feedingTime, true);

        // Act
        FeedingSchedule schedule = service.getSchedule(animal.getName());

        // Assert
        assertNotNull(schedule);
        assertEquals(animal, schedule.getAnimal());
        assertEquals(feedingTime, schedule.getFeedingTime());
    }

    @Test
    void getSchedule_WhenScheduleNotFound_ShouldThrowException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
            () -> service.getSchedule(animal.getName()));
    }

    @Test
    void deleteFeedingSchedule_ShouldRemoveSchedule() {
        // Arrange
        service.createFeedingSchedule(animal, feedingTime, true);

        // Act
        service.deleteFeedingSchedule(animal.getName());

        // Assert
        assertThrows(IllegalArgumentException.class,
            () -> service.getSchedule(animal.getName()));
    }

    @Test
    void deleteFeedingSchedule_WhenScheduleNotFound_ShouldThrowException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
            () -> service.deleteFeedingSchedule(animal.getName()));
    }

    @Test
    void getFeedingTime_ShouldReturnCorrectTime() {
        // Arrange
        service.createFeedingSchedule(animal, feedingTime, true);

        // Act
        LocalTime time = service.getFeedingTime(animal);

        // Assert
        assertEquals(feedingTime, time);
    }
} 