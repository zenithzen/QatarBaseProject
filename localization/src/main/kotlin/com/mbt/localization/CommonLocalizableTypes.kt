package com.mbt.localization

import android.text.SpannableStringBuilder
import android.view.View
import androidx.annotation.StringRes

class MappedLocalizable<T>(
    val default: T? = null,
    val en: T? = null,
    val ar: T? = null,
) : Localizable<T> {
    override fun getLocalizedValue(locale: String): T? {
        return when (locale) {
            LocalizedApp.LOCALE_EN -> en ?: default ?: ar
            LocalizedApp.LOCALE_AR -> ar ?: default ?: en
            else -> error("Illegal locale: $locale")
        }
    }
}

class StringResourceLocalizable(@StringRes private val id: Int) : Localizable<String> {
    override fun getLocalizedValue(locale: String): String {
        return LocalizedApp.getResources(locale).getString(id)
    }
}

/**
 * Extension method to convert a string directly to localizable.
 * Use this method only if you have a string but you need to pass
 * a Localizable type.
 */
fun String.toLocalizedString() = object : Localizable<String> {
    override fun getLocalizedValue(locale: String): String {
        return this@toLocalizedString
    }
}

/**
 * Extension method to convert a string directly to localizable.
 * Use this method only if you have a SpannableStringBuilder but you need to pass
 * a Localizable type.
 */
fun SpannableStringBuilder.toLocalizedString() = object : Localizable<SpannableStringBuilder> {
    override fun getLocalizedValue(locale: String): SpannableStringBuilder {
        return this@toLocalizedString
    }
}

/**
 * Extension method to convert string resource to localizable type
 * Example usage: R.string.app_name.toLocalizedString()
 * Warning: Do not use this method with other integers
 */
fun Int.toLocalizedString() = StringResourceLocalizable(this)

fun <T> select(en: T, ar: T): T {
    return when (LocalizedApp.locale) {
        LocalizedApp.LOCALE_EN -> en
        LocalizedApp.LOCALE_AR -> ar
        else -> error("Unknown locale: ${LocalizedApp.locale}")
    }
}

fun View.mirror(locale: String) {
    scaleX = when (locale) {
        LocalizedApp.LOCALE_EN -> 1f
        LocalizedApp.LOCALE_AR -> -1f
        else -> error("Unknown locale: $locale")
    }
}

fun <T: CharSequence> composeLocalizable(vararg localizables: Localizable<out T>) = object: Localizable<String> {
    override fun getLocalizedValue(locale: String): String {
        return localizables.map { it.getLocalizedValue(locale) }.joinToString(separator = "")
    }
}