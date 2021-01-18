package com.app.telemed.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.app.telemed.R
import com.app.telemed.databinding.CalendarDayLayoutBinding
import com.app.telemed.databinding.MonthHeaderBinding
import com.app.telemed.databinding.ScheduleFragmentBinding
import com.app.telemed.fragments.baseFragments.BaseFragment
import com.app.telemed.interfaces.Lesson
import com.app.telemed.setVisible
import com.app.telemed.viewModels.ScheduleViewModel
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import dagger.hilt.android.AndroidEntryPoint
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*


@AndroidEntryPoint
class ScheduleFragment : BaseFragment() {

    lateinit var binding: ScheduleFragmentBinding

    override val viewModel: ScheduleViewModel by navGraphViewModels(R.id.menu_navigation_xml) {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ScheduleFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun restoreState(savedInstanceState: Bundle?) {
        setUpCalendar()
    }

    private fun setUpCalendar() {
        binding.calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View): DayViewContainer {
                return  DayViewContainer(view)
            }

            override fun bind(container: DayViewContainer, day: CalendarDay) {
                val lesson = viewModel.list.find { it.date.get(Calendar.DAY_OF_MONTH) == day.day}
                container.bind(lesson, day)
            }
        }

        binding.calendarView.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                container.bind(month)
            }

            override fun create(view: View): MonthViewContainer  = MonthViewContainer(view)

        }

        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(10)
        val lastMonth = currentMonth.plusMonths(10)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        binding.calendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
        binding.calendarView.scrollToMonth(currentMonth)
    }

    override fun setListeners() {

    }

    override fun manageLoading(b: Boolean) {
    }

    override fun <T> manageSuccess(obj: T?) {
        binding.calendarView.notifyCalendarChanged()

    }

    override fun manageError(bool: Boolean) {

    }

}


class DayViewContainer(view: View) : ViewContainer(view) {
    fun bind(lesson: Lesson?, day: CalendarDay) {

        if(null != lesson) {
            lessonText.text = lesson.title
            if(lesson.passed)
                lessonText.alpha = 0.5f
            lessonText.setVisible(true)
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                textView.setTextColor(view.context!!.getColor(R.color.green_color_succsess))
            }else {
                textView.setTextColor(view.context!!.resources.getColor(R.color.green_color_succsess))
            }*/
        }else {
            textView.text = day.date.dayOfMonth.toString()
            lessonText.setVisible(false)
        }
    }

    private val textView = CalendarDayLayoutBinding.bind(view).calendarDayText
    private val lessonText = CalendarDayLayoutBinding.bind(view).lessonText
}

class MonthViewContainer(view: View) : ViewContainer(view) {
    private val textView = MonthHeaderBinding.bind(view).monthYearText

    @SuppressLint("SetTextI18n")
    fun bind(month: CalendarMonth){
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

    }
}

