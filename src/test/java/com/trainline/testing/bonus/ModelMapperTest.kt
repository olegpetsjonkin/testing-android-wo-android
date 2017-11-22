package com.trainline.testing.bonus

import com.flextrade.jfixture.FixtureAnnotations
import com.flextrade.jfixture.annotations.Fixture
import com.trainline.testing.TestHelper
import com.trainline.testing.helpers.R
import com.trainline.testing.mvp.Formatter
import junitparams.JUnitParamsRunner
import junitparams.NamedParameters
import junitparams.Parameters
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when`

@RunWith(JUnitParamsRunner::class)
class ModelMapperTest {
    companion object {
        const val UNSUPPORTED_TYPES = "UNSUPPORTED_TYPES"
    }

    @Mock lateinit var mockFormatter: Formatter

    @Fixture lateinit var fixtDomain: DeliveryMethodDomain

    lateinit var sut: ModelMapper

    @NamedParameters(UNSUPPORTED_TYPES)
    private fun unsuporortedTypes(): Array<Type> {
        return TestHelper.valuesExcluding<Type>(Type.KIOSK, Type.MOBILE)
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        FixtureAnnotations.initFixtures(this)
        sut = ModelMapper(mockFormatter)
    }

    @Test
    fun formatsPrice() {
        // init
        val formattedLabel = "Any_formatted_label"
        `when`<String>(mockFormatter.format(fixtDomain.deliveryFee)).thenReturn(formattedLabel)
        // run
        val (_, _, formattedPrice) = sut.call(fixtDomain)
        // verify
        assertEquals(formattedLabel, formattedPrice)
    }

    @Test
    fun mapsMobileDelivery() {
        // init
        TestHelper.setField(fixtDomain, "type", Type.MOBILE)
        // run
        val (mostPopularVisible, typeIcon) = sut.call(fixtDomain)
        // verify
        assertTrue(mostPopularVisible)
        assertEquals(R.drawable.ic_delivery_mobile, typeIcon)
    }

    @Test
    @Parameters(named = UNSUPPORTED_TYPES)
    fun mapsUnsupportedTypes(type: Type) {
        // init
        TestHelper.setField(fixtDomain, "type", type)
        // run
        val (mostPopularVisible, typeIcon) = sut.call(fixtDomain)
        // verify
        assertFalse(mostPopularVisible)
        assertEquals(R.drawable.ic_delivery_default, typeIcon)
    }
}

