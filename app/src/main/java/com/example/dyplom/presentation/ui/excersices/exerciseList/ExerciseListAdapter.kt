package com.example.dyplom.presentation.ui.excersices.exerciseList

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.dyplom.R
import com.example.dyplom.data.exercise.model.ExerciseUi
import com.example.dyplom.databinding.ExerciseItemBinding
import com.example.dyplom.presentation.ui.util.ItemCallbackExercise
import com.example.dyplom.presentation.ui.util.ext.setPhoto

class ExerciseListAdapter(private val clickListener: ExerciseItemClickListener) :
    ListAdapter<ExerciseUi, ExerciseListAdapter.ExerciseViewHolder>(ItemCallbackExercise),
    View.OnClickListener {

    override fun onClick(view: View?) {
        val exercise = view?.tag as ExerciseUi

        when (view.id) {
            R.id.buttonExerciseInfo -> clickListener.onDetail(exercise)
            else -> clickListener.onClick(exercise)
        }
    }

    inner class ExerciseViewHolder(val binding: ExerciseItemBinding) : ViewHolder(binding.root) {
        fun bind(exercise: ExerciseUi) {
            with(binding) {
                cardViewExerciseItem.tag = exercise
                buttonExerciseInfo.tag = exercise
                textViewContactName.text = exercise.name
                textViewContactCareer.text = exercise.category
                if (exercise.isChecked) cardViewExerciseItem.setBackgroundResource(R.drawable.selected_item_border)
                else cardViewExerciseItem.setBackgroundResource(R.drawable.item_border)
                if (exercise.image != null) imageViewContactPhoto.setPhoto(exercise.image!!)
                else imageViewContactPhoto.setPhoto()
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExerciseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ExerciseItemBinding.inflate(inflater, parent, false)

        binding.cardViewExerciseItem.setOnClickListener(this)
        binding.buttonExerciseInfo.setOnClickListener(this)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

