package org.erpzoo.domains;

import org.erpzoo.interfaces.Inventory;
import org.erpzoo.parameters.ThingType;

/**
 * Computer class.
 */
public class Computer extends Thing {

    public Computer(int id) {
        super(id);
    }


    @Override
    public ThingType toThingType() {
        return ThingType.COMPUTER;
    }

}
