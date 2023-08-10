package com.turun.myeasyfood.adapters.categorycount

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.turun.myeasyfood.databinding.ActivityMealDetailBinding
import com.turun.myeasyfood.databinding.MealItemBinding
import com.turun.myeasyfood.pojo.popular.MealsByCategory

class CategoryMealsAdapterDetail:RecyclerView.Adapter<CategoryMealsAdapterDetail.CategoryMealsViewHolder>() {

    private var mealsList = ArrayList<MealsByCategory>()
    fun setMealsList(mealList:List<MealsByCategory>){
        this.mealsList = mealList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }

   inner class CategoryMealsViewHolder(val binding: MealItemBinding):
RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        return CategoryMealsViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealsList[position].strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = mealsList[position].strMeal
    }
}