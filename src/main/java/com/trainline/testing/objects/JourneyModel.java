package com.trainline.testing.objects;

import android.support.annotation.NonNull;

public class JourneyModel {

    public final @NonNull String departureTime;
    public final @NonNull StopModel departureStation;
    public final @NonNull String arrivalTime;
    public final @NonNull StopModel arrivalStation;

    public JourneyModel(@NonNull String departureTime,
                        @NonNull StopModel departureStation,
                        @NonNull String arrivalTime,
                        @NonNull StopModel arrivalStation) {
        this.departureTime = departureTime;
        this.departureStation = departureStation;
        this.arrivalTime = arrivalTime;
        this.arrivalStation = arrivalStation;
    }

    public static class StopModel {
        public final @NonNull String name;
        public final @NonNull String platform;

        public StopModel(@NonNull String name, @NonNull String platform) {
            this.name = name;
            this.platform = platform;
        }
    }
}
