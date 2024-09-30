package com.mbt.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withScale
import androidx.core.graphics.withTranslation

class AppBarImageView : View {
    var drawable: Drawable? = null
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        initialize(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.AppBarImageView)
        drawable = array.getDrawable(R.styleable.AppBarImageView_android_src)
        array.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val drawable = this.drawable
        if (drawable != null && drawable.intrinsicWidth > 0 && drawable.intrinsicHeight > 0) {
            val width = MeasureSpec.getSize(widthMeasureSpec)
            val height = MeasureSpec.getSize(heightMeasureSpec)
            val heightRatio =  height.toFloat() / drawable.intrinsicHeight.toFloat()
            val scaledWidth = (drawable.intrinsicWidth * heightRatio).toInt()
            if (scaledWidth > width) {
                setMeasuredDimension(width, height)
            } else {
                val widthRatio = width.toFloat() / drawable.intrinsicWidth.toFloat()
                val scaledHeight = (drawable.intrinsicHeight.toFloat() * widthRatio).toInt()
                setMeasuredDimension(width, scaledHeight)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        val drawable = this.drawable
        if (drawable != null && canvas != null) {
            if (drawable.intrinsicWidth > 0 && drawable.intrinsicHeight > 0) {
                val heightRatio =  height.toFloat() / drawable.intrinsicHeight.toFloat()
                val scaledWidth = (drawable.intrinsicWidth * heightRatio).toInt()
                val horizontalOffset = (width - scaledWidth) / 2f
                drawable.setBounds(
                    horizontalOffset.toInt(), 0,
                    scaledWidth + horizontalOffset.toInt(), height
                )
            } else {
                drawable.setBounds(0, 0, width, height)
            }
            drawable.draw(canvas)
        }
    }
}