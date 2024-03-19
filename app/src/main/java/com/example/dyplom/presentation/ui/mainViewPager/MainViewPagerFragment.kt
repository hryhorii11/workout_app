package com.example.dyplom.presentation.ui.mainViewPager

import com.example.dyplom.presentation.ui.timer.fragments.TimerViewPagerFragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dyplom.presentation.ui.profile.ProfileFragment
import com.example.dyplom.R
import com.example.dyplom.databinding.FragmentViewPagerBinding
import com.example.dyplom.presentation.ui.excersices.exerciseList.ExercisesListFragment
import com.example.dyplom.presentation.ui.workouts.workoutList.WorkoutListFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainViewPagerFragment : Fragment() {

    private val binding: FragmentViewPagerBinding by lazy {
        FragmentViewPagerBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)

        val fragmentList = arrayListOf(
            WorkoutListFragment(),
            TimerViewPagerFragment(),
            ProfileFragment()
        )
        val adapter =
            ViewPagerAdapter(fragmentList, childFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ ->
        }.attach()
        setTabText()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.viewPager.adapter = null
    }

    private fun setTabText() {

        binding.tabLayout.getTabAt(0)?.setIcon(R.drawable.baseline_sports_gymnastics_24)
        binding.tabLayout.getTabAt(1)?.setIcon(R.drawable.baseline_access_time_24)
        binding.tabLayout.getTabAt(2)?.setIcon(R.drawable.baseline_person_outline_24)
    }

}