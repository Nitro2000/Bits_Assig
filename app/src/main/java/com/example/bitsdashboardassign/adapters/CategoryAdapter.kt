package com.example.bitsdashboardassign.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bitsdashboardassign.R
import com.example.bitsdashboardassign.databinding.CategoryItemBinding
import com.example.bitsdashboardassign.models.CategoryItem


class CategoryAdapter(
    val context: Context,
    val navigateCallback: (catId: Int) -> Any,
    val callback: (catId: Int, item: CategoryItem, action: Int) -> Any
) :
    ListAdapter<CategoryItem, CategoryAdapter.ViewHolder>(DiffUtilCat) {

    class ViewHolder(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(catItem: CategoryItem) {
            binding.catNameTxt.text = catItem.name
            binding.catDesTxt.text = catItem.description
            switchWork(catItem.enable)
        }

        fun switchWork(isCheck: Boolean) {
            if (isCheck) {
                binding.shade.visibility = View.GONE
                binding.mainLayout.isEnabled = true
                binding.switchItem.isChecked = true
            } else {
                binding.apply {
                    shade.visibility = View.VISIBLE
                    mainLayout.isEnabled = false
                    switchItem.isChecked = false
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem: CategoryItem = getItem(position)
        holder.bind(listItem)
        holder.binding.switchItem.setOnCheckedChangeListener {_, isChecked ->
            listItem.enable = isChecked
            holder.switchWork(isChecked)
        }

        holder.binding.mainLayout.setOnClickListener {
            navigateCallback(getItem(position).id)
        }

        holder.binding.catOptionTxt.setOnClickListener {
            val popup = PopupMenu(context, holder.binding.catOptionTxt)
            popup.inflate(R.menu.category_menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.edit -> {                    //handle menu1 click
                        callback(getItem(position).id, listItem , 1)
                        true
                    }
                    R.id.delete -> {                        //handle menu2 click
                        callback(getItem(position).id, listItem,2)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

    companion object DiffUtilCat : DiffUtil.ItemCallback<CategoryItem>() {
        override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem == newItem
        }
    }
}