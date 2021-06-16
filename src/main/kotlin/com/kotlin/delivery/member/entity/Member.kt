package com.kotlin.delivery.member.entity

import com.kotlin.delivery.common.entity.MutableEntity
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = "members")
class Member(

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val nickname: String,

    @Column(nullable = false)
    val mobile: String,

    @Embedded
    val location: Location? = null,

    @Embedded
    val profileImage: ProfileImage? = null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val type: Type,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val status: Status = Status.ACTIVE
) : MutableEntity() {

    enum class Type {
        CUSTOMER, RIDER, OWNER
    }

    enum class Status {
        ACTIVE, DEACTIVATED;
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Member) return false
        if (id != other.id) return false
        return true
    }

    override fun hashCode() = id?.hashCode() ?: 0
}
