package com.kotlin.delivery.common.entity

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class ImmutableEntity: BaseEntity() {

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
}
