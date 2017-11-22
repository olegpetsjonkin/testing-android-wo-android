package com.trainline.testing.bonus

import java.math.BigDecimal

data class DeliveryMethodDomain(val type: Type, val deliveryFee: BigDecimal)

enum class Type { KIOSK, MOBILE, ELECTRONIC, POSTAL }