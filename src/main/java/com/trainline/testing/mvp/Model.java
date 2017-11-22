package com.trainline.testing.mvp;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;

public class Model {
    public final boolean mostPopularIconVisible;
    public final @DrawableRes int typeIcon;
    public final @Nullable String price;

    public Model(boolean mostPopularIconVisible,
                 @DrawableRes int typeIcon,
                 @Nullable String price) {
        this.mostPopularIconVisible = mostPopularIconVisible;
        this.typeIcon = typeIcon;
        this.price = price;
    }
}
