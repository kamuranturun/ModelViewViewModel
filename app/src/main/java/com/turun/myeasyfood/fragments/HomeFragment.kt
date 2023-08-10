package com.turun.myeasyfood.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.turun.myeasyfood.activities.MainActivity
import com.turun.myeasyfood.activities.MealActivityDetail
import com.turun.myeasyfood.activities.categori.CategoryDetailActivity
import com.turun.myeasyfood.adapters.categories.CategoriesAdapter
import com.turun.myeasyfood.adapters.popular.MostPopularAdapter
import com.turun.myeasyfood.databinding.FragmentHomeBinding
import com.turun.myeasyfood.pojo.Meal
import com.turun.myeasyfood.pojo.popular.MealsByCategory
import com.turun.myeasyfood.viewModel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var homeViewModel: HomeViewModel


    private lateinit var randomMeal: Meal

    private lateinit var categoriesAdapter: CategoriesAdapter


    companion object {
        //sadece anahtar kelimeler
        const val MEAL_ID = "com.turun.myeasyfood.fragments.idMeal"//id
        const val MEAL_NAME = "com.turun.myeasyfood.fragments.nameMeal"//isim
        const val MEAL_THUMB = "com.turun.myeasyfood.fragments.thumbMeal"//resim

        const val CATEGORY_NAME ="com.turun.myeasyfood.fragments.categoryName"//key tanımladık
    }

    private lateinit var popularItemsAdapter: MostPopularAdapter //adapterin örneğini oluştur


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel = (activity as MainActivity).homeViewModel

        popularItemsAdapter = MostPopularAdapter()//inişılayz çalıştırmak
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getRandomMeal()
        obsereRandomMeal()


        onRandomMealClick()

        homeViewModel.getPopularItems()
        observePopularItemsLiveData()
        preparePopularItemRecyclerView()


        onPopularItemClick()


        homeViewModel.getCategories()
        observeCategoriesLiveData()


        prepareCategoriesRecyclerView()


        onCategoryClick()
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick ={
            categori->
            val intent = Intent(activity, CategoryDetailActivity::class.java)
            intent.putExtra(CATEGORY_NAME,categori.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()//çalıştır
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)

            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        homeViewModel.observeCategoriesLiveData().observe(viewLifecycleOwner,
            Observer { categories -> categoriesAdapter.setCategoryList(categories) })
        //kategori tasarımı bitti
    }


    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealActivityDetail::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)//id gönderdik
            intent.putExtra(MEAL_NAME, meal.strMeal)//yemek ismi gönderdik
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)//resmi gönderdik
            startActivity(intent)
            //popülerde detaya gitme işlemi tamam
        }
    }

    private fun preparePopularItemRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        homeViewModel.observePopularItemsLiveData().observe(viewLifecycleOwner) { mealList ->
            popularItemsAdapter.setMeals(mealList = mealList as ArrayList<MealsByCategory>)
            //bitti runapp
        }
    }


    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivityDetail::class.java)

            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun obsereRandomMeal() {
        homeViewModel.observeRandomMealLiveData().observe(viewLifecycleOwner) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomMeal = meal
        }

    }




}

