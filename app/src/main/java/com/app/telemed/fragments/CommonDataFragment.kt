package com.app.telemed.fragments

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import com.app.telemed.*
import com.app.telemed.databinding.CommonDataFragmentBinding
import com.app.telemed.databinding.GoalViewBinding
import com.app.telemed.fragments.baseFragments.BaseFragment
import com.app.telemed.models.GoalData
import com.app.telemed.models.GoalResponse
import com.app.telemed.models.Profile
import com.app.telemed.models.UpdateProfileResponse
import com.app.telemed.viewModels.CommonDataViewModel
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

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
            nozoologyButton.setOnClickListener {
                goalContainer.visibility = if(goalContainer.visibility == View.GONE) View.VISIBLE else View.GONE
            }
            deleteFile.setOnClickListener {
                it.isActivated = true
                setButtonEnabled()
            }

            submitButton.setOnClickListener {
                viewModel.updateProfile(
                    name = nameEditText.text.toString(),
                    surname = surnameEditText.text.toString(),
                    height = heightEditText.text.toString().toInt(),
                    weight = weightEditText.text.toString().toInt(),
                    age = ageEditText.text.toString().toInt(),
                    gender = if(maleButton.isChecked) "m" else "f",
                    goalId = goalContainer.id
                )
                hideKeyboard()
            }
        }
    }

    override fun manageLoading(b: Boolean) {
        if(b) {
            binding.submitButton.showProgress { progressColor = getColor(requireContext(), R.color.white)}
        }else {
            binding.submitButton.hideProgress(getString(R.string.submit_text))
        }
    }

    override fun <T> manageSuccess(obj: T?) {
        if(obj is Profile){
            updateProfile(obj)
        }else if(obj is UpdateProfileResponse){
            showToast(obj.toString())
        }
    }

    @ExperimentalCoroutinesApi
    private fun updateProfile(profile: Profile) {
        with(binding){
            heightEditText.setText(profile.data.height)
            weightEditText.setText(profile.data.weight)
            ageEditText.setText(profile.data.age)
            if(profile.data.gender == "m"){
                maleButton.isChecked = true
            }else{
                femaleButton.isChecked = true
            }
            nameEditText.setText(profile.data.name)
            surnameEditText.setText(profile.data.surname)
            lifecycleScope.launch(IO) {
                nozoologyButton.text = viewModel.getGoal(profile.data.goal_id)
                val inflater = LayoutInflater.from(requireContext())

                viewModel.getGoal()?.data?.forEach {
                    val goalView = GoalViewBinding.inflate(inflater, null, false)
                    goalView.goalText.text = it.name
                    goalView.goalText.setOnClickListener {_->
                        binding.nozoologyButton.text = it.name
                        binding.goalContainer.id = it.id
                        binding.goalContainer.visibility = View.GONE
                    }
                    binding.goalList.addView(goalView.root)
                }
            }
        }
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
                                nozoologyButton.text != "-" //&& isFileAdded()
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
