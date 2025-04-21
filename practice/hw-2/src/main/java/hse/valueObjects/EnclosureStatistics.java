package hse.valueObjects;

/**
 * Record that holds statistics about an enclosure.
 *
 * @param enclosureName Name of the enclosure
 * @param animalType Type of animals in the enclosure
 * @param occupiedPlaces Number of occupied places
 * @param totalPlaces Total number of places in the enclosure
 * @param occupancyPercentage Percentage of occupied places
 */
public record EnclosureStatistics(
    int index,
    AnimalType animalType,
    int occupiedPlaces,
    int totalPlaces,
    double occupancyPercentage
) {
    /**
     * Creates a new EnclosureStatistics instance.
     *
     * @param enclosureName Name of the enclosure
     * @param animalType Type of animals in the enclosure
     * @param occupiedPlaces Number of occupied places
     * @param totalPlaces Total number of places in the enclosure
     */
    public EnclosureStatistics(int ind,AnimalType animalType, int occupiedPlaces, int totalPlaces) {
        this(
            ind,
            animalType,
            occupiedPlaces,
            totalPlaces,
            totalPlaces > 0 ? ((double) occupiedPlaces / totalPlaces) * 100 : 0
        );
    }
} 