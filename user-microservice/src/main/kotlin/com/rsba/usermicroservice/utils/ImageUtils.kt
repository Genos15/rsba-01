package com.rsba.usermicroservice.utils

import org.springframework.http.MediaType
import java.awt.image.BufferedImage
import java.util.*


object ImageUtils {
//     fun scale(image: BufferedImage): BufferedImage? {
//        val maxWidth = 200
//        val maxHeight = 200
//        return if (image.width >= image.height && image.width > maxWidth) {
//            val newHeight = (image.height * (maxWidth.toFloat() / image.width)).toInt()
//            getBufferered(image.getScaledInstance(maxWidth, newHeight, BufferedImage.SCALE_SMOOTH), maxWidth, newHeight)
//        } else if (image.height > image.width && image.height > maxHeight) {
//            val newWidth = (image.width * (maxHeight.toFloat() / image.height)).toInt()
//            getBufferered(image.getScaledInstance(newWidth, maxHeight, BufferedImage.SCALE_SMOOTH), newWidth, maxHeight)
//        } else {
//            image
//        }
//    }
//
//    private fun getType(mimetype: String): String {
//        val mediaType: MediaType = MediaType.parseMediaType(mimetype)
//        return if (!isImage(mediaType)) throw InvalidPersonAvatarException("Invalid content-type") else if (isJpeg(
//                mediaType
//            )
//        ) "jpg" else mediaType.subtype
//    }
//
//    private fun isJpeg(mediaType: MediaType): Boolean {
//        return "jpeg".equals(mediaType.subtype, ignoreCase = true)
//    }
//
//    private fun isImage(mediaType: MediaType): Boolean {
//        return "image".equals(mediaType.type, ignoreCase = true)
//    }
}