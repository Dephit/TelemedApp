package com.app.telemed.fragments

import android.view.View
import com.app.telemed.databinding.CalendarDayLayoutBinding
import com.app.telemed.interfaces.Lesson
import com.app.telemed.isCurrentDay
import com.app.telemed.setVisible
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.ViewContainer

class DayViewContainer(view: View) : ViewContainer(view) {
    fun bind(lesson: Lesson?, day: CalendarDay, cMonth: Int, onDayPressed: (Lesson) -> Unit) {
        if(null != lesson) {
            lessonText.text = lesson.title
            if(lesson.passed)
                lessonText.alpha = 0.5f
            lessonText.setVisible(true)
            /*if (Build.VERSION.SDK_I   NT >= Build.VERSION_CODES.M) {
                textView.setTextColor(view.context!!.getColor(R.color.green_color_succsess))
            }else {
                textView.setTextColor(view.context!!.resources.getColor(R.color.green_color_succsess))
            }*/
            view.setOnClickListener { onDayPressed(lesson) }
        }else {
            lessonText.setVisible(false)
        }

        view.isActivated = day.isCurrentDay()
        if(cMonth != day.date.monthValue)
            textView.alpha = 0.3f
        textView.text = day.date.dayOfMonth.toString()

    }



    private val textView = CalendarDayLayoutBinding.bind(view).calendarDayText
    private val lessonText = CalendarDayLayoutBinding.bind(view).lessonText
}