package com.kotlin.delivery.model.entity.member

import javax.persistence.Embeddable

@Embeddable
class ProfileImage(var imageName: String? = null, var imagePath: String? = null)
