package com.app.telemed.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.app.telemed.BuildConfig
import com.app.telemed.viewModels.ProfileViewModel
import com.app.telemed.R
import com.app.telemed.databinding.CustomTabBinding
import com.app.telemed.databinding.ProfileFragmentBinding
import com.app.telemed.fragments.baseFragments.BaseFragment
import com.app.telemed.models.LoginResponse
import com.app.telemed.showAlertDialogButtonClicked
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    private lateinit var dialog: AlertDialog
    override val viewModel: ProfileViewModel by navGraphViewModels(R.id.menu_navigation_xml) {
        defaultViewModelProviderFactory
    }

    lateinit var binding: ProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ProfileFragmentBinding.inflate(inflater).run {
            binding = this
            root
        }
    }

    override fun restoreState(savedInstanceState: Bundle?) {
        setTabs()
    }

    private fun setTabs() {
        val vv = CustomTabBinding.inflate(LayoutInflater.from(requireContext()))
        vv.customTabTextView.text = getString(R.string.leave_text)
        if(BuildConfig.IS_REHUB){
            binding.tabLayout.getTabAt(0)?.setText(R.string.com_doctor)
            binding.tabLayout.getTabAt(1)?.setText(R.string.com_instructor)
            binding.tabLayout.getTabAt(3)?.let { binding.tabLayout.removeTab(it) }
        }else {
            binding.tabLayout.getTabAt(0)?.setText(R.string.com_coach)
            binding.tabLayout.getTabAt(1)?.setText(R.string.com_main_coach)
        }

        binding.tabLayout.getTabAt(binding.tabLayout.tabCount - 1)?.customView = vv.root
        binding.tabLayout.isEnabled = false
    }

    override fun setListeners() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> navigateToDoctor()
                    1 -> navigateToInstructor()
                    2 -> navigateToCommonData()
                    3 -> navigateToPromocodes()
                    binding.tabLayout.tabCount - 1 -> navigateToExitDialog()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun navigateToPromocodes() {
        if(BuildConfig.IS_REHUB)
            navigateToExitDialog()
        else {
            binding.fragment.findNavController().navigate(R.id.action_global_promocodeFragment, viewModel.getPromocodes())
        }
    }

    private fun navigateToExitDialog() {
        showDialog()
        binding.tabLayout.selectTab(binding.tabLayout.getTabAt(2))
    }

    private fun navigateToCommonData() {
        binding.fragment.findNavController().navigate(R.id.action_global_commonDataFragment2, viewModel.getInstructorComments())
    }

    private fun navigateToInstructor() {
        binding.fragment.findNavController().navigate(R.id.action_global_commentListFragment, viewModel.getInstructorComments())
    }

    private fun navigateToDoctor() {
        binding.fragment.findNavController().navigate(R.id.action_global_commentListFragment, viewModel.getDoctorComments())
    }

    private fun showDialog() {
        if(!this::dialog.isInitialized)
            dialog = showAlertDialogButtonClicked(binding.root, R.string.are_you_sure_you_wanna_leave_account,{
                viewModel.logOut()
            }, {})
        else
            dialog.show()
    }

    override fun manageLoading(b: Boolean) {

    }

    override fun <T> manageSuccess(obj: T?) {
        if(obj is LoginResponse?){
            findNavController().popBackStack(R.id.menu_navigation_xml, true)
            findNavController().navigate(R.id.menu_navigation_xml, bundleOf("back" to "auth"))
        }else {
            binding.tabLayout.isEnabled = true
            binding.tabLayout.selectTab(binding.tabLayout.getTabAt(0))
            navigateToDoctor()
        }
    }

    override fun manageError(bool: Boolean) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDestroy() {
        if(this::dialog.isInitialized)
            dialog.dismiss()
        super.onDestroy()
    }

    override fun onPause() {
        if(this::dialog.isInitialized)
            dialog.dismiss()
        super.onPause()
    }

}