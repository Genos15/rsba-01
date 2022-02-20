package com.rsba.tasks_microservice.domain.usecase.custom.task

import java.util.*

interface ToExecuteTasksOfItemUseCase {
    suspend operator fun invoke(input: UUID, token: UUID): Boolean
}