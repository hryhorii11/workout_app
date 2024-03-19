package com.example.dyplom.presentation.ui.workouts.workoutList

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dyplom.R
import com.example.dyplom.data.UIState
import com.example.dyplom.data.workout.model.Workout
import com.example.dyplom.databinding.BottomSheetWorkoutOptionsBinding
import com.example.dyplom.databinding.BottomShhetExerciseOptionsBinding
import com.example.dyplom.databinding.FragmentWorkoutListBinding
import com.example.dyplom.presentation.ui.mainViewPager.MainViewPagerFragmentDirections
import com.example.dyplom.presentation.ui.util.ext.hide
import com.example.dyplom.presentation.ui.util.ext.show
import com.example.dyplom.presentation.ui.workouts.createWorkout.CreateWorkoutFragmentDirections
import com.example.dyplom.presentation.ui.workouts.exploreWorkout.ExploreWorkoutFragmentDirections
import com.example.dyplom.presentation.ui.workouts.exploreWorkout.WorkoutItemClickListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WorkoutListFragment : Fragment() {

    private val binding: FragmentWorkoutListBinding by lazy {
        FragmentWorkoutListBinding.inflate(
            layoutInflater
        )
    }
    private lateinit var adapter: UserWorkoutListAdapter
    private val viewModel: UserWorkoutListViewModel by viewModels()
    private var isOptionsDialogOpen = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setListeners()
        adapter = UserWorkoutListAdapter(object : UserWorkoutItemClickListener {
            override fun onClick(workout: Workout) {
                findNavController().navigate(
                    MainViewPagerFragmentDirections.actionViewPagerFragmentToWorkoutDetailFragment(
                        workout.id,
                        workout.name
                    )
                )
            }

            override fun onOptionsClick(workoutId: String) {
                showOptionDialog(workoutId)
            }
        })


        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        setObservers()
        viewModel.setWorkouts()
        return binding.root
    }

    private fun setListeners() {
        with(binding) {
            buttonCreateWorkout.setOnClickListener {
                findNavController().navigate(
                    MainViewPagerFragmentDirections.actionViewPagerFragmentToCreateWorkoutFragment()
                )
            }
            buttonExploreWorkout.setOnClickListener {
                findNavController().navigate(
                    MainViewPagerFragmentDirections.actionViewPagerFragmentToExploreWorkoutFragment()
                )
            }
        }
    }

    private fun setObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.workoutList.collect {
                    when (it) {
                        is UIState.Success -> {
                            binding.progressBar.hide()
                            adapter.submitList(it.data)
                        }
                        is UIState.Error -> {
                            binding.progressBar.hide()
                            Toast.makeText(
                                requireContext(),
                                it.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is UIState.Loading -> {
                             binding.progressBar.show()
                        }
                        else -> {}
                    }
                }
            }
        }
    }
    private fun showOptionDialog(workoutId: String) {
        if (!isOptionsDialogOpen) {
            isOptionsDialogOpen = true
            val optionsBinding = BottomSheetWorkoutOptionsBinding.inflate(layoutInflater)
            val bottomSheetDialog =
                BottomSheetDialog(requireActivity(), R.style.BottomSheetDialogTheme)
            bottomSheetDialog.setContentView(optionsBinding.root)
            bottomSheetDialog.show()
            bottomSheetDialog.setOnDismissListener {
                isOptionsDialogOpen = false
            }
            with(optionsBinding)
            {
                buttonDelete.setOnClickListener {
                    viewModel.deleteWorkout(workoutId)
                    bottomSheetDialog.dismiss()
                }
                buttonCancel.setOnClickListener {
                    bottomSheetDialog.dismiss()
                }
            }
        }
    }
}