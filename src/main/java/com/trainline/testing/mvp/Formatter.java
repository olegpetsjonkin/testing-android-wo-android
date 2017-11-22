package com.trainline.testing.mvp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.math.BigDecimal;

public class Formatter {
    public @Nullable String format(@NonNull BigDecimal value) {
        if (BigDecimal.ZERO.compareTo(value) == 0) {
            return null;
        } else {
            return value.toString();
        }
    }

}
