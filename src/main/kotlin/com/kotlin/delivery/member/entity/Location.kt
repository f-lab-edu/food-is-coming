package com.kotlin.delivery.member.entity

import javax.persistence.Embeddable

@Embeddable
class Location(

    var address: String? = null,

    var longitude: Double? = null,

    var latitude: Double? = null
)
