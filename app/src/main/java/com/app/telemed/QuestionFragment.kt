package com.app.telemed

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.app.telemed.fragments.baseFragments.BaseFragment

abstract class QuestionFragment : BaseFragment() {

    abstract override val viewModel: IQuestionBaseViewModel

    protected fun getTitle(): CharSequence {
        return "${getString(R.string.question_number)} №${viewModel.getCurrentPosition() + 1}"
    }

    override fun restoreState(savedInstanceState: Bundle?) {
        arguments?.let {
            viewModel.restoreState(it)
            viewModel.getCurrentQuestion().apply {
                onStateRestored(this)
            }
        }
    }

    abstract fun onStateRestored(question: Question)

    protected fun getNumText(): CharSequence {
        return "${getString(R.string.question_number)} ${viewModel.getCurrentPosition() + 1} ${getString(R.string.from_text)} ${viewModel.getQuests().size - 1}"
    }

    protected fun navigateToQuestion() {
        val bundle = bundleOf(
            viewModel.CURRENT_QUESTION to viewModel.getCurrentPosition(),
            viewModel.QUESTIONS to viewModel.getQuests()
        )
        if(viewModel.getCurrentPosition() >= viewModel.getQuests().size){
            findNavController().navigate(R.id.action_global_endOfQuestioningFragment, bundle)
            return
        }
        when(viewModel.getCurrentQuestion().type){
            QuestionType.Mark -> findNavController().navigate(R.id.action_global_firstQuestionFragment, bundle)
            QuestionType.Select -> findNavController().navigate(R.id.action_global_secondFragment, bundle)
            QuestionType.Comment -> findNavController().navigate(R.id.action_global_thirdQuestionFragment, bundle)
        }
    }
}
