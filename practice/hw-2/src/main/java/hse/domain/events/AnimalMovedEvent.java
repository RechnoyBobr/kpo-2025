package hse.domain.events;

import hse.domain.Animal;
import hse.domain.Enclosure;
import java.time.LocalDateTime;

/**
 * Domain event that represents an animal being moved from one enclosure to another.
 */
public record AnimalMovedEvent(Animal animal, Enclosure fromEnclosure, Enclosure toEnclosure, LocalDateTime timestamp) {
    @Override
    public String toString() {
        return String.format(
            "Moving event: \n\t %s,\n\t %s,\n\t  %s,\n\t At: %s",
            animal.toString(),
            fromEnclosure.toString(),
            toEnclosure.toString(),
            timestamp.toString()
        );
    }
}