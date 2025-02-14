package org.erpzoo.domains;

import org.erpzoo.parameters.ThingType;

/**
 * Table class.
 */
public class Table extends Thing {
    public Table(int id) {
        super(id);
    }


    @Override
    public ThingType toThingType() {
        return ThingType.TABLE;
    }
}
