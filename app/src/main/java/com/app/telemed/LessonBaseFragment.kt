package com.app.telemed

import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.app.telemed.databinding.LessonInfoFragmentBinding
import com.app.telemed.fragments.baseFragments.BaseFragment
import com.app.telemed.interfaces.Lesson
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

abstract class LessonBaseFragment: BaseFragment(){

    abstract override val viewModel: ILessonViewModel

    protected fun setSignLessonButtoun(toBeginningOfLessonButton: Button) {
        toBeginningOfLessonButton.setOnClickListener {
            Toast.makeText(requireContext(), "Вы записались", Toast.LENGTH_SHORT).show()
        }
    }

    protected fun setBeginLessonButtoun(toBeginningOfLessonButton: Button) {
        toBeginningOfLessonButton.setOnClickListener {
            startActivity(
                Intent(context, LessonInProgressActivity::class.java)
                    .putExtra(LESSONS, viewModel.getLesson())
            )
        }
    }

    protected fun setDoctors(doctorAvatar: ImageView, instructorAvatar: ImageView, doctorText: TextView, instructorText: TextView) {
        doctorAvatar.clipToOutline = true
        doctorText.text = if(BuildConfig.IS_REHUB) getString(R.string.doctor_text) else getString(R.string.coach_text)
        instructorText.text = if(BuildConfig.IS_REHUB) getString(R.string.instructor_text) else getString(R.string.coach_2_text)
        instructorAvatar.clipToOutline = true
    }

    protected fun setTimer(timerText: TextView) {
            lifecycleScope.launch {
                viewModel.getTimer()
                    .collect {
                        if(it == "") {
                            timerText.setVisible(false)
                        } else {
                            if(!timerText.isVisible)
                                timerText.setVisible(true)
                            timerText.text = it
                        }
                    }
        }
    }
}