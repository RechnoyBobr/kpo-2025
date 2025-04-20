package hse.valueObjects;

/**
 * Animal type.
 */
public enum AnimalType {
    CARNIVORE,
    HERBIVORE,
    FISH,
    BIRD;

    /**
     * Convert Animal type to string.
     *
     * @return String
     */
    public  String toString() {
        return switch (this) {
            case CARNIVORE -> "Плотоядное";
            case HERBIVORE -> "Травоядное";
            case FISH -> "Рыба";
            case BIRD -> "Птица";
        };
    }
}
