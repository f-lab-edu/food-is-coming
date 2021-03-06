package com.kotlin.delivery.common.entity

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class MutableEntity : ImmutableEntity() {

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
}
