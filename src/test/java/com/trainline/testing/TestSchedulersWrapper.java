package com.trainline.testing;

import com.trainline.testing.helpers.SchedulersWrapper;
import rx.Scheduler;
import rx.schedulers.Schedulers;

public class TestSchedulersWrapper extends SchedulersWrapper {
    @Override
    public Scheduler background() {
        return Schedulers.immediate();
    }

    @Override
    public Scheduler main() {
        return Schedulers.immediate();
    }
}
