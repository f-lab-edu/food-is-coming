package com.kotlin.delivery.model.entity.base

import java.time.LocalDateTime
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class ImmutableEntity: BaseEntity() {

    val createdAt: LocalDateTime = LocalDateTime.now()
}
