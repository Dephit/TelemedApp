package com.app.telemed

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.app.telemed.databinding.CustomLayoutBinding
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.kizitonwose.calendarview.model.CalendarDay
import java.time.LocalDate
import java.util.*

fun View.setVisible(b: Boolean) {
    visibility = if(b){
        View.VISIBLE
    }else
        View.GONE
}

fun createBitmapFromLayout(tv: View): Bitmap? {
    val spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    tv.measure(spec, spec)
    tv.layout(0, 0, tv.measuredWidth, tv.measuredHeight)
    val b = Bitmap.createBitmap(tv.measuredWidth, tv.measuredWidth,
        Bitmap.Config.ARGB_8888)
    val c = Canvas(b)
    c.translate((-tv.scrollX).toFloat(), (-tv.scrollY).toFloat())
    tv.draw(c)
    return b
}

fun Calendar.setDay(i: Int) {
    set(Calendar.DAY_OF_MONTH, i)
}

fun Calendar.setHourOfDay(i: Int) {
    set(Calendar.HOUR_OF_DAY, i)
}

fun getColor(requireContext: Context, id: Int): Int {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        requireContext.getColor(id)
    }else
        requireContext.resources.getColor(id)
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

fun CalendarDay.isCurrentDay(): Boolean {
    return date == LocalDate.now()
}

fun showAlertDialogButtonClicked(view: View, listener: ()-> Unit, onCancel: ()-> Unit) {
    // create an alert builder
    val builder = AlertDialog.Builder(view.context)
    // set the custom layout
    val customLayout = CustomLayoutBinding.inflate(LayoutInflater.from(view.context))
    builder.setView(customLayout.root)
    // add a button
    builder.setOnDismissListener { onCancel() }

    customLayout.leaveButton.setOnClickListener {
        listener()
    }
    // create and show the alert dialog
    val dialog = builder.create()
    dialog.show()
    customLayout.cancel.setOnClickListener {
        dialog.dismiss()
        dialog.cancel()
    }
}

fun Int.get00time()= String.format("%02d", this)

val MONTH_LIST = listOf(
    "Январь",
    "Февраль",
    "Март",
    "Апрель",
    "Май",
    "Июнь",
    "Июль",
    "Август",
    "Сентябрь",
    "Октябрь",
    "Ноябрь",
    "Декабрь",
)

val DAY_WEEK_LIST = listOf(
    "Пн",
    "Вт",
    "Ср",
    "Чт",
    "Пт",
    "Сб",
    "Вс"
)

val LESSONS = "LESSONS"

