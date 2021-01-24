package com.app.telemed

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.app.telemed.databinding.CommentViewBinding
import com.app.telemed.databinding.PromocodeViewBinding
import com.app.telemed.viewModels.Comment
import com.app.telemed.viewModels.Promocode

class PromocodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), IViewHolder {

    override fun <T> bindTo(concert: T?) {
        (concert as Promocode).let {
            val v = PromocodeViewBinding.bind(itemView)
            v.promocodeDate.text = it.date
            v.promocode.text = it.name
            v.root.isActivated = it.isPassed == true
        }
    }

}
