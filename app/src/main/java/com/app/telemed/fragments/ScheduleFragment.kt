package com.app.telemed.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.app.telemed.LESSONS
import com.app.telemed.R
import com.app.telemed.databinding.ScheduleFragmentBinding
import com.app.telemed.fragments.baseFragments.BaseFragment
import com.app.telemed.getColor
import com.app.telemed.interfaces.Lesson
import com.app.telemed.viewModels.ScheduleViewModel
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
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
        val currentMonth = YearMonth.now()
        var cMonth = currentMonth.month.value

        binding.calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View): DayViewContainer =
                DayViewContainer(view)

            override fun bind(container: DayViewContainer, day: CalendarDay) =
                container.bind(viewModel.list.find { it.date.get(Calendar.DAY_OF_MONTH) == day.day}, day, cMonth) { lesson: Lesson->
                    val bundle = bundleOf(
                        LESSONS to viewModel.list.filter { it.date.get(Calendar.DAY_OF_MONTH) == day.day },
                        "day" to day
                    )
                    findNavController().navigate(R.id.schedule_to_day_schedule, bundle)
                }
        }

        binding.calendarView.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                cMonth = month.month
                container.bind(month) { m: YearMonth -> binding.calendarView.scrollToMonth(m) }
            }

            override fun create(view: View): MonthViewContainer  = MonthViewContainer(view)
        }

        val firstMonth = currentMonth.minusMonths(10)
        val lastMonth = currentMonth.plusMonths(10)
        val firstDayOfWeek = WeekFields.of(Locale("ru", "RU")).firstDayOfWeek

        binding.calendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
        binding.calendarView.scrollToMonth(currentMonth)
    }

    override fun setListeners() {
        binding.swiperefresh.setColorSchemeColors(getColor(requireContext(), R.color.primary_button_color))

        binding.swiperefresh.setOnRefreshListener {
            viewModel.getEvents()
        }
    }

    override fun manageLoading(b: Boolean) {
        binding.swiperefresh.isRefreshing = b
        binding.swiperefresh.isVisible = b
    }

    override fun <T> manageSuccess(obj: T?) {
        if(obj is Bundle){
            findNavController().navigate(R.id.schedule_to_day_schedule, obj)
        }else
            binding.calendarView.notifyCalendarChanged()
    }

    override fun manageError(bool: Boolean) {

    }

}



