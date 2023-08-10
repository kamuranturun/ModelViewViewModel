package com.turun.myeasyfood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.turun.myeasyfood.R
import com.turun.myeasyfood.db.MealDatabase
import com.turun.myeasyfood.viewModel.HomeViewModel
import com.turun.myeasyfood.viewModel.HomeViewModelFactory

class MainActivity : AppCompatActivity() {

val homeViewModel:HomeViewModel by lazy {
    val mealDatabase = MealDatabase.getInstance(this)
    val homeViewModelProviderFactory = HomeViewModelFactory(mealDatabase)

     ViewModelProvider(this,homeViewModelProviderFactory)[HomeViewModel::class.java]
}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //bottom nav
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.btm_nav)
        val navController = Navigation.findNavController(this, R.id.host_fragment)
        NavigationUI.setupWithNavController(bottomNavigation, navController)



    }
}
//MVVM genel yapısı

//1->>data class
//2->>interface
//3->>object
//4->> viewModel
//5->>fragment veya aktivity









