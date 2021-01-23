package com.app.telemed

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.app.telemed.databinding.CommentViewBinding
import com.app.telemed.viewModels.Comment
import com.app.telemed.viewModels.Promocode

class PromocodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindTo(concert: Promocode?) {
        val v = CommentViewBinding.bind(itemView)
        v.commentText.text = concert?.commentText
        v.groupText.text = concert?.groupText
        v.dateText.text = concert?.dataText
        v.root.isActivated = concert?.isSeen == true
    }

}
