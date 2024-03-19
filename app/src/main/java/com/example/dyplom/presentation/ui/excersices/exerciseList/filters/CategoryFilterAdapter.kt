package com.example.dyplom.presentation.ui.excersices.exerciseList.filters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dyplom.R
import com.example.dyplom.data.exerciseCategories.CategoryItem
import com.example.dyplom.databinding.EquipmentItemBinding
import com.example.dyplom.presentation.ui.excersices.exerciseList.filters.util.ItemCallbackCategory
import com.example.dyplom.presentation.ui.util.ext.setPhotoById

class CategoryFilterAdapter(private val clickListener: FilterItemClickListener) :
    ListAdapter<CategoryItem, CategoryFilterAdapter.EquipmentViewHolder>(ItemCallbackCategory),
    View.OnClickListener {

    override fun onClick(view: View?) {
        val category = view?.tag as CategoryItem
        when (view.id) {
            R.id.cardViewItemWorkoutItem -> clickListener.onClick(category.name)
        }

    }

    inner class EquipmentViewHolder(val binding: EquipmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(categoryItem: CategoryItem) {
            with(binding) {
                cardViewItemWorkoutItem.tag = categoryItem
                binding.textViewName.text = categoryItem.name
                binding.imageViewExerciseImage.setPhotoById(categoryItem.image)
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EquipmentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = EquipmentItemBinding.inflate(inflater, parent, false)

        binding.cardViewItemWorkoutItem.setOnClickListener(this)

        return EquipmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EquipmentViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}
