package com.app.telemed

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.navigation.navGraphViewModels
import com.app.telemed.databinding.CommonDataFragmentBinding
import com.app.telemed.fragments.baseFragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommonDataFragment : BaseFragment() {

    lateinit var binding: CommonDataFragmentBinding

    override val viewModel: CommonDataViewModel by navGraphViewModels(R.id.profile_navegation_xml) {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return CommonDataFragmentBinding.inflate(inflater).run {
            binding = this
            root
        }
    }

    override fun restoreState(savedInstanceState: Bundle?) {

    }

    override fun setListeners() {

    }

    override fun manageLoading(b: Boolean) {
        with(binding){
            heightEditText.addTextChangedListener {
                setButtonEnabled()
            }
            weightEditText.addTextChangedListener {
                setButtonEnabled()
            }
            ageEditText.addTextChangedListener {
                setButtonEnabled()
            }
            sexGroup.setOnCheckedChangeListener { group, checkedId -> setButtonEnabled() }

            nameEditText.addTextChangedListener {
                setButtonEnabled()
            }
            surnameEditText.addTextChangedListener {
                setButtonEnabled()
            }
            nozoologyButton.setOnClickListener { nozoologyButton.text = "first" }
            deleteFile.setOnClickListener {
                it.isActivated = true
                setButtonEnabled()
            }

            submitButton.setOnClickListener {
                hideKeyboard()
            }
        }
    }

    override fun <T> manageSuccess(obj: T?) {

    }

    override fun manageError(bool: Boolean) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(binding){
            setSpanableText(heightText, R.string.height_text)
            setSpanableText(weightText, R.string.weight_text)
            setSpanableText(ageText, R.string.age_text)
            setSpanableText(sexText, R.string.sex_text)
            setSpanableText(nameText, R.string.name_text)
            setSpanableText(surnameText, R.string.surname_text)
            setSpanableText(nozoologyText, if(BuildConfig.IS_REHUB)
                R.string.nozoology_text
            else
                R.string.goal_text)
            setSpanableText(fileText, R.string.file_text)
            fileText.setVisible(BuildConfig.IS_REHUB)
            fileNameText.setVisible(BuildConfig.IS_REHUB)
            deleteFile.setVisible(BuildConfig.IS_REHUB)
            additionalEditText.setVisible(BuildConfig.IS_REHUB)
            additionalText.setVisible(BuildConfig.IS_REHUB)

            submitButton.isEnabled = false
        }
    }

    private fun setButtonEnabled() {
        with(binding){
            submitButton.isEnabled =
                        heightEditText.text?.isNotEmpty() == true &&
                        weightEditText.text?.isNotEmpty() == true &&
                        ageEditText.text?.isNotEmpty() == true &&
                                (maleButton.isChecked || femaleButton.isChecked) &&
                                nameEditText.text?.isNotEmpty() == true &&
                                surnameEditText.text?.isNotEmpty() == true &&
                                nozoologyButton.text != "-" && isFileAdded()
        }
    }

    private fun isFileAdded(): Boolean {
        return if(BuildConfig.IS_REHUB)
            binding.deleteFile.isActivated
        else
            true
    }

    private fun setSpanableText(heightText: TextView, heightText1: Int) {
        val str = getString(heightText1)
        val spannable = SpannableString(str)
        spannable.setSpan(
            ForegroundColorSpan(getColor(requireContext(), R.color.red_color_failure)),
            str.indexOf("*") - 1,str.indexOf("*") + 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        heightText.text = spannable
    }

}
