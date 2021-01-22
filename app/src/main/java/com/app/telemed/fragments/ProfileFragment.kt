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
import com.app.telemed.viewModels.ProfileViewModel
import com.app.telemed.R
import com.app.telemed.databinding.CustomTabBinding
import com.app.telemed.databinding.ProfileFragmentBinding
import com.app.telemed.fragments.baseFragments.BaseFragment
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

    override fun observe() {

    }

    override fun restoreState(savedInstanceState: Bundle?) {
        setTabs()
    }

    private fun setTabs() {
        val vv = CustomTabBinding.inflate(LayoutInflater.from(requireContext()))
        vv.customTabTextView.text = getString(R.string.leave_text)
        binding.tabLayout.getTabAt(3)?.customView = vv.root
    }

    override fun setListeners() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    3 -> {
                        showDialog()
                        binding.tabLayout.selectTab(binding.tabLayout.getTabAt(2))
                    }
                    1 -> {
                        //binding.fragment.findNavController().navigate(R.id.action_global_commentListFragment, viewModel.getDoctorComments())
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun showDialog() {
        if(!this::dialog.isInitialized)
            dialog = showAlertDialogButtonClicked(binding.root, R.string.are_you_sure_you_wanna_leave_account,{
                findNavController().popBackStack(R.id.menu_navigation_xml, true)
                findNavController().navigate(R.id.menu_navigation_xml, bundleOf("back" to "auth"))
            }, {})
        else
            dialog.show()
    }

    override fun manageLoading(b: Boolean) {

    }

    override fun <T> manageSuccess(obj: T?) {

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