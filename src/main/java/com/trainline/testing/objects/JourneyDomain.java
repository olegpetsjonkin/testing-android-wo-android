package com.trainline.testing.objects;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class JourneyDomain {
    public final @NonNull String id;
    /**
     * can be {@code null} for open return's inbound journey
     */
    public final @Nullable Date departureTime;
    /**
     * can be {@code null} for open return's inbound journey
     */
    public final @Nullable Date arrivalTime;
    public final @IntRange(from = 0) int durationInMinutes;
    /**
     * can be empty (0 length) if it's open return's inbound journey
     */
    public final @NonNull List<JourneyLegDomain> legs;
    public final @NonNull JourneyDirection direction;
    public final @NonNull StationDomain departureStation;
    public final @NonNull StationDomain arrivalStation;

    public JourneyDomain(@NonNull String id,
                         @Nullable Date departureTime,
                         @Nullable Date arrivalTime,
                         @IntRange(from = 0) int durationInMinutes,
                         @NonNull List<JourneyLegDomain> legs,
                         @NonNull JourneyDirection direction,
                         @NonNull StationDomain departureStation,
                         @NonNull StationDomain arrivalStation) {
        this.id = id;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.durationInMinutes = durationInMinutes;
        this.legs = Collections.unmodifiableList(legs);
        this.direction = direction;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
    }
//region JourneyDirection
    public enum JourneyDirection {
        INBOUND,
        OUTBOUND
    }
//endregion
// region JourneyLegDomain
    public static class JourneyLegDomain {

        public final @NonNull String id;
//        public final @NonNull JourneyStopDomain departure;
//        public final @NonNull JourneyStopDomain arrival;
//        public final @IntRange(from = 0) int durationInMinutes;
//        public final @NonNull TransportDomain transport;
//
//        public final @Nullable PlatformInfoDomain departurePlatformInfo;
//        public final @Nullable PlatformInfoDomain arrivalPlatformInfo;
//        /**
//         * Only used in search results.
//         */
//        public final @Nullable LegRealTimeDomain realTime;

        public JourneyLegDomain(@NonNull String id
//                                @NonNull JourneyStopDomain departure,
//                                @NonNull JourneyStopDomain arrival,
//                                @IntRange(from = 0) int durationInMinutes,
//                                @NonNull TransportDomain transport,
//                                @Nullable LegRealTimeDomain realTime,
//                                @Nullable PlatformInfoDomain departurePlatformInfo,
//                                @Nullable PlatformInfoDomain arrivalPlatformInfo
        ) {
            this.id = id;
//            this.departure = departure;
//            this.arrival = arrival;
//            this.durationInMinutes = durationInMinutes;
//            this.transport = transport;
//            this.realTime = realTime;
//            this.departurePlatformInfo = departurePlatformInfo;
//            this.arrivalPlatformInfo = arrivalPlatformInfo;
        }
    }
//endregion
//region StationDomain
    public static class StationDomain {

        public final @NonNull String name;
        /**
         * can be null for coach
         */
        public final @Nullable String code;

        /**
         * can be null for tracs bookings
         */
        public final @Nullable String id;

        public StationDomain(@NonNull String name, @Nullable String code, @Nullable String id) {
            this.name = name;
            this.code = code;
            this.id = id;
        }
    }
//endregion
}
