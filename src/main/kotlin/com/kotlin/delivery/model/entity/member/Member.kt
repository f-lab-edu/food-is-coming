package com.kotlin.delivery.model.entity.member

import com.kotlin.delivery.model.entity.base.MutableEntity
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = "members")
class Member(

    val email: String,

    val nickname: String,

    val mobile: String,

    @Embedded
    val location: Location,

    @Embedded
    val profileImage: ProfileImage,

    @Enumerated(EnumType.STRING)
    val type: Member.Type,

    @Enumerated(EnumType.STRING)
    val status: Member.Status = Status.ACTIVE
): MutableEntity() {

    enum class Type {
        CUSTOMER, RIDER, OWNER
    }

    enum class Status {
        ACTIVE, DEACTIVATED;
    }
}
