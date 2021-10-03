package com.example.storagetask

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.storagetask.data.Item
import com.example.storagetask.data.ItemDiffCallback
import com.example.storagetask.databinding.ItemLayoutBinding


class ItemAdapter(val items: List<Item>): ListAdapter<Item, ItemAdapter.ItemViewHolder>(ItemDiffCallback()) {
    lateinit var clickListener: ClickListener

    class ItemViewHolder(val binding: ItemLayoutBinding, val clickListener: ClickListener)  : RecyclerView.ViewHolder(binding.root), View.OnLongClickListener, View.OnClickListener{


        fun bind(item: Item, itemView:View){
            binding.listItem = item
            itemView.setOnClickListener(View.OnClickListener {
                onClick(itemView)
            })
            itemView.setOnLongClickListener {
                onLongClick(itemView)
                return@setOnLongClickListener false
            }
        }

        override fun onLongClick(view: View?): Boolean {
            clickListener.onItemLongClick(bindingAdapterPosition, view);
            return false
        }

        override fun onClick(view: View?) {
            clickListener.onItemClick(bindingAdapterPosition, view)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = ItemLayoutBinding.inflate(inflater, parent, false)
        return ItemViewHolder(listItemBinding,clickListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position], holder.itemView)
    }

    override fun getItemCount(): Int = items.size


    fun getItemFromList(position: Int): Item = items[position]

    fun setOnItemClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    interface ClickListener {
        fun onItemClick(position: Int, v: View?)
        fun onItemLongClick(position: Int, v: View?)
    }

}