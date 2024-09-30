package com.mbt.switchwidget

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.Outline
import android.graphics.Typeface
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.animation.doOnEnd
import androidx.core.content.res.ResourcesCompat
import com.mbt.localization.LocalizedApp
import kotlin.math.min

private const val SUPER_STATE = "super_state"
private const val CURRENT_OFFSET = "current_offset"

class SwitchWidget : FrameLayout {
    private var fontFamilyEn: Typeface? = null
    private var fontFamilyAr: Typeface? = null
    var onIndexChanged: ((Int) -> Unit)? = null
    private var textSize = 0f
    private var lineSpacingExtra = 0f
    private var selectorColor = Color.WHITE
    private var selectorCornerRadius = Float.MAX_VALUE
    private var defaultTextColor = Color.BLACK
    private var selectedTextColor = Color.RED
    private val switchStringResItems = mutableListOf<Int>()

    private val textViews = mutableListOf<AppCompatTextView>()
    private lateinit var linearLayout: LinearLayoutCompat
    private lateinit var selectorView: View

    private var locale: String = LocalizedApp.locale
    private var currentOffset = 0f
    private var isAnimating = false

    constructor(context: Context) : super(context) {
        initialize(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        clipToPadding = false
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchWidget)
            selectorColor = typedArray.getColor(R.styleable.SwitchWidget_selectorColor, Color.WHITE)
            defaultTextColor =
                typedArray.getColor(R.styleable.SwitchWidget_defaultTextColor, Color.BLACK)
            selectedTextColor =
                typedArray.getColor(R.styleable.SwitchWidget_selectedTextColor, Color.RED)
            selectorCornerRadius = typedArray.getDimension(
                R.styleable.SwitchWidget_selectorCornerRadius,
                Float.MAX_VALUE
            )
            textSize =
                typedArray.getDimension(R.styleable.SwitchWidget_android_textSize, context.dp(14))
            lineSpacingExtra =
                typedArray.getDimension(
                    R.styleable.SwitchWidget_android_lineSpacingExtra,
                    context.dp(0)
                )
            val fontIdEn = typedArray.getResourceId(R.styleable.SwitchWidget_fontFamilyEn, 0)
            if (fontIdEn != 0) fontFamilyEn = ResourcesCompat.getFont(context, fontIdEn)
            val fontIdAr = typedArray.getResourceId(R.styleable.SwitchWidget_fontFamilyAr, 0)
            if (fontIdAr != 0) fontFamilyAr = ResourcesCompat.getFont(context, fontIdAr)
            val arrayResourceId = typedArray.getResourceId(R.styleable.SwitchWidget_switchItems, 0)
            if (arrayResourceId != 0) {
                val switchItemsArray = context.resources.obtainTypedArray(arrayResourceId)
                for (i in 0 until switchItemsArray.length()) {
                    val stringResourceId = switchItemsArray.getResourceId(i, 0)
                    if (stringResourceId != 0) switchStringResItems.add(stringResourceId)
                }
                switchItemsArray.recycle()
            }
            typedArray.recycle()
        }
        buildViews(context)
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable(SUPER_STATE, super.onSaveInstanceState())
        bundle.putFloat(CURRENT_OFFSET, currentOffset)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        when (state) {
            is Bundle -> {
                val superState = state.getParcelable<Parcelable>(SUPER_STATE)
                currentOffset = state.getFloat(CURRENT_OFFSET)
                superState?.let { super.onRestoreInstanceState(it) }
            }
            else -> super.onRestoreInstanceState(state)
        }
    }

    private fun buildViews(context: Context) {
        buildSelectorView(context)
        buildLinearLayout(context)
        switchStringResItems.forEachIndexed { index, item -> buildText(context, item, index) }
        post { setSelectionViewOffset(0f) }
    }

    private fun buildSelectorView(context: Context) {
        selectorView = View(context)
        val lp =
            LayoutParams(context.dp(24).toInt(), context.dp(24).toInt(), Gravity.CENTER_VERTICAL)
        selectorView.setBackgroundColor(selectorColor)
        selectorView.setCornerRadius(selectorCornerRadius / context.resources.displayMetrics.density)
        selectorView.elevation = context.dp(4)
        addView(selectorView, lp)
    }

    private fun buildLinearLayout(context: Context) {
        linearLayout = LinearLayoutCompat(context)
        linearLayout.elevation = context.dp(4)
        linearLayout.orientation = LinearLayoutCompat.HORIZONTAL
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(linearLayout, layoutParams)
    }

    private fun buildText(context: Context, stringResId: Int, position: Int) {
        val textView = AppCompatTextView(context)
        val layoutParams =
            LinearLayoutCompat.LayoutParams(0, LinearLayoutCompat.LayoutParams.MATCH_PARENT, 1f)
        textView.text = LocalizedApp.getResources(LocalizedApp.locale).getString(stringResId)
        textView.typeface = if (position == 0) fontFamilyEn else fontFamilyAr
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        textView.gravity = Gravity.CENTER
        textView.includeFontPadding = true
        textView.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        textView.setLineSpacing(lineSpacingExtra, 4f)
        textView.elevation = context.dp(5)
        textView.setTextColor(defaultTextColor)
        val horizontalPadding = context.dp(16).toInt()
        val verticalPadding = context.dp(4).toInt()
        textView.setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding)
        linearLayout.addView(textView, layoutParams)
        textViews.add(textView)
        textView.setOnClickListener { setSelectedIndex(position) }
    }

    fun setSelectedIndex(position: Int, animate: Boolean = true, notifyCallbacks: Boolean = true) {
        if (isAnimating) return
        val current = position.toFloat()
        if (animate) {
            isAnimating = true
            val previous = currentOffset
            ObjectAnimator.ofFloat(previous, current).apply {
                duration = 300L
                doOnEnd {
                    isAnimating = false
                    setSelectedIndex(position, animate = false, notifyCallbacks)
                }
                addUpdateListener { setSelectionViewOffset(it.animatedValue as Float) }
                start()
            }
        } else {
            currentOffset = current
            isAnimating = false
            setSelectionViewOffset(currentOffset)
            if (notifyCallbacks) {
                onIndexChanged?.invoke(position)
            }
        }
    }

    private fun setSelectionViewOffset(offset: Float) {
        val position = offset.toInt()
        val positionOffset = offset - position
        val current = textViews.getOrNull(position)
        val next = textViews.getOrNull(position + 1)
        val currentLeft = current?.left ?: 0
        val nextLeft = next?.left ?: currentLeft
        val currentWidth = current?.width ?: 0
        val nextWidth = next?.width ?: currentWidth
        val calculatedLeft = currentLeft + (nextLeft - currentLeft) * positionOffset
        val calculatedWidth = currentWidth + (nextWidth - currentWidth) * positionOffset
        val lp = selectorView.layoutParams
        lp.width = calculatedWidth.toInt()
        lp.height = linearLayout.height
        selectorView.layoutParams = lp
        selectorView.translationX = calculatedLeft
        current?.setTextColor(interpolateColor(positionOffset, selectedTextColor, defaultTextColor))
        next?.setTextColor(interpolateColor(positionOffset, defaultTextColor, selectedTextColor))
    }

    fun onLocaleChanged(locale: String) {
        this.locale = locale ?: LocalizedApp.LOCALE_EN
        val layoutDirection = View.LAYOUT_DIRECTION_LTR
//        this.layoutDirection = layoutDirection
        linearLayout.layoutDirection = layoutDirection
        val resources = LocalizedApp.getResources(this.locale)
        for (i in textViews.indices) {
            val stringRes = switchStringResItems[i]
            val textView = textViews[i]
            textView.text = resources.getString(stringRes)
            textView.typeface = if (i == 0) fontFamilyEn else fontFamilyAr
        }
        post { setSelectionViewOffset(currentOffset) }
    }
}

private fun Context.dp(dpValue: Number): Float {
    return resources.displayMetrics.density * dpValue.toFloat()
}

private fun View.dp(dpValue: Number) = context.dp(dpValue)

private class RoundedRectOutlineProvider(var radiusDp: Number) : ViewOutlineProvider() {
    override fun getOutline(view: View?, outline: Outline?) {
        if (view != null && outline != null) {
            val radiusPx = view.dp(radiusDp)
            val maxRadiusPx = min(view.width, view.height) / 2f
            outline.setRoundRect(
                0, 0,
                view.width, view.height,
                min(radiusPx, maxRadiusPx),
            )
        }
    }
}

private fun View.setCornerRadius(radiusDp: Number = Float.MAX_VALUE) {
    val outline =
        outlineProvider as? RoundedRectOutlineProvider ?: RoundedRectOutlineProvider(radiusDp)
    outline.radiusDp = radiusDp
    outlineProvider = outline
    clipToOutline = true
}

private fun interpolate(progress: Float, start: Int, end: Int): Int {
    return when {
        progress < 0 -> start
        progress > 1 -> end
        else -> (start + (end - start) * progress).toInt()
    }
}

private fun interpolateColor(progress: Float, start: Int, end: Int): Int {
    return Color.argb(
        interpolate(progress, Color.alpha(start), Color.alpha(end)),
        interpolate(progress, Color.red(start), Color.red(end)),
        interpolate(progress, Color.green(start), Color.green(end)),
        interpolate(progress, Color.blue(start), Color.blue(end))
    )
}