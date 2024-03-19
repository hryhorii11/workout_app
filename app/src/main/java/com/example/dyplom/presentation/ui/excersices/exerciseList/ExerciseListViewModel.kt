package com.example.dyplom.presentation.ui.excersices.exerciseList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dyplom.data.api.ExerciseService
import com.example.dyplom.data.exercise.model.ExerciseFilter
import com.example.dyplom.data.exercise.model.ExerciseUi
import com.example.dyplom.data.exercise.repository.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    private val retrofit: ExerciseService,
    private val repository: ExerciseRepository
) : ViewModel() {
    private val _exercises = MutableLiveData<List<ExerciseUi>>()
    val exercise = _exercises

    private var originalList: MutableList<ExerciseUi> = mutableListOf()

    private val exercisesToAdd: MutableList<ExerciseUi> = mutableListOf()

    private var filter = ExerciseFilter()
    fun setExercises() {
        if (_exercises.value == null) {
            viewModelScope.launch {
                val exerciseList = repository.getExercises()
                withContext(Dispatchers.Main) {
                    _exercises.value = exerciseList
                    originalList = exerciseList.toMutableList()
                }
            }
        }
//
//        if (_exercises.value == null) {
//            viewModelScope.launch(Dispatchers.IO) {
//                val response =
//                    retrofit.getExerciseInfo("Token 12968c29c902b898702d8bba4c981059b5088e08")
//                withContext(Dispatchers.Main) {
//                    val exerciseList: MutableList<ExerciseUi> = mutableListOf()
//                    for (ex in response.results) {
//                        for (e in ex.exercises) {
//                            if (e.language == 2) {
//                                val image = if (ex.images.isNotEmpty())
//                                    ex.images[0].image
//                                else null
//                                val equipment = if (ex.equipment.isNotEmpty()) ex.equipment[0].name
//                                else ""
//                                exerciseList.add(
//                                    ExerciseUi(
//                                        e.name,
//                                        ex.category.name,
//                                        e.description,
//                                        equipment,
//                                        image,
//                                        e.id.toLong()
//                                    )
//                                )
//
//                            }
//
//                        }
//                    }
//                    originalList = exerciseList
//                    for (ex in exerciseList) {
//                        repository.addExercise(changeDesc(ex))
//                    }
//                    _exercises.value = exerciseList
//                }
//            }
//        }
    }

    fun addExercise(exerciseUi: ExerciseUi) {
        val newExerciseUi = exerciseUi.copy(isChecked = !exerciseUi.isChecked)

        if (exerciseUi.isChecked) {
            if (exercisesToAdd.contains(exerciseUi))
                exercisesToAdd.remove(exerciseUi)
        } else {
            if (!exercisesToAdd.contains(newExerciseUi))
                exercisesToAdd.add(newExerciseUi)
        }

        _exercises.value = _exercises.value?.toMutableList().also {
            if (it!!.contains(exerciseUi))
                it[it.indexOf(exerciseUi)] = newExerciseUi
        }
        if (originalList.contains(exerciseUi))
            originalList[originalList.indexOf(exerciseUi)] = newExerciseUi
    }

    fun getExerciseToAdd() = exercisesToAdd

    private fun changeDesc(ex: ExerciseUi): ExerciseUi {
        Log.i("hui1",ex.description)
        val d = ex.description.replace(Regex("<([^>]+)>"), "")
        Log.i("hui2",d)
        return ex.copy(description = d)
    }

    private fun filter(): List<ExerciseUi> {

        return originalList.filter {
            it.equipment.lowercase().contains(filter.equipment.lowercase()) &&
                    it.name.lowercase().contains(filter.name.lowercase()) &&
                    it.category.lowercase().contains(filter.category.lowercase())
        }
    }

    fun filterByEquipment(equipmentName: String) {
        filter = if (equipmentName == "All equipment")
            filter.copy(equipment = "")
        else filter.copy(equipment = equipmentName)
        _exercises.value = filter()
    }

    fun search(name: String) {
        filter = filter.copy(name = name)
        _exercises.value = filter()
    }

    fun filterByCategory(categoryName: String) {
        filter = if (categoryName == "All categories")
            filter.copy(category = "")
        else filter.copy(category = categoryName)
        _exercises.value = filter()
    }

}