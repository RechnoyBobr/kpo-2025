package hse.domain.events;

import hse.domain.Animal;
import hse.domain.Enclosure;
import hse.domain.FeedingSchedule;
import java.time.LocalDateTime;

/**
 * Domain event that represents a feeding time event for animals in an enclosure.
 */
public record FeedingTimeEvent(Enclosure enclosure, Animal animal, FeedingSchedule schedule, LocalDateTime timestamp,
                               String foodType) {
    @Override
    public String toString() {
        return String.format(
            "Feeding event:\n\t %s,\n\t %s,\n\t At:  %s",
            enclosure.toString(), animal.toString(), timestamp.toString()
        );
    }
}