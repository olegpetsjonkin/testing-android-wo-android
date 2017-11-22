package com.trainline.testing.objects;

import com.flextrade.jfixture.JFixture;
import com.trainline.testing.TestHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;


public class JourneyModelMapperTest {
    //region setup
    private JourneyModelMapper sut;
    private JFixture fixture;

    @Before
    public void setUp() {
        sut = new JourneyModelMapper();
        fixture = new JFixture();
    }
    //endregion
    //region stop
    @Test
    public void mapsStop() {
        //init
        JourneyDomain.StationDomain fixtStation = fixture.create(JourneyDomain.StationDomain.class);
        //run
        JourneyModel.StopModel result = sut.mapStop(fixtStation);
        //verify
        assertEquals(fixtStation.name, result.name);
        assertEquals("UNKNOWN", result.platform);
    }
    //endregion
    //region date
    @Test
    public void formatsDate() {
        //init
        Date fixtDate = fixture.create(Date.class);
        //run
        String result = sut.formatDate(fixtDate);
        //verify
        assertEquals("-", result);
    }
    //endregion
    //region partial mock
    @Test
    public void mapsDomain() {
        //init
        JourneyModelMapper spySut = spy(sut);

        JourneyDomain fixtJourney = fixture.create(JourneyDomain.class);
        JourneyModel fixtExpectedModel = fixture.create(JourneyModel.class);

        //when(spySut.mapStop(fixtJourney.arrivalStation)).thenReturn(fixtExpectedModel.arrivalStation);
        doReturn(fixtExpectedModel.arrivalStation)
                .when(spySut).mapStop(fixtJourney.arrivalStation);
        doReturn(fixtExpectedModel.departureStation)
                .when(spySut).mapStop(fixtJourney.departureStation);
        doReturn(fixtExpectedModel.arrivalTime)
                .when(spySut).formatDate(fixtJourney.arrivalTime);
        doReturn(fixtExpectedModel.departureTime)
                .when(spySut).formatDate(fixtJourney.departureTime);
        //run
        JourneyModel result = spySut.call(fixtJourney);
        //verify
        TestHelper.setField(fixtExpectedModel, "arrivalTime", "+");
        assertThat(result, sameBeanAs(fixtExpectedModel));
    }
    //endregion
}