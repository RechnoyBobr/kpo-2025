package hse.infrastructure.storage;

import hse.domain.Animal;
import hse.domain.Enclosure;
import hse.valueObjects.AnimalType;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * Storages all zoo enclosures.
 */
public class EnclosureStorage {
    /**
     * List of enclosures.
     */
    @Getter
    private static final List<Enclosure> enclosures = new ArrayList<>(0);

    /**
     * Delete enclosure.
     *
     * @param ind Index of enclosure
     */
    public static void deleteEnclosure(int ind) {
        enclosures.remove(ind);
    }

    /**
     * Add enclosure to the zoo.
     *
     * @param enc Enclosure to add
     */
    public static void addEnclosure(Enclosure enc) {
        enclosures.add(enc);
    }

    /**
     * Gets an enclosure.
     *
     * @param ind Index of enclosure.
     * @return Enclosure
     */
    public static Enclosure getEnclosure(int ind) {
        return enclosures.get(ind);
    }

    /**
     * Get animal from his name.
     */
    public static Animal getAnimalFromName(String name) {
        for (Enclosure enc : enclosures) {
            int ind = enc.hasAnimal(name);
            if (ind != -1) {
                return enc.getAnimal(ind);
            }
        }
        throw new IllegalStateException(
            String.format("No animal with name %s found", name)
        );
    }

    public static int getCompatibleEnclosureInd(AnimalType type) {
        for (int i = 0; i < enclosures.size(); i++) {
            Enclosure enc = enclosures.get(i);
            if (enc.getType().equals(type)) {
                return i;
            }
        }
        throw new IllegalStateException(
            String.format("No compatible enclosure found for animal type %s", type.toString())
        );
    }

    public static Enclosure getEnclosureFromName(String name) {
        for (Enclosure enc : enclosures) {
            int ind = enc.hasAnimal(name);
            if (ind != -1) {
                return enc;
            }
        }
        throw new IllegalStateException(
            String.format("No enclosure with animal %s found", name)
        );
    }
}
