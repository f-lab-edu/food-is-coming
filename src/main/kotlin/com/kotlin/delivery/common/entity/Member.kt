package com.kotlin.delivery.common.entity

import com.kotlin.delivery.member.common.dto.SignUpRequest
import com.kotlin.delivery.member.common.util.MemberStatus
import com.kotlin.delivery.member.common.util.MemberType
import javax.persistence.Column
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.MappedSuperclass

@MappedSuperclass
class Member(

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false)
    var nickname: String,

    @Column(nullable = false)
    val mobile: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val type: MemberType,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: MemberStatus = MemberStatus.ACTIVE,
) : MutableEntity() {

    constructor(req: SignUpRequest, encodedPassword: String, type: MemberType) : this(
        email = req.email,
        password = encodedPassword,
        nickname = req.nickname,
        mobile = req.mobile,
        type = type
    )
}
