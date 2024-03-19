package com.example.dyplom.presentation.ui.workouts.workoutDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dyplom.data.UIState
import com.example.dyplom.data.exercise.model.ExerciseInWorkout
import com.example.dyplom.data.exercise.model.ExerciseUi
import com.example.dyplom.databinding.FragmentWorkoutDetailBinding
import com.example.dyplom.presentation.ui.workouts.createWorkout.ExerciseSettingsClickListener
import com.example.dyplom.presentation.ui.workouts.createWorkout.WorkoutExercisesSettingsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WorkoutDetailFragment : Fragment() {

    private val binding: FragmentWorkoutDetailBinding by lazy {
        FragmentWorkoutDetailBinding.inflate(
            layoutInflater
        )
    }
    private val safeArgs: WorkoutDetailFragmentArgs by navArgs()
    private val viewModel: WorkoutDetailViewModel by viewModels()
    private lateinit var adapter: WorkoutDetailAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        adapter = WorkoutDetailAdapter(object : ExerciseInWorkoutDetailClickListener {
            override fun onClick(exercise: ExerciseUi) {
                findNavController().navigate(
                    WorkoutDetailFragmentDirections.actionWorkoutDetailFragmentToExerciseDetailFragment(
                        exercise
                    )
                )
            }


        })
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        setObservers()
        binding.editTextWorkoutName.text = safeArgs.workoutName
        viewModel.setExercises(safeArgs.workoutid)
        return binding.root
    }

    private fun setObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.exercises.collect {
                    when (it) {
                        is UIState.Success -> {
                            adapter.submitList(it.data)
                        }

                        is UIState.Error -> {
                            Toast.makeText(
                                requireContext(),
                                it.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is UIState.Loading -> {
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}