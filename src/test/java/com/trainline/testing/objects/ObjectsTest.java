package com.trainline.testing.objects;

import com.flextrade.jfixture.FixtureBehaviour;
import com.flextrade.jfixture.JFixture;
import com.flextrade.jfixture.SpecimenBuilder;
import com.flextrade.jfixture.behaviours.recursion.ThrowingRecursionBehaviour;
import com.trainline.testing.fixture.RecursionCounter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ObjectsTest {

    private JFixture fixture;

    @Before
    public void setUp() {
        fixture = new JFixture();
    }

    @Test
    public void testNode() {
        //fixture.customise().circularDependencyBehaviour().omitSpecimen();
        fixture.behaviours().remove(ThrowingRecursionBehaviour.class);
        fixture.behaviours().add(new FixtureBehaviour() {
            public SpecimenBuilder transform(SpecimenBuilder builder) {
                return new RecursionCounter(builder, 3);
            }
        });

        Node fixtNode = fixture.create(Node.class);

        assertNotNull(fixtNode);
    }

}