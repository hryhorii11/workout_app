package com.example.dyplom.presentation.ui.timer.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dyplom.R
import com.example.dyplom.databinding.FragmentTabataTimerSettingsBinding
import com.example.dyplom.presentation.ui.mainViewPager.MainViewPagerFragmentDirections
import com.example.dyplom.presentation.ui.timer.ext.toSeconds
import com.example.dyplom.presentation.ui.timer.model.TabataTimeSettings
import com.example.dyplom.presentation.ui.timer.util.MyEditTextView
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class TabataTimerSettingsFragment : Fragment() {
    private val binding: FragmentTabataTimerSettingsBinding by lazy {
        FragmentTabataTimerSettingsBinding.inflate(
            layoutInflater
        )
    }
    private var isDialogOpened = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setListeners()
        return binding.root
    }

    private fun setListeners() {
        with(binding) {
            edittextPreparation.setOnClickListener {
                showNumberPicker(edittextPreparation)
            }
            edittextWork.setOnClickListener {
                showNumberPicker(edittextWork)
            }
            edittextRest.setOnClickListener {
                showNumberPicker(edittextRest)
            }
            edittextRounds.setOnClickListener {
                showNumberPicker(edittextRounds)
            }
            ButtonPlusPreparation.setOnClickListener {
                addOneSecond(edittextPreparation, 1)
            }
            ButtonPlusWork.setOnClickListener {
                addOneSecond(edittextWork, 1)
            }
            ButtonPlusRest.setOnClickListener {
                addOneSecond(edittextRest, 1)
            }
            ButtonPlusRounds.setOnClickListener {
                changeRoundsNum(1)
            }
            buttonMinusPreparation.setOnClickListener {
                addOneSecond(edittextPreparation, -1)
            }
            buttonMinusWork.setOnClickListener {
                addOneSecond(edittextWork, -1)
            }
            buttonMinusRest.setOnClickListener {
                addOneSecond(edittextRest, -1)
            }
            buttonMinusRounds.setOnClickListener {
                changeRoundsNum(-1)
            }
            buttonStart.setOnClickListener {
                startTimer()
            }
        }
    }

    private fun startTimer() {

        findNavController().navigate(
            MainViewPagerFragmentDirections.actionViewPagerFragmentToTabataTimerFragment(
                TabataTimeSettings(
                    binding.edittextPreparation.text.toString().toSeconds(),
                    binding.edittextWork.text.toString().toSeconds(),
                    binding.edittextWork.text.toString().toSeconds(),
                    binding.edittextRounds.text.toString().toInt()
                )
            )
        )
    }


    @SuppressLint("SetTextI18n")
    private fun changeRoundsNum(i: Int) {
        if (Integer.parseInt(binding.edittextRounds.text.toString()) + i >= 0) {
            binding.edittextRounds.setText(
                (Integer.parseInt(binding.edittextRounds.text.toString()) + i).toString()
            )
        }
    }

    private fun addOneSecond(edittext: MyEditTextView, seconds: Int) {
        val format = SimpleDateFormat("mm:ss", Locale.getDefault())
        val date = format.parse(edittext.text.toString())
        val calendar = Calendar.getInstance().apply {
            if (date != null) {
                time = date
            }
        }
        calendar.add(Calendar.SECOND, seconds)
        edittext.setText(format.format(calendar.time))
    }

    //TODO user can open only one dialog
    private fun showNumberPicker(edittext: MyEditTextView) {
        if (!isDialogOpened) {
            isDialogOpened = true
            val builder = AlertDialog.Builder(this.context)
            val view = this.layoutInflater.inflate(R.layout.dialog_picker, null)
            builder.setView(view)
            val pickerMinute = view.findViewById<View>(R.id.pickerMinute) as NumberPicker
            val pickerSecond = view.findViewById<View>(R.id.pickerSecond) as NumberPicker
            var isRoundEditText = false
            if (binding.edittextRounds == edittext) {
                pickerMinute.visibility = View.GONE
                isRoundEditText = true
                pickerSecond.minValue = 0
                pickerSecond.maxValue = 59
                pickerSecond.value = Integer.parseInt(edittext.text.toString())
            } else {
                pickerMinute.minValue = 0
                pickerMinute.maxValue = 59
                pickerMinute.value = Integer.parseInt(edittext.text.toString().substring(0, 2))
                pickerSecond.minValue = 0
                pickerSecond.maxValue = 59
                pickerSecond.value = Integer.parseInt(edittext.text.toString().substring(3))
            }

            builder.setPositiveButton(
                "ok"
            ) { _, _ ->
                setText(
                    pickerMinute.value.toString(),
                    pickerSecond.value.toString(),
                    edittext,
                    isRoundEditText
                )
            }
            builder.setOnDismissListener { isDialogOpened = false }
            builder.create().show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setText(
        minutes: String,
        seconds: String,
        edittext: MyEditTextView,
        isRoundEditText: Boolean
    ) {
        var min = minutes
        var sec = seconds
        if (isRoundEditText)
            edittext.setText(sec)
        else {
            if (Integer.parseInt(minutes) < 10)
                min = "0$minutes"
            if (Integer.parseInt(sec) < 10)
                sec = "0$sec"
            edittext.setText("$min:$sec")
        }
    }
}