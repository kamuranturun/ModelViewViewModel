package com.turun.myeasyfood.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.turun.myeasyfood.R
import com.turun.myeasyfood.activities.MainActivity
import com.turun.myeasyfood.adapters.favorites.FavoritesMealsAdapter
import com.turun.myeasyfood.databinding.FragmentFavoritesBinding
import com.turun.myeasyfood.viewModel.HomeViewModel


class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var homeViewModel : HomeViewModel
    private lateinit var favoritesAdapter : FavoritesMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel=(activity as MainActivity).homeViewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeFavorites()
        prepareRecyclerView()
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            )=true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position= viewHolder.adapterPosition
                //46 favorites save roomdb,
                homeViewModel.deleteMeal(favoritesAdapter.differ.currentList[position])
                //46 favorites save roomdb, go to homeviewmodel

                Snackbar.make(requireView(),"Meal deleted", Snackbar.LENGTH_LONG).setAction(
                    "Undo",
                    View.OnClickListener {
                        //48 favorites save roomdb,
                        //homeViewModel.insertMeal(favoritesAdapter.differ.currentList[position])


                        //48 favorites save roomdb,
                    }
                ).show()
            }
        }
        //49 favorites save roomdb,
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorites)
    }

    private fun prepareRecyclerView() {
        favoritesAdapter = FavoritesMealsAdapter()
        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(context,2, GridLayoutManager.VERTICAL,false)
            adapter= favoritesAdapter
        }
    }

    private fun observeFavorites() {
        homeViewModel.observeFavoritesMealsLiveData().observe(requireActivity(), Observer {
            meals->
            favoritesAdapter.differ.submitList(meals)
        })
    }


}