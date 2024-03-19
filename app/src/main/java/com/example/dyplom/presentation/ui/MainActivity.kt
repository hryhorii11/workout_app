package com.example.dyplom.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.dyplom.databinding.ActivityMainBinding
import com.example.dyplom.presentation.ui.auth.UserViewModel
import com.example.dyplom.presentation.ui.workouts.createWorkout.CreateWorkoutViewModel
import com.firebase.ui.auth.data.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val workoutViewModel:CreateWorkoutViewModel by viewModels()
  //  private val userViewModel:UserViewModel by viewModels()
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   userViewModel.autoLogin()
        setContentView(binding.root)
    }


}