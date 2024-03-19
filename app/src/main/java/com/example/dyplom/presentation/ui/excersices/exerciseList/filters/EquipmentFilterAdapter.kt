package com.example.dyplom.presentation.ui.excersices.exerciseList.filters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dyplom.R
import com.example.dyplom.data.equipment.EquipmentItem
import com.example.dyplom.databinding.EquipmentItemBinding
import com.example.dyplom.presentation.ui.excersices.exerciseList.filters.util.ItemCallbackEquipment
import com.example.dyplom.presentation.ui.util.ext.setPhotoById

class EquipmentFilterAdapter(private val clickListener: FilterItemClickListener) :
    ListAdapter<EquipmentItem, EquipmentFilterAdapter.EquipmentViewHolder>(ItemCallbackEquipment),
    View.OnClickListener {

    override fun onClick(view: View?) {
        val equipment = view?.tag as EquipmentItem
        when (view.id) {
            R.id.cardViewItemWorkoutItem-> clickListener.onClick(equipment.name)
        }

    }

    inner class EquipmentViewHolder(val binding: EquipmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(equipment: EquipmentItem) {
            with(binding) {
                cardViewItemWorkoutItem.tag = equipment
                binding.textViewName.text = equipment.name
                binding.imageViewExerciseImage.setPhotoById(equipment.image)
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
