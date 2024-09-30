package com.gco.gco.utils

import android.content.Context
import androidx.fragment.app.Fragment

val Context.dp: Float
    get() = resources.displayMetrics.density

val Fragment.dp: Float
    get() = resources.displayMetrics.density
