package com.app.telemed

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.app.telemed.databinding.EndOfQuestioningFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EndOfQuestioningFragment : QuestionFragment() {

    lateinit var binding: EndOfQuestioningFragmentBinding

    override val viewModel: EndOfQuestioningViewModel by navGraphViewModels(R.id.menu_navigation_xml) {
        defaultViewModelProviderFactory
    }

    override fun restoreState(savedInstanceState: Bundle?) {
        arguments?.let {
            viewModel.restoreState(it)
            var str = ""
            viewModel.getQuests().apply {
                forEachIndexed{ index, question ->
                    str += "$index ${question.comment} \n"
                }
            }
            Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStateRestored(question: Question) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return EndOfQuestioningFragmentBinding.inflate(inflater).run {
            binding = this
            root
        }
    }

    override fun setListeners() {

    }

    override fun manageLoading(b: Boolean) {

    }

    override fun <T> manageSuccess(obj: T?) {

    }

    override fun manageError(bool: Boolean) {

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.toQuestions.setOnClickListener {
            findNavController().popBackStack(R.id.menu_navigation_xml, true)
            findNavController().navigate(R.id.action_global_lessonFragment, bundleOf("back" to "lesson"))
        }
    }

}