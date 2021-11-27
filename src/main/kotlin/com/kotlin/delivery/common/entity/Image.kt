package com.kotlin.delivery.common.entity

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class Image(

    @Column(name = "image_name")
    var name: String? = null,

    @Column(name = "image_path")
    var path: String? = null
)
