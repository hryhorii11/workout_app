package com.example.dyplom.presentation.ui.timer.fragments

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dyplom.databinding.FragmentStopwatchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StopwatchFragment : Fragment() {
    private val binding: FragmentStopwatchBinding by lazy {
        FragmentStopwatchBinding.inflate(
            layoutInflater
        )
    }
    private var isRunning: Boolean = false
    private var timeAfterStop: Long? = null
    private var startTime: Long = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setListeners()
        startTime = SystemClock.elapsedRealtime()
        return binding.root

    }

    private fun setListeners() {
        with(binding) {
            buttonStart.setOnClickListener {
                startChronometer()
            }
            buttonReset.setOnClickListener {
                resetChronometer()
            }
            buttonStop.setOnClickListener {
                stopChronometer()
            }

        }
    }


    private fun startChronometer() {
        with(binding) {
            if (!isRunning) {
                if (timeAfterStop == null)
                    chronometerStopWatch.base = SystemClock.elapsedRealtime()
                else
                    chronometerStopWatch.base =
                        SystemClock.elapsedRealtime() + timeAfterStop as Long
                chronometerStopWatch.start()
                isRunning = true
            }
        }
    }

    private fun stopChronometer() {
        if (isRunning) {
            binding.chronometerStopWatch.stop()
            timeAfterStop = binding.chronometerStopWatch.base - SystemClock.elapsedRealtime();
            isRunning = false
        }
    }

    private fun resetChronometer() {
        binding.chronometerStopWatch.base = SystemClock.elapsedRealtime()
        stopChronometer()
        timeAfterStop = null
    }


}
