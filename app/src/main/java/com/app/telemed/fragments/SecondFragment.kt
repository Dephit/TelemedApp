package com.app.telemed.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.view.forEach
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.app.telemed.R
import com.app.telemed.viewModels.SecondViewModel
import com.app.telemed.databinding.CheckBoxBinding
import com.app.telemed.databinding.SecondFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondFragment : QuestionFragment() {

    lateinit var bindings : SecondFragmentBinding

    override val viewModel: SecondViewModel by navGraphViewModels(R.id.menu_navigation_xml) {
        defaultViewModelProviderFactory
    }

    override fun onStateRestored(question: Question) {
        bindings.questionTitle.text = getTitle()
        bindings.questionSubtitle.text = getString(R.string.chose_one_of_variants)
        bindings.questionNum.text = getNumText()
        val li = LayoutInflater.from(context)
        question.variants?.forEach { variant ->
            addVariant(li, variant)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return SecondFragmentBinding.inflate(inflater).run {
            bindings = this
            root
        }
    }

    private fun addVariant(li: LayoutInflater, string: String) {
        val variantView = CheckBoxBinding.inflate(li)
        variantView.checkBox2.text = string
        variantView.checkBox2.setOnClickListener {
            bindings.variants.forEach { vv->
                CheckBoxBinding.bind(vv).checkBox2.isChecked = false
            }
            (it as CheckBox).isChecked = true
            bindings.toQuestions.isEnabled = true
            viewModel.getCurrentQuestion().comment = string
        }
        bindings.variants.addView(variantView.root)
    }

    override fun setListeners() {
        with(bindings){
            toQuestions.isEnabled = false
            toQuestions.setOnClickListener {
                viewModel.position ++
                navigateToQuestion()
            }

            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
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