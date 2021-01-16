package com.app.telemed

import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress

fun View.setVisible(b: Boolean) {
    visibility = if(b){
        View.VISIBLE
    }else
        View.GONE
}

fun EditText.togglePasswordVisibility(bool: Boolean): Boolean {
    transformationMethod = if(bool){
        null
    }else {
        PasswordTransformationMethod.getInstance()
    }
    return bool
}

fun Button.enableProgress(b: Boolean, textId: Int) {
    if(b){
        showProgress {
            progressColor = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                context.getColor(R.color.primary_button_color)
            }else {
                context.resources?.getColor(R.color.primary_button_color)
            }
        }
    }else {
        hideProgress(textId)
    }
}