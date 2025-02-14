package org.erpzoo.parameters;

import org.erpzoo.domains.Thing;

/**
 * Thing type.
 */
public enum ThingType {
    TABLE,
    COMPUTER;

    /**
     * Convert to string.
     *
     * @param thingType Type of thing
     *
     * @return String of thing type.
     */
    public static String toString(ThingType thingType) {
        return switch (thingType) {
            case TABLE -> "Стол";
            case COMPUTER -> "Компьютер";
        };
    }
}
