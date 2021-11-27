package com.kotlin.delivery.member.owner

import com.kotlin.delivery.common.entity.Member
import com.kotlin.delivery.restaurant.Restaurant
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "owners")
class Owner(

    common: Member,

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.ALL])
    val restaurants: MutableList<Restaurant> = ArrayList()

    // TODO: 연관관계 편의 메소드 owner <-> restaurant
) : Member(
    email = common.email,
    password = common.password,
    nickname = common.nickname,
    mobile = common.mobile,
    type = common.type
)
