package com.trainline.testing.objects;

import android.support.annotation.VisibleForTesting;
import rx.functions.Func1;

import java.util.Date;

public class JourneyModelMapper implements Func1<JourneyDomain, JourneyModel> {

    @Override
    public JourneyModel call(JourneyDomain journeyDomain) {
        return new JourneyModel(
                formatDate(journeyDomain.departureTime),
                mapStop(journeyDomain.departureStation),
                formatDate(journeyDomain.arrivalTime),
                mapStop(journeyDomain.arrivalStation)
        );
    }

    @VisibleForTesting
    JourneyModel.StopModel mapStop(JourneyDomain.StationDomain stationDomain) {
        return new JourneyModel.StopModel(stationDomain.name, "UNKNOWN");
    }

    @VisibleForTesting
    String formatDate(Date date) {
        return "-";
    }
}
