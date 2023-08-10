package com.turun.myeasyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turun.myeasyfood.db.MealDatabase
import com.turun.myeasyfood.pojo.CategoryAll.Categori
import com.turun.myeasyfood.pojo.CategoryAll.CategoryList
import com.turun.myeasyfood.pojo.Meal
import com.turun.myeasyfood.pojo.MealList
import com.turun.myeasyfood.pojo.popular.MealsByCategory
import com.turun.myeasyfood.pojo.popular.MealsByCategoryList
import com.turun.myeasyfood.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val mealDatabase: MealDatabase
) : ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData = MutableLiveData<List<Categori>>()

    private var favoritesMealsLiveData = mealDatabase.mealDao().getAllMeals()



    fun deleteMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }
    //45 favorites save roomdb,go back favortesfragment

    //47 favorites save roomdb,
    fun insertMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }

    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList?> {
            override fun onResponse(call: Call<MealList?>, response: Response<MealList?>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                }
            }

            override fun onFailure(call: Call<MealList?>, t: Throwable) {
                Log.d("home fragment", t.message.toString())
            }
        })
    }

    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }


    fun getPopularItems() {
        RetrofitInstance.api.getPopularItems("SeaFood")
            .enqueue(object : Callback<MealsByCategoryList?> {
                override fun onResponse(
                    call: Call<MealsByCategoryList?>,
                    response: Response<MealsByCategoryList?>
                ) {
                    if (response.body() != null) {
                        popularItemsLiveData.value = response.body()!!.meals
                    }
                }

                override fun onFailure(call: Call<MealsByCategoryList?>, t: Throwable) {
                    Log.d("homefragmentpopularitem", t.message.toString())
                }
            })
    }

    fun observePopularItemsLiveData(): LiveData<List<MealsByCategory>> {
        return popularItemsLiveData
    }

    fun getCategories(){//tüm katori
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList?> {
            override fun onResponse(call: Call<CategoryList?>, response: Response<CategoryList?>) {
                response.body()?.let { 
                    categoryList ->categoriesLiveData.postValue(categoryList.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList?>, t: Throwable) {
                Log.e("homeviewmodelcategorytasarım",t.message.toString())
            }
        })
    }
    fun observeCategoriesLiveData():LiveData<List<Categori>>{
        return categoriesLiveData
    }

    fun observeFavoritesMealsLiveData():LiveData<List<Meal>>{
        return favoritesMealsLiveData
    }
}