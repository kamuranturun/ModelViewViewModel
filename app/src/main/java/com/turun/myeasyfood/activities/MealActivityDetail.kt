package com.turun.myeasyfood.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bumptech.glide.Glide
import com.turun.myeasyfood.R
import com.turun.myeasyfood.databinding.ActivityMealDetailBinding
import com.turun.myeasyfood.db.MealDatabase
import com.turun.myeasyfood.fragments.HomeFragment
import com.turun.myeasyfood.pojo.Meal
import com.turun.myeasyfood.viewModel.MealViewModelDetail
import com.turun.myeasyfood.viewModel.MealViewModelFactory

class MealActivityDetail : AppCompatActivity() {

    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb: String

    private lateinit var binding: ActivityMealDetailBinding


    private lateinit var mealViewModelDetail :MealViewModelDetail

    private lateinit var youtubeLink:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getRandomInformationFromIntent()//bilgileri çek
        setInformationInViews()//görüntüyü çek

        val mealDatabase = MealDatabase.getInstance(this)
        val viewmodelFactory = MealViewModelFactory(mealDatabase)

        //inişılize
        mealViewModelDetail =ViewModelProvider(this, viewmodelFactory)[MealViewModelDetail::class.java]

        mealViewModelDetail.getMealDetail(mealId)

        observerMealDetailsLiveData()

        loadingCase()

        onYoutubeClick()

        onFavoriteClick()

    }

    private fun onFavoriteClick() {
        binding.btnAddToFavorites.setOnClickListener {
            mealToSave?.let {
                mealViewModelDetail.insertMeal(it)
                Toast.makeText(this,"Meal Saved..", Toast.LENGTH_LONG).show()
            }
        }
    }

    private var mealToSave:Meal?=null

    private fun onYoutubeClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)//işlem tamam
        }
    }

    private fun observerMealDetailsLiveData() {
        mealViewModelDetail.observeMealDetailsLiveData().observe(this,object : Observer<Meal?> {
            override fun onChanged(value: Meal?) {
                onResponseCase()
                val meal = value
                mealToSave = meal

                binding.tvCategory.text ="Category: ${meal!!.strCategory}"
                binding.tvArea.text = "Area: ${meal!!.strArea}"
                binding.tvInstructionSteps.text = meal.strInstructions
                //detayda verileri gösterme işlemi tamam

                youtubeLink = meal.strYoutube.toString()

            }
        })
    }



    private fun setInformationInViews() {
        Glide.with(applicationContext).load(mealThumb)
            .into(binding.imgMealDetail)
        //go to meal api

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getRandomInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase(){
        //görünmez yüklenirken
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddToFavorites.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase(){
        //yüklendiğinde görünür
        binding.btnAddToFavorites.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}