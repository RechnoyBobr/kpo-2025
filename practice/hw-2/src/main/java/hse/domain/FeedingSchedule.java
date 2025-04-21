package hse.domain;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Schedule of animal feeding.
 */
@AllArgsConstructor
@Getter
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

    @Override
    public String toString() {
        return String.format(
            "Feeding Schedule: \n\t %s, \n\t Feeding time: %s,\n\t Food type: %s\n",
            this.animal.toString(), this.feedingTime.toString(), this.isCarnivore ? "Meat" : "Plants"
        );
    }
}
