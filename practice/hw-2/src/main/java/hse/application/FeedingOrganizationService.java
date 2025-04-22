package hse.application;

import hse.domain.Animal;
import hse.domain.Enclosure;
import hse.domain.FeedingSchedule;
import hse.domain.events.FeedingTimeEvent;
import hse.infrastructure.storage.EnclosureStorage;
import hse.infrastructure.storage.EventsStorage;
import hse.infrastructure.storage.ScheduleStorage;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service for organizing the feeding process of animals.
 */
@Service
public class FeedingOrganizationService {

    /**
     * Creates a new feeding schedule for an animal.
     *
     * @param animal      The animal to create a schedule for
     * @param feedingTime The time when the animal should be fed
     * @param isCarnivore Whether the animal is carnivorous
     * @return FeedingSchedule
     */
    public FeedingSchedule createFeedingSchedule(Animal animal, LocalTime feedingTime, boolean isCarnivore) {
        FeedingSchedule schedule = new FeedingSchedule(animal, feedingTime, isCarnivore);
        ScheduleStorage.addSchedule(schedule);
        return schedule;
    }

    /**
     * Updates the feeding time for an animal's schedule.
     *
     * @param animal  The animal whose schedule needs to be updated
     * @param newTime The new feeding time
     */
    public void updateFeedingSchedule(Animal animal, LocalTime newTime) {
        List<FeedingSchedule> schedules = ScheduleStorage.getSchedules();
        for (int i = 0; i < schedules.size(); i++) {
            if (schedules.get(i).getAnimal().equals(animal)) {
                ScheduleStorage.changeSchedule(i, newTime);
                return;
            }
        }
        throw new IllegalArgumentException("No feeding schedule found for animal: " + animal.getName());
    }

    /**
     * Processes feeding for all animals at the current time.
     */
    public String processFeeding() {
        LocalTime currentTime = LocalTime.now();
        List<FeedingSchedule> schedules = ScheduleStorage.getSchedules();
        StringBuilder builder = new StringBuilder();
        for (FeedingSchedule schedule : schedules) {
            if (schedule.getFeedingTime().withSecond(0).equals(currentTime.withSecond(0).withNano(0))) {
                Animal animal = schedule.getAnimal();
                Enclosure enclosure = EnclosureStorage.getEnclosureFromName(animal.getName());

                FeedingTimeEvent event = new FeedingTimeEvent(
                    enclosure,
                    animal,
                    schedule,
                    LocalDateTime.now().withSecond(0),
                    animal.getFavouriteFood()
                );
                EventsStorage.addFeedEvent(event);
                builder.append(event);
                schedule.feedAnimal();
            }
        }
        return builder.toString();
    }

    /**
     * Get schedule by name.
     *
     * @param animalName Animal name
     * @return Feeding schedule
     */
    public FeedingSchedule getSchedule(String animalName) {
        var res = ScheduleStorage.getSchedules().stream().filter(
            schedule -> schedule.getAnimal().getName().equals(animalName)).findFirst();
        if (res.isPresent()) {
            return res.get();
        } else {
            throw new IllegalArgumentException("No Schedule found for animal: " + animalName);
        }
    }

    /**
     * Delete feeding schedule by animal name.
     *
     * @param animalName Animal name
     */
    public void deleteFeedingSchedule(String animalName) {
        var res = ScheduleStorage.getSchedules().stream().filter(
            schedule -> schedule.getAnimal().getName().equals(animalName)).findFirst();
        if (res.isPresent()) {
            ScheduleStorage.getSchedules().remove(res.get());
        } else {
            throw new IllegalArgumentException("No Schedule found for animal: " + animalName);
        }
    }

    /**
     * Gets the feeding time for a specific animal.
     *
     * @param animal The animal to check
     * @return The feeding time for the animal
     */
    public LocalTime getFeedingTime(Animal animal) {
        return ScheduleStorage.getFeedingTime(animal);
    }
}
