package com.turun.myeasyfood.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.turun.myeasyfood.R
import com.turun.myeasyfood.activities.MainActivity
import com.turun.myeasyfood.activities.categori.CategoryDetailActivity
import com.turun.myeasyfood.adapters.categories.CategoriesAdapter
import com.turun.myeasyfood.databinding.FragmentCategoriesBinding
import com.turun.myeasyfood.viewModel.HomeViewModel


class CategoriesFragment : Fragment() {
    private lateinit var binding : FragmentCategoriesBinding
    private lateinit var categoriesAdapter : CategoriesAdapter
    private lateinit var homeViewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = (activity as MainActivity).homeViewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        observeCategories()
        onCategoryClick()
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = {
                categoriy->
            val intent = Intent(activity, CategoryDetailActivity::class.java)

            intent.putExtra(HomeFragment.CATEGORY_NAME,categoriy.strCategory)
            startActivity(intent)


        }
    }

    private fun observeCategories() {
        homeViewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer {
                categories->
            categoriesAdapter.setCategoryList(categories)
            //runapp çalıştır , devam
        })
    }


    private fun prepareRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context,3, GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }

}