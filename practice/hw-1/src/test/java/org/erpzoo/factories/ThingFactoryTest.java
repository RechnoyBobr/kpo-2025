package org.erpzoo.factories;

import org.erpzoo.domains.Computer;
import org.erpzoo.domains.Table;
import org.erpzoo.domains.Thing;
import org.erpzoo.parameters.ThingParams;
import org.erpzoo.parameters.ThingType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for {@link  ThingFactory}.
 */
@SpringBootTest
public class ThingFactoryTest {

    @Autowired
    private ThingFactory thingFactory;

    @Test
    void createsThing() {
        Thing thing = thingFactory.createThing(new ThingParams(ThingType.COMPUTER));
        Thing thing1 = thingFactory.createThing(new ThingParams(ThingType.TABLE));
        Assertions.assertAll(
            () -> Assertions.assertInstanceOf(Computer.class, thing),
            () -> Assertions.assertInstanceOf(Table.class, thing1),
            () -> Assertions.assertEquals(thing.getId(), thing1.getId() - 1)
        );
    }
}
