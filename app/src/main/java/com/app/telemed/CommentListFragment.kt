package com.app.telemed

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.telemed.databinding.CommentListFragmentBinding
import com.app.telemed.fragments.baseFragments.BaseFragment
import com.app.telemed.viewModels.Comment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommentListFragment : BaseFragment() {

    override val viewModel: CommentListViewModel by navGraphViewModels(R.id.profile_navegation_xml) {
        defaultViewModelProviderFactory
    }

    lateinit var binding: CommentListFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return CommentListFragmentBinding.inflate(inflater).run{
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