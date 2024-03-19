package com.example.dyplom.presentation.ui.timer.fragments

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.dyplom.R
import com.example.dyplom.databinding.FramentTabataTimerBinding
import com.example.dyplom.presentation.ui.timer.ext.toSeconds
import com.example.dyplom.presentation.ui.timer.ext.toTimeFormat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabataTimerFragment : Fragment() {
    private val binding: FramentTabataTimerBinding by lazy {
        FramentTabataTimerBinding.inflate(
            layoutInflater
        )
    }
    private val args: TabataTimerFragmentArgs by navArgs()
    private lateinit var countdownTimerWork: CountDownTimerWithExtras
    private lateinit var countdownTimerRest: CountDownTimerWithExtras
    private lateinit var currentTimer: CountDownTimerWithExtras
    private var isPause = false
    private var isWorking = false
    private var roundsCount = 0
    private lateinit var mediaPlayer: MediaPlayer

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setStartSettings()
        setListeners()
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.timer)
        startCountdown()
        return binding.root
    }

    private fun setListeners() {
        with(binding) {
            pauseButton.setOnClickListener {
                if (isPause) resumeTimer()
                else pauseTimer()
            }
            resetButton.setOnClickListener {
                resetTimer()
            }
        }
    }

    private fun resetTimer() {
        if (isPause) resumeTimer()
        currentTimer.cancel()
        currentTimer = getTimer(args.timeSettings.preparationTime)
        currentTimer.start()
        setStartSettings()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.prepare()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setStartSettings() {
        with(binding) {
            textViewRounds.text =
                resources.getString(R.string.rounds) + " 0/${args.timeSettings.roundsNumber}"
            textViewState.text = resources.getString(R.string.preparation)
        }
        isWorking = false
        roundsCount = 0
    }

    private fun resumeTimer() {
        currentTimer =
            getTimer(binding.frame.text.toString().toSeconds(), currentTimer.origTime.toInt())
        if (binding.frame.text.toString().toSeconds() <= 3) mediaPlayer.start()
        currentTimer.start()
        isPause = false
        binding.pauseButton.text = resources.getString(R.string.pause)
    }

    private fun pauseTimer() {
        currentTimer.cancel()
        isPause = true
        binding.pauseButton.text = resources.getString(R.string.resume)
        if (mediaPlayer.isPlaying) mediaPlayer.pause()
    }

    private fun startCountdown() {
        currentTimer = getTimer(args.timeSettings.preparationTime)
        countdownTimerWork = getTimer(args.timeSettings.workTime)
        countdownTimerRest = getTimer(args.timeSettings.restTime)
        currentTimer.start()


    }

    private fun getTimer(time: Int, origTime: Int = time): CountDownTimerWithExtras {
        return CountDownTimerWithExtras(time * 1000L, origTime.toLong(), 1000)

    }

    private fun setRestTimer() {
        currentTimer = countdownTimerRest
        countdownTimerRest.start()
        binding.textViewState.setTextColor(resources.getColor(R.color.green))
        binding.textViewState.text = resources.getString(R.string.rest)

    }

    private fun setWorkTimer() {
        countdownTimerWork.start()
        binding.textViewState.text = resources.getString(R.string.work)
        binding.textViewState.setTextColor(resources.getColor(R.color.red))
        currentTimer = countdownTimerWork
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying) mediaPlayer.stop()
        currentTimer.cancel()
    }

    inner class CountDownTimerWithExtras(
        time: Long,
        val origTime: Long,
        countDownInterval: Long
    ) : CountDownTimer(time, countDownInterval) {
        override fun onTick(millisUntilFinished: Long) {
            val secondsUntilFinished = millisUntilFinished / 1000
            if (secondsUntilFinished <= 3L && !mediaPlayer.isPlaying) mediaPlayer.start()
            binding.frame.text = secondsUntilFinished.toTimeFormat()
            binding.frame.setProgress(
                (binding.frame.text.toString().toSeconds() / (origTime.toFloat()))
            )
            Log.i(
                "hui",
                (binding.frame.text.toString().toSeconds()).toString() + " " + origTime.toString()
            )
        }

        @SuppressLint("SetTextI18n")
        override fun onFinish() {
            roundsCount++
            if ((roundsCount - 1) / 2 < args.timeSettings.roundsNumber) {
                binding.textViewRounds.text =
                    resources.getString(R.string.rounds) + " ${(roundsCount - 1) / 2 + 1}/${args.timeSettings.roundsNumber}"
                isWorking = !isWorking

                if (isWorking) setWorkTimer()
                else setRestTimer()

            }
        }
    }
}
