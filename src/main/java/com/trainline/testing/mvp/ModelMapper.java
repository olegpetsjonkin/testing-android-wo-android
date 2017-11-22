package com.trainline.testing.mvp;

import android.support.annotation.NonNull;
import com.trainline.testing.helpers.R;
import rx.functions.Func1;

import java.net.URI;

public class ModelMapper implements Func1<DeliveryMethodDomain, Model> {
    private final @NonNull Formatter formatter;

    public ModelMapper(@NonNull Formatter formatter) {
        this.formatter = formatter;
    }

    public Model call(DeliveryMethodDomain domain) {
        return map3(domain);
    }
    //region map
    private Model map(DeliveryMethodDomain domain) {
        String price = formatter.format(domain.deliveryFee);
        boolean showMostPopular = false;
        int icon = R.drawable.ic_delivery_default;
        switch (domain.type) {
            case MOBILE:
                showMostPopular = true;
                icon = R.drawable.ic_delivery_mobile;
                break;
            case KIOSK:
                icon = R.drawable.ic_delivery_kiosk;
                break;
            default:
                break;
        }
        return new Model(showMostPopular, icon, price);
    }
    //endregion
    //region new type
    private Model map2(DeliveryMethodDomain domain) {
        String price = formatter.format(domain.deliveryFee);
        boolean showMostPopular = false;
        int icon = R.drawable.ic_delivery_default;
        switch (domain.type) {
            case MOBILE:
                showMostPopular = true;
                icon = R.drawable.ic_delivery_mobile;
                break;
            case KIOSK:
                icon = R.drawable.ic_delivery_kiosk;
                break;
            case ELECTRONIC:
                icon = R.drawable.ic_delivery_electronic;
            default:
                break;
        }
        return new Model(showMostPopular, icon, price);
    }
    //endregion
    //region guard
    private Model map3(DeliveryMethodDomain domain) {
        if (domain.type == DeliveryMethodDomain.Type.ELECTRONIC) {
            throw new IllegalArgumentException(domain.type + " is not supported by this model!");
        }
        String price = formatter.format(domain.deliveryFee);
        boolean showMostPopular = false;
        int icon = R.drawable.ic_delivery_default;
        switch (domain.type) {
            case MOBILE:
                showMostPopular = true;
                icon = R.drawable.ic_delivery_mobile;
                break;
            case KIOSK:
                icon = R.drawable.ic_delivery_kiosk;
                break;
            default:
                break;
        }
        return new Model(showMostPopular, icon, price);
    }
    //endregion

}