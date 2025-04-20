package hse.domain;

import java.time.LocalTime;
import lombok.AllArgsConstructor;

/**
 * Schedule of animal feeding.
 */
@AllArgsConstructor
public class FeedingSchedule {

    /**
     * Animal to feed.
     */
    final Animal animal;

    /**
     * Feeding time.
     */
    LocalTime feedingTime;

    /**
     * Type of food.
     */
    final Boolean isCarnivore;

    /**
     * Change schedule of feeding.
     *
     * @param newTime New schedule time
     */
    public void changeSchedule(LocalTime newTime) {
        this.feedingTime = newTime;
    }

    /**
     * Feed animal.
     */
    public void feedAnimal() {
        this.animal.feed();

    }
}
