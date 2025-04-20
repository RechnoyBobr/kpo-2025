package hse.valueObjects;

/**
 * Animal type.
 */
public enum AnimalType {
    CARNIVORE,
    HERBIVORE,
    FISH,
    OMNIVORE,
    BIRD;

    /**
     * Convert Animal type to string.
     *
     * @return String
     */
    public String toString() {
        return switch (this) {
            case CARNIVORE -> "Плотоядное";
            case HERBIVORE -> "Травоядное";
            case FISH -> "Рыба";
            case BIRD -> "Птица";
            case OMNIVORE -> "Всеядное";
        };
    }

    public static AnimalType fromString(final String str) {
        return switch (str) {
            case "CARNIVORE" -> CARNIVORE;
            case "HERBIVORE" -> HERBIVORE;
            case "FISH" -> FISH;
            case "BIRD" -> BIRD;
            case "OMNIVORE" -> OMNIVORE;
            default -> CARNIVORE;
        };
    }
}
