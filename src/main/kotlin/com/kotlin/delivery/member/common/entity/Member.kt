package com.kotlin.delivery.member.common.entity

import com.kotlin.delivery.common.entity.MutableEntity
import com.kotlin.delivery.member.common.dto.SignUpRequest
import com.kotlin.delivery.member.common.util.MemberStatus
import javax.persistence.Column
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.MappedSuperclass

@MappedSuperclass
class Member(

    @Column(nullable = false)
    var nickname: String,

    @Column(nullable = false)
    val mobile: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: MemberStatus = MemberStatus.ACTIVE,
) : MutableEntity()
