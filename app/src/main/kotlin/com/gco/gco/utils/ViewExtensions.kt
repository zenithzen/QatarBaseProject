package com.gco.gco.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.PointF
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment

val View.dp: Float
    get() = context.dp

fun Context.dp(dpValue: Number): Float = resources.displayMetrics.density * dpValue.toFloat()

fun Fragment.dp(dpValue: Number) = requireContext().dp(dpValue)

fun View.dp(dpValue: Number) = context.dp(dpValue)

fun View.setCornerRadius(radiusPx: Float = Float.MAX_VALUE) {
    val outline =
        outlineProvider as? RoundedRectOutlineProvider ?: RoundedRectOutlineProvider(radiusPx)
    outline.radiusPx = radiusPx
    outlineProvider = outline
    clipToOutline = true
}

fun ArrayList<View>.setCornerRadius(radiusPx: Float = Float.MAX_VALUE) =
    forEach { v -> v.setCornerRadius(radiusPx) }

fun View.setCornerRadiusInt(radius: Int = Int.MAX_VALUE) {
    val radiusPx = radius * dp
    val outline =
        outlineProvider as? RoundedRectOutlineProvider ?: RoundedRectOutlineProvider(radiusPx)
    outline.radiusPx = radiusPx
    outlineProvider = outline
    clipToOutline = true
}

fun ArrayList<View>.setCornerRadiusInt(radius: Int = Int.MAX_VALUE) =
    forEach { v -> v.setCornerRadiusInt(radius) }

fun View.setCornerBottomRadius(radius: Float = Float.MAX_VALUE) {
    val radiusPx = radius * dp
    val outline =
        outlineProvider as? RoundedBottomRectOutlineProvider ?: RoundedBottomRectOutlineProvider(
            radiusPx
        )
    outline.radiusPx = radiusPx
    outlineProvider = outline
    clipToOutline = true
}

fun View.setCornerTopRadius(radius: Float = Float.MAX_VALUE) {
    val radiusPx = radius * dp
    val outline =
        outlineProvider as? RoundedTopRectOutlineProvider ?: RoundedTopRectOutlineProvider(radiusPx)
    outline.radiusPx = radiusPx
    outlineProvider = outline
    clipToOutline = true
}

fun View.viewVisibility(visible: Boolean = true) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.viewInvisibility(visible: Boolean = true) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

fun EditText.editTextWatcher(runnable: ((String, Int) -> Unit)) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            runnable.invoke(s.toString(), s.toString().length)
        }
    })
}

fun EditText.validTextCheck(
    starts: ArrayList<String>?, limit: Int?, runnable: ((String, Int) -> Unit)
) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s?.isNotEmpty() == true) {
                var valid = false
                var cValue = ""
                starts?.forEach { valid = !valid && s.firstOrNull()?.toString() == it }
                if (valid) cValue = s.toString()
                limit?.let { cValue = cValue.take(it) }
                if (valid) runnable.invoke(cValue, cValue.length)
            }
        }
    })
}

fun EditText.validQid(runnable: ((String, Int) -> Unit)? = null) {
    var pText: String
    this.doAfterTextChanged { e ->
        if (!e?.toString().isNullOrBlank()) {
            var eText = ""
            var valid = false
            arrayListOf("2", "3").forEach { if (!valid) valid = e?.firstOrNull()?.toString() == it }
            if (valid) eText = e?.toString() ?: ""
            pText = if (valid) eText else ""
            if (!valid) setText(pText) else runnable?.invoke(pText, pText.length)
        }
    }
}

fun EditText.validPhone(runnable: ((String, Int) -> Unit)? = null) {
    var pText: String
    this.doAfterTextChanged { e ->
        if (!e?.toString().isNullOrBlank()) {
            var eText = ""
            var valid = false
            arrayListOf("3", "5", "6", "7")
                .forEach { if (!valid) valid = e?.firstOrNull()?.toString() == it }
            if (valid) eText = e?.toString() ?: ""
            pText = if (valid) eText else ""
            if (!valid) this.setText(pText) else runnable?.invoke(pText, pText.length)
        }
    }
}

fun interpolate(progress: Float, start: Int, end: Int): Int = when {
    progress < 0 -> start
    progress > 1 -> end
    else -> (start + (end - start) * progress).toInt()
}

fun interpolate(progress: Float, start: Float, end: Float): Float = when {
    progress < 0 -> start
    progress > 1 -> end
    else -> start + (end - start) * progress
}

fun interpolateColor(progress: Float, start: Int, end: Int): Int = Color.argb(
    interpolate(progress, Color.alpha(start), Color.alpha(end)),
    interpolate(progress, Color.red(start), Color.red(end)),
    interpolate(progress, Color.green(start), Color.green(end)),
    interpolate(progress, Color.blue(start), Color.blue(end))
)

fun interpolatePoint(progress: Float, start: Point, end: Point, out: Point) {
    out.x = interpolate(progress, start.x, end.x)
    out.y = interpolate(progress, start.y, end.y)
}

fun interpolatePoint(progress: Float, start: PointF, end: PointF, out: PointF) {
    out.x = interpolate(progress, start.x, end.x)
    out.y = interpolate(progress, start.y, end.y)
}

fun interpolateViewTranslation(progress: Float, target: View, placeholder: View) {
    val targetLeft = target.left
    val targetTop = target.top
    val targetRight = target.right
    val targetBottom = target.bottom
    val targetWidth = targetRight - targetLeft
    val targetHeight = targetBottom - targetTop
    val placeholderLeft = placeholder.left
    val placeholderTop = placeholder.top
    val placeholderRight = placeholder.right
    val placeholderBottom = placeholder.bottom
    val placeholderWidth = placeholderRight - placeholderLeft
    val placeholderHeight = placeholderBottom - placeholderTop
    val endScaleX = placeholderWidth.toFloat() / targetWidth.toFloat()
    val endScaleY = placeholderHeight.toFloat() / targetHeight.toFloat()
    val translationX = placeholderLeft - targetLeft
    val translationY = placeholderTop - targetTop
    target.scaleX = 1f - (1f - endScaleX) * progress
    target.scaleY = 1f - (1f - endScaleY) * progress
    target.translationX = translationX * progress
    target.translationY = translationY * progress
}

fun Activity.intentActionDial(phone: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$phone")
    startActivity(intent)
}

@SuppressLint("QueryPermissionsNeeded")
fun Activity.intentSendMail(mailAddress: String) {
    /*val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/html"
    intent.putExtra(Intent.EXTRA_EMAIL, mailAddress)
    intent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
    intent.putExtra(Intent.EXTRA_TEXT, "Hi")
    startActivity(Intent.createChooser(intent, "Send Email"))*/

    /*val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("mailto:") // only email apps should handle this
    intent.putExtra(Intent.EXTRA_EMAIL, mailAddress)
    intent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }*/

    val emailIntent = Intent(
        Intent.ACTION_SENDTO, Uri.fromParts(
            "mailto", mailAddress, null
        )
    )
    intent.putExtra(Intent.EXTRA_EMAIL, mailAddress)
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
    emailIntent.putExtra(Intent.EXTRA_TEXT, "Body")
    startActivity(Intent.createChooser(emailIntent, "Send email..."))
}

fun Activity.intentActionBrowser(browserUrl: String) {
    val uri = Uri.parse(browserUrl)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    try {
        startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Activity.intentActionInstagram(instagramUrl: String) {
    val uri = Uri.parse(instagramUrl)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.setPackage("com.instagram.android")
    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        try {
            intent.setPackage("com.instagram.lite")
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl)))
        }
    }
}

fun Activity.intentActionSnapchat(snapchatUrl: String) {
    val uri = Uri.parse(snapchatUrl)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.setPackage("com.snapchat.android")
    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(snapchatUrl)))
    }
}

fun Activity.intentActionFacebook(facebookUrl: String) {
    /*val uri = Uri.parse(facebookUrl)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.setPackage("com.facebook.katana")
    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        try {
            intent.setPackage("com.facebook.lite")
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/$facebookUrl")
                )
            )
        }
    }*/
    val intent = Intent(Intent.ACTION_VIEW)
    val uri = Uri.parse(facebookUrl)
    intent.data = uri

    intent.`package` = "com.facebook.katana" // Use the Facebook app package name

    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        intent.`package` = null // Clear the package to allow the system to choose a default handler
        startActivity(intent)
    }
}

fun Activity.intentActionWhatsApp(phone: String) {
    val uri = Uri.parse("https://api.whatsapp.com/send?phone=$phone")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    startActivity(intent)
}

fun Activity.intentActionYoutube(url: String) {
    val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$url"))
    val webIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(url)
    )
    try {
        startActivity(appIntent)
    } catch (ex: ActivityNotFoundException) {
        startActivity(webIntent)
    }
}

fun Activity.intentActionTwitter(twitterUrl: String) {
    val uri = Uri.parse(twitterUrl)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.setPackage("com.twitter.android")
    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        try {
            intent.setPackage("com.twitter.lite")
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(twitterUrl)))
        }
    }
}