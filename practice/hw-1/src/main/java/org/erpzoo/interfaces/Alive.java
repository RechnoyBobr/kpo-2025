package org.erpzoo.interfaces;

import java.util.Optional;

/**
 * Interface for animals.
 */
public interface Alive {
    /**
     * Returns level of kindness if present.
     *
     * @return Level of kindness if present.
     */
    Optional<Integer> getKindness();

}
