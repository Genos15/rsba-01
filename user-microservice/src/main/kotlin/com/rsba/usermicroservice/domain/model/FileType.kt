package com.rsba.usermicroservice.domain.model

import org.springframework.core.io.buffer.DefaultDataBufferFactory

sealed class FileType {
    object image : FileType()
    object video : FileType()
    object excel : FileType()
    object pdf : FileType()
    object other : FileType()


    fun factory() = DefaultDataBufferFactory()
}
