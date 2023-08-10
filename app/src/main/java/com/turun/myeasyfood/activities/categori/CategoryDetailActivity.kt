package com.turun.myeasyfood.activities.categori

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.turun.myeasyfood.R
import com.turun.myeasyfood.adapters.categorycount.CategoryMealsAdapterDetail
import com.turun.myeasyfood.databinding.ActivityCategoryDetailBinding
import com.turun.myeasyfood.fragments.HomeFragment
import com.turun.myeasyfood.viewModel.categorycount.CategoryMealsViewModel

class CategoryDetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityCategoryDetailBinding
    lateinit var categoryMealsViewModel : CategoryMealsViewModel

    lateinit var categoryMealsAdapter :CategoryMealsAdapterDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]

        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoryMealsViewModel.observeCategoryDetailLiveData().observe(this, Observer {
            mealsList->binding.tvCategoryCount.text= mealsList.size.toString()
            categoryMealsAdapter.setMealsList(mealsList)
        })
        prepareRecyclerView()
    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapterDetail()
        binding.rvMealsCategoryCount.apply {
            layoutManager= GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter= categoryMealsAdapter
        }
    }

}