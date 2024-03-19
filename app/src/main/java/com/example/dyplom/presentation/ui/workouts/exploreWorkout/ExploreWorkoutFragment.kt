package com.example.dyplom.presentation.ui.workouts.exploreWorkout

import android.os.Bundle
import android.util.Log
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dyplom.data.UIState
import com.example.dyplom.data.workout.model.Workout
import com.example.dyplom.databinding.FragmentExploreWorkoutBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ExploreWorkoutFragment : Fragment() {

    private val binding: FragmentExploreWorkoutBinding by lazy {
        FragmentExploreWorkoutBinding.inflate(
            layoutInflater
        )
    }
    private val viewModel: ExploreWorkoutViewModel by viewModels()
    private lateinit var adapter: ExploreWorkoutAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        adapter = ExploreWorkoutAdapter(object : WorkoutItemClickListener {
            override fun onClick(workout: Workout) {
                findNavController().navigate(
                    ExploreWorkoutFragmentDirections.actionExploreWorkoutFragmentToWorkoutDetailFragment(
                        workout.id,
                        workout.name
                    )
                )
            }

            override fun saveWorkout(workout: Workout) {
                viewModel.saveWorkout(workout)
            }

        })

        binding.recyclerExploreWorkout.adapter = adapter
        binding.recyclerExploreWorkout.layoutManager = LinearLayoutManager(requireContext())
        setObservers()
        viewModel.setWorkoutList()
        return binding.root
    }

    private fun setObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.workoutListState.collect {
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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.states.collect {
                    Log.i("hui", "collect")
                    adapter.setStates(it)
                }
            }
        }
//        viewModel._states.observe(viewLifecycleOwner){
//            Log.i("hui","collect")
//            Log.i("hui",it.toString())
//            if(it!=null)
//            adapter.setStates(it)
//        }
    }

}