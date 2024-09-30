package com.gco.gco.utils

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.Px
import kotlin.math.min

class RoundedRectOutlineProvider(@Px var radiusPx: Float) : ViewOutlineProvider() {
    override fun getOutline(view: View?, outline: Outline?) {
        if (view != null && outline != null) {
            val maxRadiusPx = min(view.width, view.height) / 2f
            outline.setRoundRect(
                0, 0,
                view.width, view.height,
                min(radiusPx, maxRadiusPx),
            )
        }
    }
}

class RoundedBottomRectOutlineProvider(@Px var radiusPx: Float) : ViewOutlineProvider() {
    override fun getOutline(view: View?, outline: Outline?) {
        if (view != null && outline != null) {
            val maxRadiusPx = min(view.width, view.height) / 2f
            val radius = min(radiusPx, maxRadiusPx)
            outline.setRoundRect(
                0, -radius.toInt(),
                view.width, view.height,
                radius,
            )
        }
    }
}

class RoundedTopRectOutlineProvider(@Px var radiusPx: Float) : ViewOutlineProvider() {
    override fun getOutline(view: View?, outline: Outline?) {
        if (view != null && outline != null) {
            val maxRadiusPx = min(view.width, view.height) / 2f
            val radius = min(radiusPx, maxRadiusPx)
            outline.setRoundRect(
                0, 0,
                view.width, (view.height + radius).toInt(),
                radius,
            )
        }
    }
}