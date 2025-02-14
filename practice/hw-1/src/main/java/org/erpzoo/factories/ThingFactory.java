package org.erpzoo.factories;

import org.erpzoo.domains.Computer;
import org.erpzoo.domains.Table;
import org.erpzoo.domains.Thing;
import org.erpzoo.parameters.ThingParams;
import org.springframework.stereotype.Component;

/**
 * Thing factory.
 */
@Component
public class ThingFactory {
    int currentId = -1;

    /**
     * Creates thing.
     *
     * @param params Thing parameters
     * @return Thing
     */
    public Thing createThing(ThingParams params) {
        currentId++;
        return switch (params.type()) {
            case TABLE -> new Table(currentId);
            case COMPUTER -> new Computer(currentId);
        };
    }
}
