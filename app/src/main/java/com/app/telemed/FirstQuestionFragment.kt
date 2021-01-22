package com.app.telemed

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.app.telemed.databinding.FirstQuestionFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstQuestionFragment : QuestionFragment() {

    lateinit var bindings : FirstQuestionFragmentBinding

    override val viewModel: FirstQuestionViewModel by navGraphViewModels(R.id.menu_navigation_xml) {
        defaultViewModelProviderFactory
    }

    override fun onStateRestored(question: Question) {
        bindings.questionTitle.text = getTitle()
        bindings.questionSubtitle.text = question.question
        bindings.questionNum.text = getNumText()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return FirstQuestionFragmentBinding.inflate(inflater).run {
            bindings = this
            root
        }
    }

    override fun setListeners() {
        with(bindings){
            toQuestions.setOnClickListener {
                viewModel.position ++
                navigateToQuestion()
            }

            toolbar2.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            manageProgress(-1)
            toQuestions.isEnabled = false

            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    toQuestions.isEnabled = true
                    viewModel.getCurrentQuestion().comment = progress.toString()

                    when {
                        progress > 90 -> seekBar?.progress = 100
                        progress > 80 -> seekBar?.progress = 90
                        progress > 70 -> seekBar?.progress = 80
                        progress > 60 -> seekBar?.progress = 70
                        progress > 50 -> seekBar?.progress = 60
                        progress > 40 -> seekBar?.progress = 50
                        progress > 30 -> seekBar?.progress = 40
                        progress > 20 -> seekBar?.progress = 30
                        progress > 10 -> seekBar?.progress = 20
                        progress > 0 -> seekBar?.progress = 10
                        else -> seekBar?.progress = 0
                    }
                    seekBar?.progress?.let { manageProgress(it) }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }

            })
        }
    }

    @SuppressLint("SetTextI18n")
    private fun manageProgress(value: Int) {
        with(bindings) {
            bar1.isActivated = value >= 0
            bar2.isActivated = value >= 10
            bar3.isActivated = value >= 20
            bar4.isActivated = value >= 30
            bar6.isActivated = value >= 40
            bar7.isActivated = value >= 50
            bar9.isActivated = value >= 60
            bar10.isActivated = value >= 70
            bar11.isActivated = value >= 80
            bar12.isActivated = value >= 90
            bar13.isActivated = value >= 100

            text1.setSelect(value == 0)
            text2.setSelect(value == 10)
            text3.setSelect(value == 20)
            text4.setSelect(value == 30)
            text5.setSelect(value == 40)
            text6.setSelect(value == 50)
            text7.setSelect(value == 60)
            text8.setSelect(value == 70)
            text9.setSelect(value == 80)
            text10.setSelect(value == 90)
            text11.setSelect(value == 100)

            if(value == -1)
                mark.text = "- ${getString(R.string.from_text)} 10"
            else
                mark.text = "${value / 10} ${getString(R.string.from_text)} 10"
        }
    }

    override fun manageLoading(b: Boolean) {

    }

    override fun <T> manageSuccess(obj: T?) {

    }

    override fun manageError(bool: Boolean) {

    }

}

private fun TextView.setSelect(b: Boolean) {
    if(b){
        setTextColor(getColor(context, R.color.primary_button_color))
        translationY = 3f
    }else{
        setTextColor(getColor(context, R.color.gray_text_color))
        translationY = 0f
    }
}
