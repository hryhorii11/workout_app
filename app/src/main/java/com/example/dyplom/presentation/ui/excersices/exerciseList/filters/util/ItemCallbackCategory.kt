package com.example.dyplom.presentation.ui.excersices.exerciseList.filters.util

import androidx.recyclerview.widget.DiffUtil
import com.example.dyplom.data.equipment.EquipmentItem
import com.example.dyplom.data.exerciseCategories.CategoryItem

object ItemCallbackCategory: DiffUtil.ItemCallback<CategoryItem>()  {

    override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
        return oldItem == newItem
    }
}