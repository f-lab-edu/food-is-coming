package com.kotlin.delivery.restaurant

import com.kotlin.delivery.common.entity.Image
import com.kotlin.delivery.common.entity.MutableEntity
import com.kotlin.delivery.common.entity.Location
import com.kotlin.delivery.member.owner.Owner
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "restaurants")
class Restaurant(

    @ManyToOne
    @JoinColumn(name = "owner_id")
    val owner: Owner,

    val businessNo: String,

    val category: FoodCategory,

    val name: String,

    val location: Location,

    val telecom: String,

    val open: Boolean,

    // TODO: 1:N 으로 만드려면 엔티티로 승격(?)필요해보임
    val image: Image,

    val description: String
) : MutableEntity() {
}
