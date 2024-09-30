package com.mbt.localization

interface Localizable<T> {
    fun getLocalizedValue(locale: String): T?
}