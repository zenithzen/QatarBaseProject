package com.gco.gco.utils

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.text.HtmlCompat
import androidx.core.text.color
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mbt.datelib.DateLib
import com.mbt.localization.LocalizedApp
import com.mbt.localization.LocalizedApp.Companion.locale
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.gco.gco.R
import java.io.File
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun View.dismissKeyboard() {
    try {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun <T> choose(condition: Boolean? = false, vt: T, vf: T): T = if (condition == true) vt else vf
fun <T> chooseL(
    vt: T, vf: T, condition: Boolean? = locale == LocalizedApp.LOCALE_EN
): T = if (condition == true) vt else vf

@Suppress("IMPLICIT_CAST_TO_ANY")
fun ImageView.setImage(
    imageUrl: Any? = R.drawable.ministry_icon,
    @DrawableRes placeholderId: Int? = R.drawable.ministry_icon,
    @DrawableRes errorId: Int? = R.drawable.ministry_icon,
    cache: Boolean? = true,
    fitCenter: Boolean? = false,
    autoMirrored: Boolean? = false
) = when (imageUrl) {
    is Int -> setImageDrawable(LocalizedApp.getDrawable(imageUrl, autoMirrored))
    else -> Glide.with(this).load(imageUrl).placeholder(placeholderId ?: R.drawable.ministry_icon)
        .let {
            if (fitCenter == true) it.fitCenter() else it.centerCrop()
        }.error(errorId ?: R.drawable.ministry_icon)
        .diskCacheStrategy(if (cache == true) DiskCacheStrategy.AUTOMATIC else DiskCacheStrategy.NONE)
        .skipMemoryCache(cache != true).into(this)
}

fun MotionLayout.viewVisibility(
    constraints: Any? = null, views: Any? = null, visible: Boolean = true
) = constraints?.let { c ->
    when (c) {
        is ArrayList<*> -> c.onEach { ci ->
            if (ci is Int) getConstraintSet(ci).apply { viewVisibility(views, visible) }
                .applyTo(this)
        }

        is Int -> getConstraintSet(c).apply { viewVisibility(views, visible) }.applyTo(this)
        else -> {}
    }
}

fun ConstraintSet.viewVisibility(views: Any? = null, visible: Boolean = true) = views?.let { vs ->
    when (vs) {
        is ArrayList<*> -> vs.onEach { v ->
            when (v) {
                is View -> setVisibility(v.id, choose(visible, View.VISIBLE, View.GONE))
                is Int -> setVisibility(v, choose(visible, View.VISIBLE, View.GONE))
                else -> {}
            }
        }

        is View -> setVisibility(vs.id, choose(visible, View.VISIBLE, View.GONE))
        is Int -> setVisibility(vs, choose(visible, View.VISIBLE, View.GONE))
        else -> {}
    }
}

fun getNavOptions(
    popFragId: Int? = null, popFragInclusive: Boolean? = false, singleTop: Boolean? = null
): NavOptions = navOptions {
    popFragId?.let { popUpTo(it) { inclusive = popFragInclusive ?: false } }
    singleTop?.let { launchSingleTop = it }
}

fun getBitmapFromVector(@DrawableRes image: Int): Bitmap? {
    var drawable = LocalizedApp.getDrawable(image)
    return drawable?.let {
        drawable = DrawableCompat.wrap(it).mutate()
        val bitmap =
            Bitmap.createBitmap(it.intrinsicWidth, it.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        it.setBounds(0, 0, canvas.width, canvas.height)
        it.draw(canvas)
        return@let bitmap
    }
}

fun String.stringMandatory(@ColorRes color: Int = R.color.black): Spanned? {
    val c = LocalizedApp.getColor(color)
    return Html.fromHtml(
        if (this.contains("*")) replace("*", "<font color='$c'>*</font>")
        else StringBuilder().append(this).append(" <font color='$c'>*</font>").toString(),
        HtmlCompat.FROM_HTML_MODE_LEGACY
    )
}

fun String.stringNonMandatory() = replace("*", "").trim()

/*fun String.stringError(
    @ColorRes color: Int = R.color.error, @StringRes string: Int? = R.string.label_required
): Spanned? {
    val c = LocalizedApp.getColor(color)
    val s = string?.let { LocalizedApp.getString(it) } ?: ""
    return Html.fromHtml(
        if (this.contains("*")) replace("*", "<font color='$c'>$s</font>")
        else StringBuilder().append(this).append(" <font color='$c'>$s</font>").toString(),
        HtmlCompat.FROM_HTML_MODE_LEGACY
    )
}

fun String.inactive(@ColorRes c: Int = R.color.inactive_menu): Spanned =
    SpannableStringBuilder().color(LocalizedApp.getColor(c)) { append(this@inactive) }

fun Spanned.comingSoon(@ColorRes cc: Int = R.color.primary): Spanned =
    SpannableStringBuilder().append(this)
        .color(LocalizedApp.getColor(cc)) { append(" (${LocalizedApp.getString(R.string.label_coming_soon)})") }*/

fun View.animateProgress(condition: Boolean, start: Float, end: Float) =
    ValueAnimator.ofFloat(choose(condition, start, end), choose(condition, end, start)).apply {
        duration = 300
        interpolator = AccelerateDecelerateInterpolator()
        addUpdateListener { valueAnimator -> rotation = valueAnimator.animatedValue as Float }
    }.start()

/*fun formatTimestamp(time: String, locale: String): String? {
    try {
        val resources = LocalizedApp.getResources(locale)
        val dateParser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'", Locale.ENGLISH)
        val date = dateParser.parse(time)
        val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US)
        val today = DateToken.convertTimestampToDateToken(cal.timeInMillis)
        cal.add(Calendar.DATE, -1)
        val yesterday = DateToken.convertTimestampToDateToken(cal.timeInMillis)
        cal.timeInMillis = date.time
        return when (DateToken.convertTimestampToDateToken(cal.timeInMillis)) {
            today -> when (val durationMs = System.currentTimeMillis() - date.time) {
                *//* Seconds ago *//*
                in 0L until TimeUnit.MINUTES.toMillis(1) ->
                    TimeUnit.MILLISECONDS.toSeconds(durationMs).let {
                        String.format(
                            Locale.US,
                            LocalizedApp.getString(if (it == 1L) R.string.second_ago_singular else R.string.seconds_ago_plural),
                            it
                        )
                    }
                *//* Minutes ago *//*
                in TimeUnit.MINUTES.toMillis(1) until TimeUnit.HOURS.toMillis(1) ->
                    TimeUnit.MILLISECONDS.toMinutes(durationMs).let {
                        String.format(
                            Locale.US,
                            LocalizedApp.getString(if (it == 1L) R.string.minutes_ago_singular else R.string.minutes_ago_plural),
                            it
                        )
                    }
                *//* Hours ago *//*
                else -> TimeUnit.MILLISECONDS.toHours(durationMs).let {
                    String.format(
                        Locale.US,
                        LocalizedApp.getString(if (it == 1L) R.string.hour_ago_singular else R.string.hours_ago_plural),
                        it
                    )
                }
            }

            yesterday -> String.format(
                Locale.US,
                "%1\$s, %2\$02d:%3\$02d %4\$s",
                LocalizedApp.getString(R.string.label_yesterday),
                to12Hr(cal[Calendar.HOUR_OF_DAY]),
                cal[Calendar.MINUTE],
                DateLib.getAmPm(resources, cal[Calendar.AM_PM])
            )

            else -> String.format(
                Locale.US,
                "%1\$02d %2\$s %3\$d, %4\$02d:%5\$02d %6\$s",
                cal[Calendar.DAY_OF_MONTH],
                DateLib.getMonth(resources, cal[Calendar.MONTH]),
                cal[Calendar.YEAR],
                to12Hr(cal[Calendar.HOUR_OF_DAY]),
                cal[Calendar.MINUTE],
                DateLib.getAmPm(resources, cal[Calendar.AM_PM])
            )
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}*/

fun String.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
private fun to12Hr(hour: Int): Int {
    return when (hour) {
        0 -> 12
        in 1..12 -> hour
        else -> hour - 12
    }
}

@SuppressLint("SimpleDateFormat")
fun String.getFormattedDate(
    inF: String = "yyyy-MM-dd'T'HH:mm:ss", of: String = "dd/MM/yyyy", utc: Boolean? = false
): String? = try {
    SimpleDateFormat(inF, Locale.ENGLISH).apply {
        if (utc == true) timeZone = TimeZone.getTimeZone("UTC")
    }
        .parse(this)?.let { qed ->
            SimpleDateFormat(of, Locale.ENGLISH).apply {
                if (utc == true) timeZone = TimeZone.getDefault()
            }
                .format(qed)
        }
} catch (e: Exception) {
    e.printStackTrace()
    null
}

@SuppressLint("SimpleDateFormat")
fun String.getFormattedDateLocale(
    inF: String = "yyyy-MM-dd'T'HH:mm:ss", of: String = "dd/MM/yyyy", utc: Boolean? = false
): String? = SimpleDateFormat(inF, Locale(locale)).apply {
    if (utc == true) timeZone = TimeZone.getTimeZone("UTC")
}.parse(this)?.let { qed ->
    SimpleDateFormat(of, Locale(locale)).apply { if (utc == true) timeZone = TimeZone.getDefault() }
        .format(qed)
}

@SuppressLint("SimpleDateFormat")
fun String.getDateOfString(ipf: String = "dd/MM/yyyy"): Date? =
    SimpleDateFormat(ipf, Locale.ENGLISH).parse(this)

/*fun Number.getFormattedPrice(dp: Int? = 0, cur: Boolean = true): String = String.format(
    "%s %s",
    DecimalFormat(choose((dp ?: 0) > 0, "#.", "#") + "#".repeat(dp ?: 0)).format(this.toFloat()),
    if (cur) LocalizedApp.getString(R.string.title_qar) else ""
).trim()*/

/*fun getDate(time: String): String? {
    try {
        val dateParser = SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag(locale))
        val date = dateParser.parse(time)!!
        val cal = Calendar.getInstance(Locale.forLanguageTag(locale))
        val today = DateToken.convertTimestampToDateToken(cal.timeInMillis)
        cal.add(Calendar.DATE, -1)
        val yesterday = DateToken.convertTimestampToDateToken(cal.timeInMillis)
        cal.timeInMillis = date.time
        return when (DateToken.convertTimestampToDateToken(cal.timeInMillis)) {
            today -> LocalizedApp.getString(R.string.label_today)
            yesterday -> LocalizedApp.getString(R.string.label_yesterday)
            else -> time
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}*/

fun getLangString(sEn: String? = null, sAr: String? = null): String? =
    chooseL(sEn.let { if (it.isNullOrEmpty()) sAr else it },
        sAr.let { if (it.isNullOrEmpty()) sEn else it })

fun RecyclerView.addOnScrolledToEndListener(onScrolledToEnd: () -> Unit) {
    val tag = "addOnScrolledToEndListener"
    val listener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            when (val layoutManager = recyclerView.layoutManager) {
                is GridLayoutManager -> {
                    val lastItemPosFromLayoutManager =
                        layoutManager.findLastCompletelyVisibleItemPosition()
                    val lastItemPosFromAdapter = (recyclerView.adapter?.itemCount ?: 0) - 1
                    if (lastItemPosFromLayoutManager >= 0 && lastItemPosFromAdapter >= 0 && lastItemPosFromLayoutManager >= lastItemPosFromAdapter) {
                        onScrolledToEnd()
                    }
                }

                is LinearLayoutManager -> {
                    val lastItemPosFromLayoutManager =
                        layoutManager.findLastCompletelyVisibleItemPosition()
                    val lastItemPosFromAdapter = (recyclerView.adapter?.itemCount ?: 0) - 1
                    if (lastItemPosFromLayoutManager >= 0 && lastItemPosFromAdapter >= 0 && lastItemPosFromLayoutManager >= lastItemPosFromAdapter) {
                        onScrolledToEnd()
                    }
                }

                is StaggeredGridLayoutManager -> {
                    val lastItemPosFromLayoutManager =
                        layoutManager.findLastVisibleItemPositions(null)?.maxOrNull() ?: -1
                    val lastItemPosFromAdapter = (recyclerView.adapter?.itemCount ?: 0) - 1
                    if (lastItemPosFromLayoutManager >= 0 && lastItemPosFromAdapter >= 0 && lastItemPosFromLayoutManager >= lastItemPosFromAdapter) {
                        onScrolledToEnd()
                    }
                }

                null -> Log.e(tag, "onScrolled: layout manager is null")
                else -> error("Unknown layout manager: $layoutManager")
            }
        }
    }
    addOnScrollListener(listener)
}

fun File.isFileLessThan(size: Int): Boolean {
    try {
        val maxFileSize = size * 1024 * 1024
        val fileLength: Long = length()
        val fileSize = fileLength.toString()
        val finalFileSize = fileSize.toInt()
        return finalFileSize <= maxFileSize
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}