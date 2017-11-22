package com.trainline.testing.mvp;

import android.support.annotation.NonNull;

import java.math.BigDecimal;

public class DeliveryMethodDomain {
    public final @NonNull Type type;
    public final @NonNull BigDecimal deliveryFee;

    public DeliveryMethodDomain(@NonNull Type type, @NonNull BigDecimal deliveryFee) {
        this.type = type;
        this.deliveryFee = deliveryFee;
    }

    public  enum Type {
        KIOSK, MOBILE, ELECTRONIC,
         POSTAL
    }
}
