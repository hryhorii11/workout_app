package com.example.dyplom.data.equipment

import com.example.dyplom.R

object EquipmentList {
    fun getEquipmentList()= listOf(
        EquipmentItem("All equipment",R.drawable.baseline_select_all_24),
        EquipmentItem("bodyweight",R.drawable.bodyweight_ic ),
        EquipmentItem("Kettlebell", R.drawable.kettebell_ic),
        EquipmentItem("Barbell", R.drawable.barbell_ic),
        EquipmentItem("Dumbbell", R.drawable.dumbbell_ic),
        EquipmentItem("Pull-up bar",R.drawable.pull_up_bar_ic),
        EquipmentItem("Gym mat",R.drawable.gym_mat_ic),
        EquipmentItem("Bench",R.drawable.bench_ic),
        EquipmentItem("SZ-Bar",R.drawable.ez_bar_ic),
        EquipmentItem("Swiss Ball",R.drawable.swiis_ball_ic),
    )
}