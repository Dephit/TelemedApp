package com.app.telemed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.telemed.databinding.CommentViewBinding
import com.app.telemed.interfaces.Repository
import com.app.telemed.viewModels.Comment
import com.app.telemed.viewModels.baseViewModels.BaseViewModel
import com.app.telemed.viewModels.baseViewModels.ModelState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CommentListViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel(repository, savedStateHandle){

    var comments: List<Comment>? = null

    fun restoreState(arguments: Bundle?) {
        comments = arguments?.getParcelableArrayList("comments")
        modelState.postValue(ModelState.Success(comments))

    }

}

class ConcertAdapter() : RecyclerView.Adapter<CommentViewHolder>() {

    var list: List<Comment> = listOf()

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val concert: Comment = list[position]
        holder.bindTo(concert)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(CommentViewBinding.inflate(LayoutInflater.from(viewGroup.context)).root)
    }

    override fun getItemCount(): Int = list.size

}
