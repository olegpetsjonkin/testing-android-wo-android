package com.trainline.testing.mvp.presentation;

import com.flextrade.jfixture.FixtureAnnotations;
import com.flextrade.jfixture.JFixture;
import com.flextrade.jfixture.annotations.Fixture;
import com.trainline.testing.TestHelper;
import com.trainline.testing.helpers.R;
import com.trainline.testing.mvp.DeliveryMethodDomain;
import com.trainline.testing.mvp.Formatter;
import com.trainline.testing.mvp.Model;
import com.trainline.testing.mvp.ModelMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class Test2 {
    //region setup
    @Mock Formatter mockFormatter;
    @Fixture DeliveryMethodDomain fixtDomain;

    private ModelMapper sut;

    @Before
    public void setUp() {
        FixtureAnnotations.initFixtures(this);
        MockitoAnnotations.initMocks(this);
        sut = new ModelMapper(mockFormatter);
    }
    //endregion
    //region tests
    @Test
    public void formatsPrice() {
        // init
        String formattedPrice = "Any_formatted_price";
        when(mockFormatter.format(fixtDomain.deliveryFee))
                .thenReturn(formattedPrice);
        // run
        Model result = sut.call(fixtDomain);
        // verify
        assertEquals(formattedPrice, result.price);
    }

    @Test
    public void mapsMobileDelivery1() {
        // init
        JFixture fixture = new JFixture();
        fixture.customise().sameInstance(DeliveryMethodDomain.Type.class,
                DeliveryMethodDomain.Type.MOBILE);
        fixtDomain = fixture.create(DeliveryMethodDomain.class);
        // run
        Model result = sut.call(fixtDomain);
        // verify
        assertTrue(result.mostPopularIconVisible);
        assertEquals(R.drawable.ic_delivery_mobile, result.typeIcon);
    }

    @Test
    public void mapsMobileDelivery2() {
        // init
        TestHelper.setField(fixtDomain, "type" ,
                DeliveryMethodDomain.Type.MOBILE);
        // run
        Model result = sut.call(fixtDomain);
        // verify
        assertTrue(result.mostPopularIconVisible);
        assertEquals(R.drawable.ic_delivery_mobile, result.typeIcon);
    }

    @Test
    public void mapsKioskDelivery() {
        // init
        TestHelper.setField(fixtDomain, "type" ,
                DeliveryMethodDomain.Type.KIOSK);
        // run
        Model result = sut.call(fixtDomain);
        // verify
        assertFalse(result.mostPopularIconVisible);
        assertEquals(R.drawable.ic_delivery_kiosk, result.typeIcon);
    }
    //endregion
    //region test C
    @Test
    public void showsUnsupportedDelivery() {
        // init
        TestHelper.setField(fixtDomain, "type" ,
                DeliveryMethodDomain.Type.ELECTRONIC); //DeliveryMethodDomain.Type.POSTAL
        // run
        Model result = sut.call(fixtDomain);
        // verify
        assertFalse(result.mostPopularIconVisible);
        assertEquals(R.drawable.ic_delivery_default, result.typeIcon);
    }
    //endregion

}
