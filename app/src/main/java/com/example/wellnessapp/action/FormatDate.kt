package com.example.wellnessapp.action

import java.text.SimpleDateFormat
import java.util.*

fun formatTimestampToDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
