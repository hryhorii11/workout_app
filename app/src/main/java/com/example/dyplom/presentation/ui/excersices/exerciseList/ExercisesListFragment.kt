package com.example.dyplom.presentation.ui.excersices.exerciseList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dyplom.R
import com.example.dyplom.data.exerciseCategories.CategoryList
import com.example.dyplom.data.equipment.EquipmentList
import com.example.dyplom.data.exercise.model.ExerciseUi
import com.example.dyplom.databinding.FragmentExercisesBinding
import com.example.dyplom.presentation.ui.excersices.exerciseList.filters.CategoryFilterAdapter
import com.example.dyplom.presentation.ui.excersices.exerciseList.filters.FilterItemClickListener
import com.example.dyplom.presentation.ui.excersices.exerciseList.filters.EquipmentFilterAdapter
import com.example.dyplom.presentation.ui.workouts.createWorkout.CreateWorkoutViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExercisesListFragment : Fragment() {
    private val bindingExercise: FragmentExercisesBinding by lazy {
        FragmentExercisesBinding.inflate(
            layoutInflater
        )
    }
    private val viewModelWorkouts: CreateWorkoutViewModel by activityViewModels()
    private val viewModel: ExerciseListViewModel by viewModels()
    private lateinit var adapter: ExerciseListAdapter
    private val safeArgs: ExercisesListFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setListeners()
        adapter = ExerciseListAdapter(object : ExerciseItemClickListener {
            override fun onDetail(exercise: ExerciseUi) {
                findNavController().navigate(
                    ExercisesListFragmentDirections.actionExercisesListFragmentToExerciseDetailFragment(
                        exercise
                    )
                )
            }
            override fun onClick(exercise: ExerciseUi) {
                if (safeArgs.isReplacement) {
                    viewModelWorkouts.replaceExercise(
                        safeArgs.replacementExercisePosition,
                        exercise
                    )
                    findNavController().popBackStack()
                }
                else
                    viewModel.addExercise(exercise)
            }
        })
        bindingExercise.recyclerExercise.layoutManager = LinearLayoutManager(requireContext())
        bindingExercise.recyclerExercise.adapter = adapter
        setObservers()
        viewModel.setExercises()
        return bindingExercise.root
    }

    private fun setListeners() {
        with(bindingExercise) {
            searchViewExercises.setOnQueryTextFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    hideKeyboard()
                }
            }
            layout.onTapEvent = { searchViewExercises.clearFocus() }
            buttonEquipment.setOnClickListener { showEquipmentDialog() }
            buttonCategory.setOnClickListener { showCategoriesDialog() }
            buttonAdd.setOnClickListener {
                viewModelWorkouts.addExercise(viewModel.getExerciseToAdd())
                findNavController().popBackStack()
            }
            searchViewExercises.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?) = true
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        viewModel.search(newText)
                    }
                    return true
                }
            })
        }
    }

    private fun showEquipmentDialog() {
        val dialogView = layoutInflater.inflate(R.layout.bottom_shhet_equipment, null)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(dialogView)
        val adapter = EquipmentFilterAdapter(object : FilterItemClickListener {
            override fun onClick(itemName: String) {
                viewModel.filterByEquipment(itemName)
                bindingExercise.buttonEquipment.text = itemName
                dialog.dismiss()
            }
        })
        adapter.submitList(EquipmentList.getEquipmentList())
        val recycler = dialogView.findViewById<RecyclerView>(R.id.recycler)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())
        dialog.show()
    }

    private fun showCategoriesDialog() {
        val dialogView = layoutInflater.inflate(R.layout.bottom_shhet_equipment, null)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(dialogView)
        val adapter = CategoryFilterAdapter(object : FilterItemClickListener {
            override fun onClick(itemName: String) {
                viewModel.filterByCategory(itemName)
                bindingExercise.buttonCategory.text = itemName
                dialog.dismiss()
            }
        })
        adapter.submitList(CategoryList.getCategoryList())
        val recycler = dialogView.findViewById<RecyclerView>(R.id.recycler)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())
        dialog.show()
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            bindingExercise.searchViewExercises.windowToken,
            0
        )
    }

    private fun setObservers() {
        viewModel.exercise.observe(viewLifecycleOwner)
        {
            adapter.submitList(it)
        }
    }
}