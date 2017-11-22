package com.trainline.testing.mvp.presentation;

import com.trainline.testing.helpers.R;
import com.trainline.testing.mvp.DeliveryMethodDomain;
import com.trainline.testing.mvp.Formatter;
import com.trainline.testing.mvp.Model;
import com.trainline.testing.mvp.ModelMapper;
import com.trainline.testing.objects.JourneyDomain;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.math.BigDecimal;
import java.net.URI;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class Test1 {
    //region setup
    @Mock Formatter mockFormatter;

    private ModelMapper sut;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sut = new ModelMapper(mockFormatter);
    }
    //endregion
    //region tests
    @Test
    public void formatsPrice() {
        // init
        BigDecimal deliveryFee = BigDecimal.ONE;
        String formattedPrice = "Any_formatted_price";
        DeliveryMethodDomain domain =
                new DeliveryMethodDomain(DeliveryMethodDomain.Type.KIOSK,
                        deliveryFee);
        when(mockFormatter.format(deliveryFee)).thenReturn(formattedPrice);
        // run
        Model result = sut.call(domain);
        // verify
        assertEquals(formattedPrice, result.price);
    }

    @Test
    public void mapsMobileDelivery() {
        // init
        DeliveryMethodDomain domain =
                new DeliveryMethodDomain(DeliveryMethodDomain.Type.MOBILE,
                        BigDecimal.ZERO);
        // run
        Model result = sut.call(domain);
        // verify
        assertTrue(result.mostPopularIconVisible);
        assertEquals(R.drawable.ic_delivery_mobile, result.typeIcon);
    }

    @Test
    public void mapsKioskDelivery() {
        // init
        DeliveryMethodDomain domain =
                new DeliveryMethodDomain(DeliveryMethodDomain.Type.KIOSK,
                        BigDecimal.ZERO);
        // run
        Model result = sut.call(domain);
        // verify
        assertFalse(result.mostPopularIconVisible);
        assertEquals(R.drawable.ic_delivery_kiosk, result.typeIcon);
    }
    //endregion
    //region JourneyDomain
    private JourneyDomain createJourneyDomain() {
        return new JourneyDomain("id", null, null,
                100, Collections.<JourneyDomain.JourneyLegDomain>emptyList(),
                JourneyDomain.JourneyDirection.INBOUND,
                new JourneyDomain.StationDomain("Station1", "1", "1"),
                new JourneyDomain.StationDomain("Station2", "2", "2"));
    }
    //endregion
}
