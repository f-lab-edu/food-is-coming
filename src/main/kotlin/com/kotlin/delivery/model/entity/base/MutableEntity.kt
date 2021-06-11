package com.kotlin.delivery.model.entity.base

import java.time.LocalDateTime
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class MutableEntity : ImmutableEntity() {

    var updatedAt: LocalDateTime = LocalDateTime.now()
}
