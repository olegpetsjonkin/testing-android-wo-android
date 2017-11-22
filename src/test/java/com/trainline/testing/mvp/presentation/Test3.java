package com.trainline.testing.mvp.presentation;

import com.flextrade.jfixture.FixtureAnnotations;
import com.flextrade.jfixture.annotations.Fixture;
import com.trainline.testing.TestHelper;
import com.trainline.testing.helpers.R;
import com.trainline.testing.mvp.DeliveryMethodDomain;
import com.trainline.testing.mvp.Formatter;
import com.trainline.testing.mvp.Model;
import com.trainline.testing.mvp.ModelMapper;
import junitparams.JUnitParamsRunner;
import junitparams.NamedParameters;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class Test3 {
    //region setup
    private static final String UNSUPPORTED_TYPES = "UNSUPPORTED_TYPES";

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
        when(mockFormatter.format(fixtDomain.deliveryFee)).thenReturn(formattedPrice);
        // run
        Model result = sut.call(fixtDomain);
        // verify
        assertEquals(formattedPrice, result.price);
    }

    @NamedParameters(UNSUPPORTED_TYPES)
    private static DeliveryMethodDomain.Type[] unsuporortedTypes() {
        return TestHelper.valuesExcluding(DeliveryMethodDomain.Type.MOBILE,
                DeliveryMethodDomain.Type.KIOSK);
    }

    @Test
    @Parameters(named = UNSUPPORTED_TYPES)
    public void hidesAllForUnsupportedTypes(DeliveryMethodDomain.Type type) {
        // init
        TestHelper.setField(fixtDomain, "type" , type);
        // run
        Model result = sut.call(fixtDomain);
        // verify
        assertFalse(result.mostPopularIconVisible);
        assertEquals(R.drawable.ic_delivery_default, result.typeIcon);
    }
    //endregion
}
