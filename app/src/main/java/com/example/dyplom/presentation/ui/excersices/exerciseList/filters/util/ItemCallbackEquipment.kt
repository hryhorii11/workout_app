package com.example.dyplom.presentation.ui.excersices.exerciseList.filters.util

import androidx.recyclerview.widget.DiffUtil
import com.example.dyplom.data.equipment.EquipmentItem
import com.example.dyplom.data.exercise.model.ExerciseUi

  object ItemCallbackEquipment: DiffUtil.ItemCallback<EquipmentItem>()  {

      override fun areItemsTheSame(oldItem: EquipmentItem, newItem: EquipmentItem): Boolean {
         return oldItem.name == newItem.name
      }

      override fun areContentsTheSame(oldItem: EquipmentItem, newItem: EquipmentItem): Boolean {
          return oldItem == newItem
      }
  }