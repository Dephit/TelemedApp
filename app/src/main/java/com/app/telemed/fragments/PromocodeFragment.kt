package com.app.telemed.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.telemed.PromocodeAdapter
import com.app.telemed.viewModels.PromocodeViewModel
import com.app.telemed.R
import com.app.telemed.databinding.PromocodeFragmentBinding
import com.app.telemed.fragments.baseFragments.BaseFragment
import com.app.telemed.showPromoDialogButtonClicked
import com.app.telemed.viewModels.Promocode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PromocodeFragment : BaseFragment() {

    companion object {
        fun newInstance() = PromocodeFragment()
    }

    lateinit var binding: PromocodeFragmentBinding

    lateinit var dialog: AlertDialog

    override val viewModel: PromocodeViewModel by navGraphViewModels(R.id.profile_navegation_xml) {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return PromocodeFragmentBinding.inflate(inflater).run {
            binding = this
            root
        }
    }

    override fun restoreState(savedInstanceState: Bundle?) {
        val adapter = PromocodeAdapter()
        binding.commentRv.layoutManager = LinearLayoutManager(requireContext())
        binding.commentRv.adapter = adapter
        viewModel.restoreState(arguments)
    }

    override fun setListeners() {
        binding.addPromoButton.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        if(!this::dialog.isInitialized)
            dialog = showPromoDialogButtonClicked(binding.root,
                    {promocode ->
                        (binding.commentRv.adapter as PromocodeAdapter).addElement(promocode)
                        dialog.dismiss()
                    },
                    {

                    }
            )
        else dialog.show()
    }

    override fun onResume() {
        if(this::dialog.isInitialized)
            dialog.dismiss()
        super.onResume()
    }

    override fun onDestroy() {
        if(this::dialog.isInitialized)
            dialog.dismiss()
        super.onDestroy()
    }

    override fun manageLoading(b: Boolean) {

    }

    override fun <T> manageSuccess(obj: T?) {
        if(obj != null){
            (binding.commentRv.adapter as PromocodeAdapter).updateList(obj as List<Promocode>)
        }
    }

    override fun manageError(bool: Boolean) {

    }

}