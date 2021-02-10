package com.example.homework9_1.model

import java.time.Instant
import java.time.ZoneId

class DateMapper {
    companion object {
        fun getTime(dt: Long): String = "${Instant.ofEpochSecond(dt).atZone(ZoneId.systemDefault()).toLocalDateTime().hour}:00, " +
                "${Instant.ofEpochSecond(dt).atZone(ZoneId.systemDefault()).toLocalDateTime().dayOfWeek}"
    }
}