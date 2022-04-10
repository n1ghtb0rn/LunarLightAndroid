package com.sample.jetbooks.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class TimestampConverter {

    @SuppressLint("SimpleDateFormat")
    fun getDateTime(s: String): String? {
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val netDate = Date(s.toLong() * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return "Unknown timestamp"
        }
    }
}