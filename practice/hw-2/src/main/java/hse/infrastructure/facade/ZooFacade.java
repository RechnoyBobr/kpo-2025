package hse.infrastructure.facade;

import hse.application.AnimalTransferService;
import hse.application.FeedingOrganizationService;
import hse.application.ZooStatisticsService;
import hse.domain.Animal;
import hse.domain.Enclosure;
import hse.domain.FeedingSchedule;
import hse.domain.events.AnimalMovedEvent;
import hse.domain.events.FeedingTimeEvent;
import hse.infrastructure.storage.EnclosureStorage;
import hse.infrastructure.storage.EventsStorage;
import hse.infrastructure.storage.ScheduleStorage;
import hse.valueobjects.AnimalParams;
import hse.valueobjects.AnimalStatistics;
import hse.valueobjects.AnimalType;
import hse.valueobjects.EnclosureParams;
import hse.valueobjects.EnclosureStatistics;
import hse.valueobjects.FeedingScheduleParams;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Facade for zoo management.
 * Provides a simplified interface to the zoo management system,
 * coordinating between different services and storage components.
 */
@RequiredArgsConstructor
@Component
public class ZooFacade {
    private final AnimalTransferService transferService;
    private final FeedingOrganizationService feedingService;
    private final ZooStatisticsService statisticsService;

    /**
     * Adds a new animal to the zoo.
     *
     * @param params Parameters for creating the animal
     * @return Created animal
     * @throws IllegalStateException if no compatible enclosure is found
     */
    public Animal addAnimal(final AnimalParams params) {
        AnimalType type = AnimalType.fromString(params.type());
        Animal animal = new Animal(params.name(), type, params.specie(), params.birthDate(),
            params.isMale(), params.favouriteFood(), params.isHealthy());
        int ind = EnclosureStorage.getCompatibleEnclosureInd(type);
        transferService.addAnimal(animal, ind);
        return animal;
    }

    /**
     * Removes an animal from the zoo.
     *
     * @param name Name of the animal to remove
     * @throws IllegalStateException if animal is not found
     */
    public void removeAnimal(final String name) {
        transferService.removeAnimal(name);
    }

    /**
     * Retrieves an animal by its name.
     *
     * @param name Name of the animal
     * @return The animal with the specified name
     * @throws IllegalStateException if animal is not found
     */
    public Animal getAnimal(final String name) {
        return EnclosureStorage.getAnimalFromName(name);
    }

    /**
     * Transfers an animal from one enclosure to another.
     *
     * @param name Name of the animal to transfer
     * @param from Index of the source enclosure
     * @param to   Index of the destination enclosure
     * @throws IllegalStateException if animal or enclosures are not found
     */
    public AnimalMovedEvent transferAnimal(final String name, final int from, final int to) {
        return transferService.transfer(name, from, to);
    }

    /**
     * Retrieves statistics about animals in the zoo.
     *
     * @return Animal statistics including counts by type, species, and health status
     */
    public AnimalStatistics getAnimalsStats() {
        return new AnimalStatistics(statisticsService.getAllAnimalsInfo(), statisticsService.getAnimalsByType(),
            statisticsService.getSickAnimalsInfo(), statisticsService.getAnimalsBySpecies(),
            statisticsService.getTotalAnimalsCount());
    }

    /**
     * Creates a new enclosure in the zoo.
     *
     * @param params Parameters for creating the enclosure
     * @return Created enclosure
     */
    public Enclosure createEnclosure(EnclosureParams params) {
        AnimalType type = AnimalType.fromString(params.type());
        Enclosure enclosure = new Enclosure(type, params.maxCapacity());
        EnclosureStorage.addEnclosure(enclosure);
        return enclosure;
    }

    /**
     * Deletes an enclosure from the zoo.
     *
     * @param ind Index of the enclosure to delete
     * @throws IndexOutOfBoundsException if enclosure index is invalid
     */
    public void deleteEnclosure(int ind) {
        EnclosureStorage.deleteEnclosure(ind);
    }

    /**
     * Retrieves statistics about enclosures in the zoo.
     *
     * @return List of enclosure statistics including occupancy and type information
     */
    public List<EnclosureStatistics> getEnclosureStats() {
        return statisticsService.getEnclosuresStatistics();
    }

    /**
     * Retrieves information about a specific enclosure.
     *
     * @param ind Index of the enclosure
     * @return String representation of the enclosure
     * @throws IndexOutOfBoundsException if enclosure index is invalid
     */
    public String getEnclosure(Integer ind) {
        return EnclosureStorage.getEnclosure(ind).toString();
    }

    /**
     * Creates a new feeding schedule for an animal.
     *
     * @param params Parameters for creating the schedule
     * @return Created feeding schedule
     * @throws IllegalStateException if animal is not found
     */
    public FeedingSchedule createFeedingSchedule(FeedingScheduleParams params) {
        Animal animal = EnclosureStorage.getAnimalFromName(params.animalName());
        return feedingService.createFeedingSchedule(animal, params.feedingTime(), params.isCarnivore());
    }

    /**
     * Updates the feeding time for an animal's schedule.
     *
     * @param animalName Name of the animal
     * @param newTime    New feeding time
     * @return Updated feeding schedule
     * @throws IllegalStateException if animal is not found
     */
    public FeedingSchedule updateFeedingSchedule(String animalName, LocalTime newTime) {
        Animal animal = EnclosureStorage.getAnimalFromName(animalName);
        feedingService.updateFeedingSchedule(animal, newTime);
        return feedingService.getSchedule(animalName);
    }

    /**
     * Deletes a feeding schedule for an animal.
     *
     * @param animalName Name of the animal
     * @throws IllegalStateException if animal is not found
     */
    public void deleteFeedingSchedule(String animalName) {
        feedingService.deleteFeedingSchedule(animalName);
    }

    /**
     * Gets the feeding schedule for an animal.
     *
     * @param animalName Name of the animal
     * @return Feeding schedule for the animal
     * @throws IllegalStateException if animal is not found
     */
    public String getFeedingSchedule(String animalName) {
        return feedingService.getSchedule(animalName).toString();
    }

    /**
     * Gets all feeding schedules in the zoo.
     *
     * @return List of all feeding schedules
     */
    public String getAllFeedingSchedules() {
        return String.format(
            "Schedules: \n\t %s",
            ScheduleStorage.getSchedules().stream().map(FeedingSchedule::toString).collect(Collectors.joining("\n\t"))
        );
    }

    /**
     * Feed animals.
     *
     * @return Report
     */
    public String feedAnimals() {
        return feedingService.processFeeding();
    }

    /**
     * Get moved events.
     *
     * @return Report
     */
    public String getMovedEvents() {
        return String.format(
            "Moving events: \n\t %s",
            EventsStorage.getMoveEvents().stream().map(AnimalMovedEvent::toString).collect(Collectors.joining("\n\t"))
        );
    }

    /**
     * Get feeding events.
     *
     * @return Report
     */
    public String getFeedingEvents() {
        return String.format(
            "Feeding events: \n\t %s",
            EventsStorage.getFeedEvents().stream().map(FeedingTimeEvent::toString).collect(Collectors.joining("\n\t"))
        );
    }

    /**
     * Flush all storages.
     */
    public void flush() {
        EnclosureStorage.getEnclosures().clear();
        ScheduleStorage.getSchedules().clear();
        EventsStorage.getFeedEvents().clear();
        EventsStorage.getMoveEvents().clear();
    }
}
