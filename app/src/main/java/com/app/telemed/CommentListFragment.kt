package com.app.telemed

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.navGraphViewModels
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.telemed.databinding.CommentListFragmentBinding
import com.app.telemed.fragments.baseFragments.BaseFragment
import com.app.telemed.viewModels.Comment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentListFragment : BaseFragment() {

    override val viewModel: CommentListViewModel by navGraphViewModels(R.id.menu_navigation_xml) {
        defaultViewModelProviderFactory
    }

    override fun observe() {

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
        viewModel.restoreState(arguments)
    }

    override fun setListeners() {

    }

    override fun manageLoading(b: Boolean) {

    }

    override fun <T> manageSuccess(obj: T?) {
        if(obj != null){
            val list = obj as List<Comment>
            val adapter = ConcertAdapter()
            adapter.list = list
            binding.commentRv.adapter = adapter
            binding.commentRv.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun manageError(bool: Boolean) {

    }

}