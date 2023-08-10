package com.turun.myeasyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turun.myeasyfood.db.MealDatabase
import com.turun.myeasyfood.pojo.Meal
import com.turun.myeasyfood.pojo.MealList
import com.turun.myeasyfood.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModelDetail(val  mealDatabase : MealDatabase):ViewModel() {

    private var mealDetailsLiveData = MutableLiveData<Meal>()


    fun getMealDetail(id:String){
        RetrofitInstance.api.getMealDetail(id).enqueue(object : Callback<MealList?> {
            override fun onResponse(call: Call<MealList?>, response: Response<MealList?>) {
                if (response.body() !=null){
                    mealDetailsLiveData.value =response.body()!!.meals[0]
                }
            }

            override fun onFailure(call: Call<MealList?>, t: Throwable) {
                Log.d("MealDetailActivity",t.message.toString())            }
        })
    }

    fun observeMealDetailsLiveData():LiveData<Meal>{
        return mealDetailsLiveData
    }

    fun insertMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }
}