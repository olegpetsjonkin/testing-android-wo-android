package com.trainline.testing.mvp;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;


public interface View {
    void showErrorMessage(@Nullable String errorMessage);

    void showMostPopular(boolean show);

    void setDeliveryMethodIcon(@DrawableRes int iconId);

    void setPrice(@Nullable String price);
}
