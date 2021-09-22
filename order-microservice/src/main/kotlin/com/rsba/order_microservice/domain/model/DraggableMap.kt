package com.rsba.order_microservice.domain.model


import java.util.*

data class DraggableMap(
    val tasks: Map<UUID, Task>,
    val columns: Map<UUID, DraggableWorkcenter>,
    val columnOrder: List<UUID>,
)

