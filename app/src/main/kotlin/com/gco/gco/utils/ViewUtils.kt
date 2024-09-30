package com.gco.gco.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.getSystemService
import com.gco.gco.R
import com.mbt.localization.LocalizedApp
import java.util.*

object ViewUtils {

    fun View.changeLayoutDirection(passedLanguage: String? = null) {
        layoutDirection = when (passedLanguage ?: LocalizedApp.locale) {
            LocalizedApp.LOCALE_AR -> View.LAYOUT_DIRECTION_RTL
            else -> View.LAYOUT_DIRECTION_LTR
        }
    }

    fun AppCompatEditText.hideKeyboard() {
        try {
            val inputMethodManager = context.getSystemService<InputMethodManager>()!!
            inputMethodManager.hideSoftInputFromWindow(
                windowToken,
                InputMethodManager.RESULT_UNCHANGED_SHOWN
            )
            clearFocus()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun EditText.hideKeyboard() {
        try {
            context.getSystemService<InputMethodManager>()
                ?.hideSoftInputFromWindow(windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
            clearFocus()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun ArrayList<Any>?.hideKeyboards() {
        this?.let { fields ->
            fields.forEach { field ->
                when (field) {
                    is AppCompatEditText -> field.hideKeyboard()
                    is EditText -> field.hideKeyboard()
                    else -> {}
                }
            }
        }
    }

    fun setViewsTypeFace(views: ArrayList<TextView>? = null, typeface: Typeface? = null) {
        views?.forEach { it.setTypeFace(typeface) }
    }

    private fun TextView.setTypeFace(tf: Typeface? = null) { typeface = tf }

    fun Context.showToastMessage(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    /* @SuppressLint("ClickableViewAccessibility")
     fun EditText.setupClearButtonWithAction() {
         addTextChangedListener(object : TextWatcher {
             override fun afterTextChanged(editable: Editable?) {
                 val clearIcon = if (editable?.isNotEmpty() == true) R.drawable.ic_baseline_cancel_24 else 0
                 setCompoundDrawablesWithIntrinsicBounds(0, 0, clearIcon, 0)
             }

             override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
             override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
         })

         setOnTouchListener(View.OnTouchListener { _, event ->
             if (event.action == MotionEvent.ACTION_UP) {
                 if (event.rawX >= (this.right - this.compoundPaddingRight)) {
                     this.setText("")
                     return@OnTouchListener true
                 }
             }
             return@OnTouchListener false
         })
     }*/
}