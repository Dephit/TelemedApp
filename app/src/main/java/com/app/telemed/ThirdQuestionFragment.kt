package com.app.telemed

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.app.telemed.databinding.SecondFragmentBinding
import com.app.telemed.databinding.ThirdQuestionFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThirdQuestionFragment : QuestionFragment() {

    lateinit var bindings : ThirdQuestionFragmentBinding

    override val viewModel: ThirdQuestionViewModel by navGraphViewModels(R.id.menu_navigation_xml) {
        defaultViewModelProviderFactory
    }

    override fun onStateRestored(question: Question) {
        bindings.questionTitle.text = getString(R.string.leave_your_comment)
        bindings.questionSubtitle.text = getString(R.string.comment)
        bindings.questionNum2.text = getString(R.string.comment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return ThirdQuestionFragmentBinding.inflate(inflater).run {
            bindings = this
            root
        }
    }

    override fun setListeners() {
        with(bindings){
            toQuestions.setOnClickListener {
                hideKeyboard()
                viewModel.position ++
                navigateToQuestion()
            }

            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            toQuestions.isEnabled = false
            commentEditText.addTextChangedListener {
                viewModel.getCurrentQuestion().comment = it.toString()
                toQuestions.isEnabled = true
            }
        }
    }

    override fun manageLoading(b: Boolean) {
    }

    override fun <T> manageSuccess(obj: T?) {
    }

    override fun manageError(bool: Boolean) {
    }

}