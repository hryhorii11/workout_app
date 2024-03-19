package com.example.dyplom.presentation.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dyplom.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ExerciseOptionsBottomSheet : BottomSheetDialogFragment() {


    companion object {
        fun newInstance() = ExerciseOptionsBottomSheet()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_shhet_exercise_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (view.parent as View).setBackgroundColor(resources.getColor(R.color.transparentColor))


    }
}