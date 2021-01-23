package com.app.telemed

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.telemed.databinding.PromocodeFragmentBinding
import com.app.telemed.fragments.baseFragments.BaseFragment
import com.app.telemed.viewModels.Comment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PromocodeFragment : BaseFragment() {

    companion object {
        fun newInstance() = PromocodeFragment()
    }

    lateinit var binding: PromocodeFragmentBinding

    override val viewModel: PromocodeViewModel  by navGraphViewModels(R.id.profile_navegation_xml) {
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
        val adapter = ConcertAdapter()
        binding.commentRv.layoutManager = LinearLayoutManager(requireContext())
        binding.commentRv.adapter = adapter
        viewModel.restoreState(arguments)
    }

    override fun setListeners() {

    }

    override fun manageLoading(b: Boolean) {

    }

    override fun <T> manageSuccess(obj: T?) {
        if(obj != null){
            (binding.commentRv.adapter as ConcertAdapter).updateList(obj as List<Comment>)
        }
    }

    override fun manageError(bool: Boolean) {

    }

}