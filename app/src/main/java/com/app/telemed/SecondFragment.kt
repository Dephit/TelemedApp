package com.app.telemed

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.app.telemed.databinding.FirstQuestionFragmentBinding
import com.app.telemed.databinding.SecondFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondFragment : QuestionFragment() {

    lateinit var bindings : SecondFragmentBinding

    override val viewModel: SecondViewModel by navGraphViewModels(R.id.menu_navigation_xml) {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return SecondFragmentBinding.inflate(inflater).run {
            bindings = this
            root
        }
    }

    override fun restoreState(savedInstanceState: Bundle?) {
        arguments?.let {
            viewModel.restoreState(it)
            viewModel.quest[viewModel.position].apply {
                bindings.questionTitle.text = getTitle()
                bindings.questionSubtitle.text = type.javaClass.name
            }
        }
    }

    override fun setListeners() {
        bindings.toQuestions.setOnClickListener {
            viewModel.position ++
            navigateToQuestion()
        }
    }

    override fun manageLoading(b: Boolean) {
    }

    override fun <T> manageSuccess(obj: T?) {
    }

    override fun manageError(bool: Boolean) {
    }

}