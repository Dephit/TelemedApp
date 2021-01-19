package com.app.telemed.fragments

import android.annotation.SuppressLint
import android.view.View
import com.app.telemed.databinding.MonthHeaderBinding
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.YearMonth

class MonthViewContainer(view: View) : ViewContainer(view) {
    private val textView = MonthHeaderBinding.bind(view).monthYearText
    private val pButton = MonthHeaderBinding.bind(view).prevMonth
    private val nButton = MonthHeaderBinding.bind(view).nextMonth

    @SuppressLint("SetTextI18n")
    fun bind(month: CalendarMonth, param: (YearMonth) -> Unit){
        val list = listOf(
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
        textView.text = "${month.year} ${list[month.month - 1]}"
        nButton.setOnClickListener { param(month.yearMonth.plusMonths(1)) }
        pButton.setOnClickListener { param(month.yearMonth.plusMonths(-1)) }
    }
}