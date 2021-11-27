package com.kotlin.delivery.member.customer

import com.kotlin.delivery.common.entity.Location
import com.kotlin.delivery.common.entity.Member
import com.kotlin.delivery.common.entity.Image
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "customers")
class Customer(

    common: Member,

    @Embedded
    val location: Location? = null,

    @Embedded
    val profileImage: Image? = null,
) : Member(
    email = common.email,
    password = common.password,
    nickname = common.nickname,
    mobile = common.mobile,
    type = common.type
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Customer) return false
        if (id != other.id) return false
        return true
    }

    // TODO: hashcode generation
    override fun hashCode() = id.hashCode()
}
