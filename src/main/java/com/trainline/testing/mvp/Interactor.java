package com.trainline.testing.mvp;


import rx.Single;

import java.math.BigDecimal;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Interactor {

    public Single<DeliveryMethodDomain> getDomain() {
        return Single.fromCallable(new Callable<DeliveryMethodDomain>() {
            public DeliveryMethodDomain call() throws Exception {
                return new DeliveryMethodDomain(DeliveryMethodDomain.Type.MOBILE, BigDecimal.TEN);
                //return new DeliveryMethodDomain(DeliveryMethodDomain.Type.KIOSK, BigDecimal.ZERO);
                // throw new Exception("Unknown error");
            }
        }).delay(1, TimeUnit.SECONDS);
    }
}
