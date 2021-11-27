package com.kotlin.delivery.member.common.entity

import com.kotlin.delivery.common.entity.MutableEntity
import com.kotlin.delivery.member.common.util.MemberType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = "member_summary")
class MemberSummary(

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var type: MemberType
) : MutableEntity()
