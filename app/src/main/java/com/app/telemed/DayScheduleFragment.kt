package com.app.telemed

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.app.telemed.databinding.DayScheduleFragmentBinding
import com.app.telemed.databinding.DayViewBinding
import com.app.telemed.fragments.baseFragments.BaseFragment
import com.app.telemed.interfaces.Lesson
import com.kizitonwose.calendarview.model.CalendarDay
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.util.*


@AndroidEntryPoint
class DayScheduleFragment : BaseFragment() {

    lateinit var binding: DayScheduleFragmentBinding

    override val viewModel: DayScheduleViewModel by navGraphViewModels(R.id.menu_navigation_xml) {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DayScheduleFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun restoreState(savedInstanceState: Bundle?) {
        setUpCalendar()
    }

    private fun setUpCalendar() {
        val lesson = arguments?.getParcelable<Lesson>("lesson")
        val day = arguments?.getSerializable("day") as CalendarDay

        val inf = LayoutInflater.from(context)
        binding.toolbar.title = "${DAY_WEEK_LIST[day.date.dayOfWeek.value - 1]}, ${day.date.dayOfMonth}-е ${MONTH_LIST[day.date.monthValue]}"

        for (i in 0..24){
            attachToView(inf, lesson, i)
        }
    }

    override fun setListeners() {
        binding.toolbar.menu.getItem(0).setOnMenuItemClickListener {
            findNavController().popBackStack()
        }
    }

    override fun manageLoading(b: Boolean) {
    }

    override fun <T> manageSuccess(obj: T?) {
    }

    override fun manageError(bool: Boolean) {

    }

    @SuppressLint("SetTextI18n")
    fun attachToView(inf: LayoutInflater, lesson: Lesson?, i: Int) {
        val v = DayViewBinding.inflate(inf, null, false)
        lesson?.apply {
            if(date.get(Calendar.HOUR) == i){
                v.event.visibility = View.VISIBLE
                if(passed) {
                    v.event.alpha = 0.5f
                    v.alarmIcon.setVisible(false)
                }
                v.root.setOnClickListener {
                    findNavController().navigate(R.id.action_dayScheduleFragment_to_lessonInfoFragment,
                    bundleOf(
                        "lesson" to lesson
                    )
                    )
                }

                v.eventTime.text = "${lesson.date.get(Calendar.HOUR).get00time()}:${lesson.date.get(Calendar.MINUTE).get00time()}"
                v.evenDesc.text = desc
            }else v.event.visibility = View.INVISIBLE
        }

        v.currentHour.setVisible(LocalDateTime.now().hour == i)
        if(LocalDateTime.now().hour == i) {
            v.currentHour.y = (v.root.height * (LocalDateTime.now().minute / 60)).toFloat()
        }
        v.timeText.text = "${i.get00time()}:00"

        binding.days.addView(v.root)
    }
}
