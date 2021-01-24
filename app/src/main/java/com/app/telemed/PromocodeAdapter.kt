package com.app.telemed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.telemed.databinding.PromocodeViewBinding
import com.app.telemed.viewModels.Promocode
import java.util.*

class PromocodeAdapter() : RecyclerView.Adapter<PromocodeViewHolder>() {

    var list: MutableList<Promocode> = mutableListOf()

    fun updateList(_list: List<Promocode>){
        list = _list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PromocodeViewHolder, position: Int) {
        holder.bindTo(list[position])
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PromocodeViewHolder {
        return PromocodeViewHolder(PromocodeViewBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false).root)
    }

    override fun getItemCount(): Int = list.size

    fun addElement(promocode: Promocode) {
        list.add(promocode)
        notifyItemChanged(list.size - 1)
    }




}