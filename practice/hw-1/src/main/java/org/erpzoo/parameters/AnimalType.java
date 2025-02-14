package org.erpzoo.parameters;

/**
 * Animal type.
 */
public enum AnimalType {
    TIGER,
    RABBIT,
    WOLF,
    MONKEY;

    /**
     * Convert Animal type to string.
     *
     * @param animalType Animal type
     *
     * @return String
     */
    public static String toString(AnimalType animalType) {
        return switch (animalType) {
            case TIGER -> "Тигр";
            case RABBIT -> "Кролик";
            case WOLF -> "Волк";
            case MONKEY -> "Обезьяна";
        };
    }
}
