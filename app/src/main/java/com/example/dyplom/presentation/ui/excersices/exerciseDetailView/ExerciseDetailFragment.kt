package com.example.dyplom.presentation.ui.excersices.exerciseDetailView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.dyplom.R
import com.example.dyplom.databinding.FragmentExerciseDetailBinding
import com.example.dyplom.presentation.ui.util.ext.setPhoto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseDetailFragment : Fragment() {
    private val binding: FragmentExerciseDetailBinding by lazy {
        FragmentExerciseDetailBinding.inflate(layoutInflater)
    }
    private val args: ExerciseDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setExerciseData()
        return binding.root
    }

    private fun setExerciseData() {
        with(binding)
        {
            imageViewExercise.setImageResource(R.drawable.baseline_sports_gymnastics_24)
            textViewExerciseName.text = args.exercise.name
            args.exercise.image?.let { imageViewExercise.setPhoto(it) }
            textViewDescription.text = args.exercise.description
            textViewEquipment.text = buildString {
                append(textViewEquipment.text.toString())
                append((args.exercise.equipment ?: " "))
            }
        }
    }
}
