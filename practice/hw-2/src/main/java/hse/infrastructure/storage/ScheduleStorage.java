package hse.infrastructure.storage;

import hse.domain.Animal;
import hse.domain.FeedingSchedule;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * Schedule storage.
 */
public class ScheduleStorage {
    /**
     * List of schedules.
     */
    @Getter
    private static final List<FeedingSchedule> schedules = new ArrayList<>(0);

    /**
     * Adds schedule to storage.
     *
     * @param schedule Schedule to add
     */
    public static void addSchedule(FeedingSchedule schedule) {
        schedules.add(schedule);
    }

    /**
     * Changes schedule time of schedule with index ind.
     *
     * @param ind     Schedule index
     * @param newTime New feeding time
     */
    public static void changeSchedule(int ind, LocalTime newTime) {
        schedules.get(ind).changeSchedule(newTime);
    }

    /**
     * Get feeding time of an animal.
     *
     * @param animal Animal
     * @return Time
     */
    public static LocalTime getFeedingTime(Animal animal) {
        for (FeedingSchedule schedule : schedules) {
            if (schedule.getAnimal().equals(animal)) {
                return schedule.getFeedingTime();
            }
        }
        throw new IllegalArgumentException(
            String.format(
                "There is no feeding schedule for %s",
                animal.getName()
            )
        );
    }
}
