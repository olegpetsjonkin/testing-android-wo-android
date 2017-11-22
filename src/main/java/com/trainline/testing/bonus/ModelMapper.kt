package com.trainline.testing.bonus

import com.trainline.testing.helpers.R
import com.trainline.testing.mvp.Formatter
import rx.functions.Func1

class ModelMapper(private val formatter: Formatter) : Func1<DeliveryMethodDomain, Model> {

    override fun call(domain: DeliveryMethodDomain): Model {
        return map(domain)
    }

    private fun map(domain: DeliveryMethodDomain): Model {
        val price = formatter.format(domain.deliveryFee)
        var showMostPopular = false
        var icon = R.drawable.ic_delivery_default
        when (domain.type) {
            Type.MOBILE -> {
                showMostPopular = true
                icon = R.drawable.ic_delivery_mobile
            }
            Type.KIOSK -> icon = R.drawable.ic_delivery_kiosk
            else -> {
            }
        }
        return Model(showMostPopular, icon, price)
    }
}