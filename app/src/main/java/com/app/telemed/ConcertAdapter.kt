package com.app.telemed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.telemed.databinding.CommentViewBinding
import com.app.telemed.viewModels.Comment

class ConcertAdapter() : RecyclerView.Adapter<CommentViewHolder>() {

    var list: List<Comment> = listOf()

    fun updateList(_list: List<Comment>){
        list = _list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bindTo(list[position])
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(CommentViewBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false).root)
    }

    override fun getItemCount(): Int = list.size



}