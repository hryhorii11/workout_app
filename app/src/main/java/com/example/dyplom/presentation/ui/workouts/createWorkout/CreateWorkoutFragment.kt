package com.example.dyplom.presentation.ui.workouts.createWorkout

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dyplom.R
import com.example.dyplom.data.exercise.model.ExerciseInWorkout
import com.example.dyplom.databinding.BottomSheetExerciseParametresBinding
import com.example.dyplom.databinding.BottomShhetExerciseOptionsBinding
import com.example.dyplom.databinding.FragmentCreateWorkoutBinding
import com.example.dyplom.presentation.ui.timer.util.MyEditTextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateWorkoutFragment : Fragment() {

    private val binding: FragmentCreateWorkoutBinding by lazy {
        FragmentCreateWorkoutBinding.inflate(
            layoutInflater
        )
    }
    private val viewModel: CreateWorkoutViewModel by activityViewModels()
    private lateinit var adapter: WorkoutExercisesSettingsAdapter

    private var isOptionsDialogOpen = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        adapter = WorkoutExercisesSettingsAdapter(
            object : ExerciseSettingsClickListener {
                override fun saveComment(comment: String, position: Int) {
                    viewModel.saveComment(comment, position)
                }

                override fun onOptionsClick(exercisePosition: Int) {
                    showOptionDialog(exercisePosition)
                }

                override fun onParametersClick(
                    position: Int,
                    exerciseInWorkout: ExerciseInWorkout
                ) {
                    showExerciseParametersDialog(position, exerciseInWorkout)
                }
            }
        )
        setListeners()
        setObservers()
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    private fun setObservers() {
        viewModel.exercises.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            if (it.isEmpty()) binding.textViewAddExerciseHint.visibility = View.VISIBLE
            else binding.textViewAddExerciseHint.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        viewModel.clearList()
        super.onDestroy()
    }

    private fun setListeners() {
        with(binding) {

            buttonAddExercise.setOnClickListener {
                findNavController().navigate(
                    CreateWorkoutFragmentDirections.actionCreateWorkoutFragmentToExercisesListFragment()
                )
            }
            buttonCancelWorkoutCreate.setOnClickListener {
                findNavController().popBackStack()
            }
            buttonSaveCreatedWorkout.setOnClickListener {
                if (binding.editTextWorkoutName.text.isBlank() || viewModel.exercises.value?.isEmpty() != false)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.workout_create_error),
                        Toast.LENGTH_LONG
                    ).show()
                else {
                    viewModel.saveWorkout(binding.editTextWorkoutName.text.toString())
                    findNavController().popBackStack()
                }
            }

        }
    }

    private fun showOptionDialog(exercisePosition: Int) {
        if (!isOptionsDialogOpen) {
            isOptionsDialogOpen = true
            val optionsBinding = BottomShhetExerciseOptionsBinding.inflate(layoutInflater)
            val bottomSheetDialog =
                BottomSheetDialog(requireActivity(), R.style.BottomSheetDialogTheme)
            bottomSheetDialog.setContentView(optionsBinding.root)
            bottomSheetDialog.show()
            bottomSheetDialog.setOnDismissListener {
                isOptionsDialogOpen = false
            }
            with(optionsBinding)
            {
                buttonReplace.setOnClickListener {
                    findNavController().navigate(
                        CreateWorkoutFragmentDirections.actionCreateWorkoutFragmentToExercisesListFragment(
                            true, exercisePosition
                        )
                    )
                    bottomSheetDialog.dismiss()
                }
                buttonDelete.setOnClickListener {
                    viewModel.deleteExercise(exercisePosition)
                    bottomSheetDialog.dismiss()
                }
                buttonCancel.setOnClickListener {
                    bottomSheetDialog.dismiss()
                }
            }
        }
    }

    private fun showExerciseParametersDialog(position: Int, exerciseInWorkout: ExerciseInWorkout) {
        val parametersBinding = BottomSheetExerciseParametresBinding.inflate(layoutInflater)
        val bottomSheetDialog =
            BottomSheetDialog(requireActivity(), R.style.BottomSheetDialogTheme)
        bottomSheetDialog.setContentView(parametersBinding.root)
        bottomSheetDialog.show()
        with(parametersBinding) {
            editTextRestTime.setText(exerciseInWorkout.restTime)
            editTextSets.setText(
                if (exerciseInWorkout.numberOfSets != null) exerciseInWorkout.numberOfSets.toString()
                else "0"
            )
            editTextKg.setText(
                if (exerciseInWorkout.kg != null) exerciseInWorkout.kg.toString()
                else "0"
            )
            editTextRestTime.setOnClickListener {
                showNumberPicker(editTextRestTime)
            }
            cancelButton.setOnClickListener { bottomSheetDialog.dismiss() }
            saveButton.setOnClickListener {
                viewModel.addParameters(
                    position,
                    exerciseInWorkout.copy(
                        restTime = editTextRestTime.text.toString(),
                        numberOfSets = editTextSets.text.toString().toIntOrNull(),
                        kg = editTextKg.text.toString().toIntOrNull()
                    )
                )
                bottomSheetDialog.dismiss()
            }
        }


    }

    private fun showNumberPicker(edittext: MyEditTextView) {
        val builder = AlertDialog.Builder(this.context)
        val view = this.layoutInflater.inflate(R.layout.dialog_picker, null)
        builder.setView(view)
        val pickerMinute = view.findViewById<View>(R.id.pickerMinute) as NumberPicker
        val pickerSecond = view.findViewById<View>(R.id.pickerSecond) as NumberPicker

        pickerMinute.minValue = 0
        pickerMinute.maxValue = 59
        pickerMinute.value = Integer.parseInt(edittext.text.toString().substring(0, 2))
        pickerSecond.minValue = 0
        pickerSecond.maxValue = 59
        pickerSecond.value = Integer.parseInt(edittext.text.toString().substring(3))

        builder.setPositiveButton(
            "ok"
        ) { _, _ ->
            setText(pickerMinute.value.toString(), pickerSecond.value.toString(), edittext)
        }

        builder.create().show()
    }

    @SuppressLint("SetTextI18n")
    private fun setText(
        minutes: String,
        seconds: String,
        edittext: MyEditTextView,
    ) {
        var min = minutes
        var sec = seconds
        if (Integer.parseInt(minutes) < 10)
            min = "0$minutes"
        if (Integer.parseInt(sec) < 10)
            sec = "0$sec"
        edittext.setText("$min:$sec")
    }
}
