package com.gco.gco


import android.content.Context
import android.graphics.Typeface
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.mbt.localization.LocalizedApp

object TypefaceLoader {
    private lateinit var interBlackEnglishFont: Typeface
    private lateinit var interBlackArabicFont: Typeface
    private lateinit var interBoldEnglishFont: Typeface
    private lateinit var interBoldArabicFont: Typeface
    private lateinit var interExtraBoldEnglishFont: Typeface
    private lateinit var interExtraBoldArabicFont: Typeface
    private lateinit var interExtraLightEnglishFont: Typeface
    private lateinit var interExtraLightArabicFont: Typeface
    private lateinit var interLightEnglishFont: Typeface
    private lateinit var interLightArabicFont: Typeface
    private lateinit var interMediumEnglishFont: Typeface
    private lateinit var interMediumArabicFont: Typeface
    private lateinit var interRegularEnglishFont: Typeface
    private lateinit var interRegularArabicFont: Typeface

    private lateinit var interSemiBoldEnglishFont: Typeface
    private lateinit var interSemiBoldArabicFont: Typeface
    private lateinit var interThinEnglishFont: Typeface
    private lateinit var interThinArabicFont: Typeface
    fun load(context: Context) {
        interBlackEnglishFont = ResourcesCompat.getFont(context, R.font.inter_black)!!
        interBlackArabicFont = ResourcesCompat.getFont(context, R.font.inter_black)!!
        interBoldEnglishFont = ResourcesCompat.getFont(context, R.font.inter_bold)!!
        interBoldArabicFont = ResourcesCompat.getFont(context, R.font.inter_bold)!!
        interExtraBoldEnglishFont = ResourcesCompat.getFont(context, R.font.inter_extra_bold)!!
        interExtraBoldArabicFont = ResourcesCompat.getFont(context, R.font.inter_extra_bold)!!
        interExtraLightEnglishFont = ResourcesCompat.getFont(context, R.font.inter_extra_light)!!
        interExtraLightArabicFont = ResourcesCompat.getFont(context, R.font.inter_extra_light)!!
        interLightEnglishFont = ResourcesCompat.getFont(context, R.font.inter_light)!!
        interLightArabicFont = ResourcesCompat.getFont(context, R.font.inter_light)!!
        interMediumEnglishFont = ResourcesCompat.getFont(context, R.font.inter_medium)!!
        interMediumArabicFont = ResourcesCompat.getFont(context, R.font.inter_medium)!!
        interRegularEnglishFont = ResourcesCompat.getFont(context, R.font.inter_regular)!!
        interRegularArabicFont = ResourcesCompat.getFont(context, R.font.inter_regular)!!
        interSemiBoldEnglishFont = ResourcesCompat.getFont(context, R.font.inter_semi_bold)!!
        interSemiBoldArabicFont = ResourcesCompat.getFont(context, R.font.inter_semi_bold)!!
        interThinEnglishFont = ResourcesCompat.getFont(context, R.font.inter_thin)!!
        interThinArabicFont = ResourcesCompat.getFont(context, R.font.inter_thin)!!
    }

    private fun interBlack(locale: String?): Typeface {
        return when (locale) {
            LocalizedApp.LOCALE_EN -> interBlackEnglishFont
            LocalizedApp.LOCALE_AR -> interBlackArabicFont
            else -> error("TypefaceLoader.interBlack: Illegal locale $locale")
        }
    }

    fun interBold(locale: String?): Typeface {
        return when (locale) {
            LocalizedApp.LOCALE_EN -> interBoldEnglishFont
            LocalizedApp.LOCALE_AR -> interBoldArabicFont
            else -> error("TypefaceLoader.interBold: Illegal locale $locale")
        }
    }

    fun interExtraBold(locale: String?): Typeface {
        return when (locale) {
            LocalizedApp.LOCALE_EN -> interExtraBoldEnglishFont
            LocalizedApp.LOCALE_AR -> interExtraBoldArabicFont
            else -> error("TypefaceLoader.interExtraBold: Illegal locale $locale")
        }
    }

    fun interExtraLight(locale: String?): Typeface {
        return when (locale) {
            LocalizedApp.LOCALE_EN -> interExtraLightEnglishFont
            LocalizedApp.LOCALE_AR -> interExtraLightArabicFont
            else -> error("TypefaceLoader.interExtraLight: Illegal locale $locale")
        }
    }

    fun interLight(locale: String?): Typeface {
        return when (locale) {
            LocalizedApp.LOCALE_EN -> interLightEnglishFont
            LocalizedApp.LOCALE_AR -> interLightArabicFont
            else -> error("TypefaceLoader.interLight: Illegal locale $locale")
        }
    }

    fun interMedium(locale: String?): Typeface {
        return when (locale) {
            LocalizedApp.LOCALE_EN -> interMediumEnglishFont
            LocalizedApp.LOCALE_AR -> interMediumArabicFont
            else -> error("TypefaceLoader.interMedium: Illegal locale $locale")
        }
    }

    fun interRegular(locale: String?): Typeface {
        return when (locale) {
            LocalizedApp.LOCALE_EN -> interRegularEnglishFont
            LocalizedApp.LOCALE_AR -> interRegularArabicFont
            else -> error("TypefaceLoader.interRegular: Illegal locale $locale")
        }
    }

    fun interSemiBold(locale: String?): Typeface {
        return when (locale) {
            LocalizedApp.LOCALE_EN -> interSemiBoldEnglishFont
            LocalizedApp.LOCALE_AR -> interSemiBoldArabicFont
            else -> error("TypefaceLoader.interSemiBold: Illegal locale $locale")
        }
    }

    fun interThin(locale: String?): Typeface {
        return when (locale) {
            LocalizedApp.LOCALE_EN -> interThinEnglishFont
            LocalizedApp.LOCALE_AR -> interThinArabicFont
            else -> error("TypefaceLoader.interThin: Illegal locale $locale")
        }
    }

    //InterBlack
    fun TextView.setInterBlack(locale: String?) {
        typeface = interBlack(locale)
    }

    fun EditText.setInterBlack(locale: String?) {
        typeface = interBlack(locale)
    }

/*    fun ArrayList<TextView>.setInterBlack(locale: String?) {
        forEach { _ -> interBlack(locale) }
    }


    fun ArrayList<EditText>.setInterBlack(locale: String?) {
        forEach { _ -> interBlack(locale) }
    }*/

    //InterBold

    fun TextView.setInterBold(locale: String?) {
        typeface = interBold(locale)
    }

    fun EditText.setInterBold(locale: String?) {
        typeface = interBold(locale)
    }

  /*  fun ArrayList<TextView>.setInterBold(locale: String?) {
        forEach { _ -> interBold(locale) }
    }


    fun ArrayList<EditText>.setInterBold(locale: String?) {
        forEach { _ -> interBold(locale) }
    }*/

    //InterExtraBold

    fun TextView.setInterExtraBold(locale: String?) {
        typeface = interExtraBold(locale)
    }

    fun EditText.setInterExtraBold(locale: String?) {
        typeface = interExtraBold(locale)
    }

  /*  fun ArrayList<TextView>.setInterExtraBold(locale: String?) {
        forEach { _ -> interExtraBold(locale) }
    }


    fun ArrayList<EditText>.setInterExtraBold(locale: String?) {
        forEach { _ -> interExtraBold(locale) }
    }
*/
    //InterLite

    fun TextView.setInterLite(locale: String?) {
        typeface = interLight(locale)
    }

    fun EditText.setInterLite(locale: String?) {
        typeface = interLight(locale)
    }

  /*  fun ArrayList<TextView>.setInterLite(locale: String?) {
        forEach { _ -> interLight(locale) }
    }


    fun ArrayList<EditText>.setInterLite(locale: String?) {
        forEach { _ -> interLight(locale) }
    }*/

    //InterExtraLite

    fun TextView.setInterExtraLite(locale: String?) {
        typeface = interExtraLight(locale)
    }

    fun EditText.setInterExtraLite(locale: String?) {
        typeface = interExtraLight(locale)
    }

   /* fun ArrayList<TextView>.setInterExtraLite(locale: String?) {
        forEach { _ -> interExtraLight(locale) }
    }


    fun ArrayList<EditText>.setInterExtraLite(locale: String?) {
        forEach { _ -> interExtraLight(locale) }
    }*/


    //InterMedium

    fun TextView.setInterMedium(locale: String?) {
        typeface = interMedium(locale)
    }

    fun EditText.setInterMedium(locale: String?) {
        typeface = interMedium(locale)
    }

  /*  fun ArrayList<TextView>.setInterMedium(locale: String?) {
        forEach { _ -> interMedium(locale) }
    }


    fun ArrayList<EditText>.setInterMedium(locale: String?) {
        forEach { _ -> interMedium(locale) }
    }*/

    //InterRegular

    fun TextView.setInterRegular(locale: String?) {
        typeface = interRegular(locale)
    }

    fun EditText.setInterRegular(locale: String?) {
        typeface = interRegular(locale)
    }

  /*  fun ArrayList<TextView>.setInterRegular(locale: String?) {
        forEach { _ -> interRegular(locale)
        }
    }


    fun ArrayList<EditText>.setInterRegular(locale: String?) {
        forEach { _ -> interRegular(locale) }
    }*/


    //InterSemiBold

    fun TextView.setSemiBold(locale: String?) {
        typeface = interSemiBold(locale)
    }

    fun EditText.setSemiBold(locale: String?) {
        typeface = interSemiBold(locale)
    }

   /* fun ArrayList<TextView>.setSemiBold(locale: String?) {
        forEach { _ -> interSemiBold(locale) }
    }


    fun ArrayList<EditText>.setSemiBold(locale: String?) {
        forEach { _ -> interSemiBold(locale) }
    }*/

    //InterThin

    fun TextView.setThin(locale: String?) {
        typeface = interThin(locale)
    }

    fun EditText.setThin(locale: String?) {
        typeface = interThin(locale)
    }

  /*  fun ArrayList<TextView>.setThin(locale: String?) {
        forEach { _ -> interThin(locale) }
    }


    fun ArrayList<EditText>.setThin(locale: String?) {
        forEach { _ -> interThin(locale) }
    }*/

}