package hse.application;

import hse.domain.Animal;
import hse.domain.Enclosure;
import hse.domain.events.AnimalMovedEvent;
import hse.infrastructure.storage.EnclosureStorage;
import hse.infrastructure.storage.EventsStorage;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

/**
 * Service for animal transfer.
 */
@Service
public class AnimalTransferService {


    /**
     * Transfer animal from one enclosure to another.
     *
     * @param name Animal to transfer
     * @param from Enclosure to transfer from
     * @param to   Enclosure to transfer to
     */
    public AnimalMovedEvent transfer(final String name, int from, int to) {
        final Enclosure enclosure = EnclosureStorage.getEnclosure(from);
        int ind = enclosure.hasAnimal(name);
        if (ind != -1) {
            Animal transferAnimal = enclosure.getAnimal(ind);
            enclosure.removeAnimal(ind);
            EnclosureStorage.getEnclosure(to).addAnimal(transferAnimal);
            AnimalMovedEvent event = new AnimalMovedEvent(
                transferAnimal,
                EnclosureStorage.getEnclosure(from),
                EnclosureStorage.getEnclosure(to),
                LocalDateTime.now()
            );
            EventsStorage.addMoveEvent(event);
            return event;
        } else {
            throw new IllegalArgumentException(
                String.format("No animal %s found in %d enclosure", name, from)
            );
        }


    }

    /**
     * Adds animal to enclosure.
     *
     * @param animal Animal to add
     * @param ind    Enclosure index
     */
    public void addAnimal(final Animal animal, int ind) {
        EnclosureStorage.getEnclosure(ind).addAnimal(animal);
    }

    /**
     * Removes animal from enclosure.
     */
    public void removeAnimal(final String name) {
        for (final Enclosure enclosure : EnclosureStorage.getEnclosures()) {
            int ind = enclosure.hasAnimal(name);
            if (ind != -1) {
                enclosure.removeAnimal(ind);
                return;
            }
        }
        throw new IllegalArgumentException(
            String.format("No animal %s found in the zoo", name)
        );
    }
}
