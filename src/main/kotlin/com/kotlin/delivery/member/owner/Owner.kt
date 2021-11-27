package com.kotlin.delivery.member.owner

import com.kotlin.delivery.member.common.entity.Member
import com.kotlin.delivery.member.common.entity.MemberSummary
import com.kotlin.delivery.restaurant.Restaurant
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "owners")
class Owner(

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "summary_id", nullable = false)
    var summary: MemberSummary,

    common: Member,

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.ALL])
    var restaurants: MutableList<Restaurant> = ArrayList()

    // TODO: 연관관계 편의 메소드 owner <-> restaurant
) : Member(nickname = common.nickname, mobile = common.mobile)
