package org.erpzoo.domains;

import lombok.Getter;
import org.erpzoo.interfaces.Inventory;
import org.erpzoo.parameters.ThingType;

/**
 * Thing class.
 */
@Getter
public abstract class Thing implements Inventory {

    int id;

    Thing(int id) {
        this.id = id;
    }

    public abstract ThingType toThingType();
}
