package com.turun.myeasyfood.retrofit

import com.turun.myeasyfood.pojo.CategoryAll.CategoryList
import com.turun.myeasyfood.pojo.MealList
import com.turun.myeasyfood.pojo.popular.MealsByCategoryList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>


    @GET("lookup.php")
    fun getMealDetail(@Query("i") id:String):Call<MealList>

    //popular
    @GET("filter.php")
    fun getPopularItems(@Query("c") categoryName:String):Call<MealsByCategoryList>


    @GET("categories.php")
    fun getCategories():Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName:String):Call<MealsByCategoryList>
}