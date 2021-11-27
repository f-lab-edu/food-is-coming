package com.kotlin.delivery.member.customer

import com.kotlin.delivery.member.common.entity.Location
import com.kotlin.delivery.member.common.entity.Member
import com.kotlin.delivery.member.common.entity.Image
import com.kotlin.delivery.member.common.entity.MemberSummary
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "customers")
class Customer(

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "summary_id", nullable = false)
    val summary: MemberSummary,

    common: Member,

    @Embedded
    val location: Location? = null,

    @Embedded
    val profileImage: Image? = null,
) : Member(nickname = common.nickname, mobile = common.mobile) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Customer) return false
        if (id != other.id) return false
        return true
    }

    // TODO: hashcode generation
    override fun hashCode() = id.hashCode()
}
