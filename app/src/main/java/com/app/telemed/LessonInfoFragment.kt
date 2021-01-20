package com.app.telemed

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.app.telemed.databinding.LessonInfoFragmentBinding
import com.app.telemed.fragments.baseFragments.BaseFragment
import com.app.telemed.interfaces.Lesson
import com.app.telemed.viewModels.baseViewModels.ModelState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
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
    ): View{
        binding = LessonInfoFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun restoreState(savedInstanceState: Bundle?) {
        viewModel.getState().value = ModelState.Normal
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
            setTexts(binding, lesson)
            when {
                lesson.isPassed() -> setPassed(binding, lesson)
                lesson.isSoon() -> setSoon(binding, lesson)
                else -> setNotSoon(binding, lesson)
            }
            setTimer(binding, lesson)
        }
    }

    private fun setTimer(binding: LessonInfoFragmentBinding, lesson: Lesson) {
        if(!lesson.isPassed()) {
            lifecycleScope.launch(Dispatchers.Main) {
                var daysLeft = Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - lesson.date.get(Calendar.DAY_OF_YEAR)
                var hoursLeft = Calendar.getInstance().get(Calendar.HOUR) - lesson.date.get(Calendar.HOUR)
                var minutesLeft = Calendar.getInstance().get(Calendar.MINUTE) - lesson.date.get(Calendar.MINUTE)

                while (daysLeft != 0 || hoursLeft != 0 || minutesLeft != 0 && viewModel.getState().value is ModelState.Success<*>) {
                    when {
                        minutesLeft > 0 -> {
                            minutesLeft--
                        }
                        hoursLeft > 0 -> {
                            minutesLeft = 59
                            hoursLeft --
                        }
                        daysLeft > 0 -> {
                            minutesLeft = 59
                            hoursLeft = 23
                            daysLeft --
                        }
                    }
                    val time = String.format("Осталось %2d дн. %2d ч. %02d м.",
                            daysLeft,hoursLeft, minutesLeft
                    )
                    binding.timerText.text = time
                    binding.timerText.setVisible(!(daysLeft == 0 && hoursLeft == 0 && minutesLeft == 0))
                    delay(1000)
                }
            }
        }
    }

    private fun setTexts(binding: LessonInfoFragmentBinding, lesson: Lesson) {
        with(binding){
            toolbar.title = lesson.title

            doctorAvatar.clipToOutline = true
            doctorText.text = if(BuildConfig.IS_REHUB) getString(R.string.doctor_text) else getString(R.string.coach_text)
            instructorAvatar.clipToOutline = true
            instructorText.text = if(BuildConfig.IS_REHUB) getString(R.string.instructor_text) else getString(R.string.coach_2_text)
            time.text = "${lesson.date.get(Calendar.DAY_OF_MONTH)} ${MONTH_LIST[lesson.date.get(Calendar.MONTH)]} ${lesson.date.get(Calendar.HOUR).get00time()}:${lesson.date.get(Calendar.MINUTE).get00time()}"
        }
    }

    private fun setNotSoon(binding: LessonInfoFragmentBinding, lesson: Lesson) {
        with(binding){
            endingText.setVisible(false)
            doctorComent.setVisible(false)
            instructorComent.setVisible(false)
            pulseLayout.setVisible(false)
            soonWillBeginIcon.setVisible(false)

            if(!BuildConfig.IS_REHUB){
                toBeginningOfLessonButton.setText(R.string.make_appointment)
            }
            toBeginningOfLessonButton.setVisible(!BuildConfig.IS_REHUB)
        }
    }

    private fun setSoon(binding: LessonInfoFragmentBinding, lesson: Lesson) {
        with(binding){
            soonWillBeginIcon.setVisible(true)
            endingText.setVisible(true)
            toBeginningOfLessonButton.setVisible(true)
            doctorComent.setVisible(false)
            instructorComent.setVisible(false)
            pulseLayout.setVisible(false)

            endingText.text = getString(R.string.soon_will_start)
            toBeginningOfLessonButton.setText(R.string.to_beginning_of_lesson_text)
        }
    }

    private fun setPassed(binding: LessonInfoFragmentBinding, lesson: Lesson) {
        with(binding){
            doctorComent.setVisible(true)
            instructorComent.setVisible(true)
            pulseLayout.setVisible(true)
            toBeginningOfLessonButton.setVisible(false)
            timerText.setVisible(false)
            endingText.setVisible(true)
            soonWillBeginIcon.setVisible(false)

            yourPulseMin.text = Random.nextInt(220).toString()
            yourPulseMid.text = Random.nextInt(220).toString()
            yourPulseMax.text = Random.nextInt(220).toString()

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                endingText.setTextColor(requireContext().getColor(R.color.gray_text_color))
            }else
                endingText.setTextColor(requireContext().resources.getColor(R.color.gray_text_color))

            endingText.text = getString(R.string.finished)
        }
    }

    override fun manageError(bool: Boolean) {

    }


}