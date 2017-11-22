package com.trainline.testing.mvp.presentation;

import com.flextrade.jfixture.FixtureAnnotations;
import com.flextrade.jfixture.JFixture;
import com.flextrade.jfixture.annotations.Fixture;
import com.trainline.testing.TestHelper;
import com.trainline.testing.fixture.EnumSupplier;
import com.trainline.testing.mvp.DeliveryMethodDomain;
import com.trainline.testing.mvp.Formatter;
import com.trainline.testing.mvp.Model;
import com.trainline.testing.mvp.ModelMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class Test4 {
    //region setup
    @Mock Formatter mockFormatter;
    @Fixture
    DeliveryMethodDomain fixtDomain;

    private ModelMapper sut;

    @Before
    public void setUp() {
        JFixture fixture = new JFixture();
        fixture.
                customise(EnumSupplier
                        .excluding(DeliveryMethodDomain.Type.ELECTRONIC));
        FixtureAnnotations.initFixtures(this, fixture);
        MockitoAnnotations.initMocks(this);
        sut = new ModelMapper(mockFormatter);
    }
    //endregion
    //region tests
    @Test
    public void formatsPrice() {
        // init
        String formattedPrice = "Any_formatted_price";
        when(mockFormatter.format(fixtDomain.deliveryFee)).thenReturn(formattedPrice);
        // run
        Model result = sut.call(fixtDomain);
        // verify
        assertEquals(formattedPrice, result.price);
    }

    @Test(expected = IllegalArgumentException.class)
    public void doesNotSupportType() {
        // init
        TestHelper.setField(fixtDomain, "type" ,
                DeliveryMethodDomain.Type.ELECTRONIC);
        // run
        sut.call(fixtDomain);
    }
    //endregion
}
