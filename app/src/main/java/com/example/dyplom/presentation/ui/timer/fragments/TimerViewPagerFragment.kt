package com.example.dyplom.presentation.ui.timer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dyplom.databinding.FragmentTimerViewPagerBinding
import com.example.dyplom.presentation.ui.mainViewPager.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimerViewPagerFragment : Fragment() {

    private val binding: FragmentTimerViewPagerBinding by lazy {
        FragmentTimerViewPagerBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)

        val fragmentList = arrayListOf(
            StopwatchFragment(),
            TabataTimerSettingsFragment()
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
        binding.tabLayout.getTabAt(0)?.text = "Stopwatch"
        binding.tabLayout.getTabAt(1)?.text = "Tabata"
    }

}