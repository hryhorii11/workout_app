package com.example.dyplom.data.exerciseCategories

import com.example.dyplom.R

object CategoryList {
    fun getCategoryList() = listOf(
        CategoryItem("All categories",R.drawable.baseline_select_all_24),
        CategoryItem("Arms", R.drawable.arms_ic),
        CategoryItem("Abs", R.drawable.abs_ic),
        CategoryItem("Legs", R.drawable.legs_ic),
        CategoryItem("Calves", R.drawable.calves_ic),
        CategoryItem("Shoulders", R.drawable.shoulders_ic),
        CategoryItem("Back", R.drawable.back_ic),
        CategoryItem("Chest", R.drawable.chest_ic),
        CategoryItem("Cardio", R.drawable.cardio_ic),
    )
}