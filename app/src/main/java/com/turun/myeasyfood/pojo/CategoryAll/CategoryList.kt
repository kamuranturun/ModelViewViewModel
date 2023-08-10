package com.turun.myeasyfood.pojo.CategoryAll


import com.google.gson.annotations.SerializedName

data class CategoryList(
    @SerializedName("categories")
    val categories: List<Categori>
)