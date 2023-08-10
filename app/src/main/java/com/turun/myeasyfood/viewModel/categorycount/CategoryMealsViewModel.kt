package com.turun.myeasyfood.viewModel.categorycount

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.turun.myeasyfood.pojo.popular.MealsByCategory
import com.turun.myeasyfood.pojo.popular.MealsByCategoryList
import com.turun.myeasyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel: ViewModel() {

    val categoryCountMealsLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(categoryName:String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object : Callback<MealsByCategoryList?> {
            override fun onResponse(
                call: Call<MealsByCategoryList?>,
                response: Response<MealsByCategoryList?>
            ) {
                response.body()?.let {
                    mealsList->
                    categoryCountMealsLiveData.postValue(mealsList.meals)
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList?>, t: Throwable) {
                Log.e("categoryMealsViewModel",t.message.toString())
            }
        })
    }

    fun observeCategoryDetailLiveData():LiveData<List<MealsByCategory>>{
        return categoryCountMealsLiveData
    }
}