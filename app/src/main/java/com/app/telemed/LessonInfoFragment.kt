package com.app.telemed

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.app.telemed.databinding.LessonInfoFragmentBinding
import com.app.telemed.fragments.baseFragments.BaseFragment
import com.app.telemed.interfaces.Lesson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@AndroidEntryPoint
class LessonInfoFragment : BaseFragment() {

    override val viewModel: LessonInfoViewModel by navGraphViewModels(R.id.menu_navigation_xml) {
        defaultViewModelProviderFactory
    }

    lateinit var binding: LessonInfoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LessonInfoFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun restoreState(savedInstanceState: Bundle?) {
        viewModel.setLesson(arguments?.getParcelable(LESSONS))
    }

    override fun setListeners() {
        with(binding){
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            toBeginningOfLessonButton.setOnClickListener {
                findNavController().navigate(R.id.action_global_lessonInProgressFragment)
            }
        }
    }

    override fun manageLoading(b: Boolean) {

    }

    @SuppressLint("SetTextI18n")
    override fun <T> manageSuccess(obj: T?) {
        (obj as Lesson).let {lesson->
            with(binding){

                toolbar.title = lesson.title

                doctorAvatar.clipToOutline = true
                doctorText.text = if(BuildConfig.IS_REHUB) getString(R.string.doctor_text) else getString(R.string.coach_text)
                instructorAvatar.clipToOutline = true
                instructorText.text = if(BuildConfig.IS_REHUB) getString(R.string.instructor_text) else getString(R.string.coach_2_text)

                doctorComent.setVisible(lesson.passed)
                instructorComent.setVisible(lesson.passed)
                pulseLayout.setVisible(lesson.passed)

                time.text = "${lesson.date.get(Calendar.DAY_OF_MONTH)} ${MONTH_LIST[lesson.date.get(Calendar.MONTH)]} ${lesson.date.get(Calendar.HOUR).get00time()}:${lesson.date.get(Calendar.MINUTE).get00time()}"

                if(Random.nextBoolean()){
                    soonWillBeginIcon.setVisible(false)
                    endingText.setVisible(false)
                    toBeginningOfLessonButton.setText(R.string.make_appointment)
                    toBeginningOfLessonButton.setVisible(!BuildConfig.IS_REHUB)
                }

                if(lesson.passed){
                    yourPulseMin.text = Random.nextInt(220).toString()
                    yourPulseMid.text = Random.nextInt(220).toString()
                    yourPulseMax.text = Random.nextInt(220).toString()

                    timerText.setVisible(!lesson.passed)
                    endingText.setVisible(true)
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        endingText.setTextColor(requireContext().getColor(R.color.gray_text_color))
                    }else
                        endingText.setTextColor(requireContext().resources.getColor(R.color.gray_text_color))
                    endingText.text = getString(R.string.finished)
                }else {

                    val daysLeft = Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - lesson.date.get(Calendar.DAY_OF_YEAR)
                    val hoursLeft = Calendar.getInstance().get(Calendar.HOUR) - lesson.date.get(Calendar.HOUR)
                    val minutesLeft = Calendar.getInstance().get(Calendar.MINUTE) - lesson.date.get(Calendar.MINUTE)
                    lifecycleScope.launch(Dispatchers.Main) {
                        val totalSeconds = TimeUnit.MINUTES.toSeconds(2)
                        val tickSeconds = 1
                        for (second in totalSeconds downTo tickSeconds) {
                            val time = String.format("Осталось %2d дн. %2d ч. %02d м.",
                                daysLeft,hoursLeft, minutesLeft
                            )
                            timerText.text = time
                            delay(60000)
                        }
                    }
                }

            }
        }
    }

    override fun manageError(bool: Boolean) {

    }


}