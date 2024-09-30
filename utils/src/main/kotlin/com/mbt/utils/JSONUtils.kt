package com.mbt.utils

import org.json.JSONObject

fun JSONObject.getStringOrNull(key: String, isStringConversionEnabled: Boolean = false): String? {
    return when (val value = opt(key)) {
        is String -> value
        is Number -> if (isStringConversionEnabled) value.toString() else null
        is Boolean -> if (isStringConversionEnabled) value.toString() else null
        JSONObject.NULL -> null
        else -> null
    }
}

fun JSONObject.getIntOrNull(key: String): Int? {
    return when (val value = opt(key)) {
        is Number -> value.toInt()
        is String -> value.toIntOrNull()
        else -> null
    }
}

fun JSONObject.getLongOrNull(key: String): Long? {
    return when (val value = opt(key)) {
        is Number -> value.toLong()
        is String -> value.toLongOrNull()
        else -> null
    }
}

fun JSONObject.getDoubleOrNull(key: String): Double? {
    return when (val value = opt(key)) {
        is Number -> value.toDouble()
        is String -> value.toDoubleOrNull()
        else -> null
    }
}