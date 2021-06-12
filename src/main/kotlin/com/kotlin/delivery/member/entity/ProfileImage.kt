package com.kotlin.delivery.member.entity

import javax.persistence.Embeddable

@Embeddable
class ProfileImage(

    var imageName: String,

    var imagePath: String
)
