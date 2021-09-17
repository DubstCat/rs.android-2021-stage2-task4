package com.example.storagetask

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.storagetask.data.Item
import com.example.storagetask.databinding.ItemLayoutBinding


class ItemAdapter(val items: List<Item>): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    private var clickListener: ClickListener? = null

    inner class ItemViewHolder(val binding: ItemLayoutBinding)  : RecyclerView.ViewHolder(binding.root), View.OnLongClickListener, View.OnClickListener{


        fun bind(item: Item){
            binding.listItem = item
        }

        override fun onLongClick(p0: View?): Boolean {
            clickListener!!.onItemLongClick(bindingAdapterPosition, p0);
            return false
        }

        override fun onClick(p0: View?) {
            clickListener!!.onItemClick(bindingAdapterPosition, p0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val listItemBinding = ItemLayoutBinding.inflate(inflater, parent, false)
        return ItemViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener(View.OnClickListener {
            holder.onClick(holder.itemView)
        })
        holder.itemView.setOnLongClickListener {
            holder.onLongClick(holder.itemView)
            return@setOnLongClickListener false
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun setOnItemClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }
    fun getItem(position: Int): Item {
        return items[position]
    }

    interface ClickListener {
        fun onItemClick(position: Int, v: View?)
        fun onItemLongClick(position: Int, v: View?)
    }

}