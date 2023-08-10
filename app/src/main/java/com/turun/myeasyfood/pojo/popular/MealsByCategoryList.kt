package com.turun.myeasyfood.pojo.popular


import com.google.gson.annotations.SerializedName

data class MealsByCategoryList(
    @SerializedName("meals")
    val meals: List<MealsByCategory>
)