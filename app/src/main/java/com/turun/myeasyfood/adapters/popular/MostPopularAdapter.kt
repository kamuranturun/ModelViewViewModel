package com.turun.myeasyfood.adapters.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.turun.myeasyfood.databinding.PopularItemsBinding
import com.turun.myeasyfood.pojo.MealList
import com.turun.myeasyfood.pojo.popular.MealsByCategory
import com.turun.myeasyfood.pojo.popular.MealsByCategoryList

class MostPopularAdapter : RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {

    lateinit var onItemClick:((MealsByCategory)->Unit)//tıklanacak liste

    private var mealList = ArrayList<MealsByCategory>()//popüler yemek listesi atama
    fun setMeals(mealList: ArrayList<MealsByCategory>) {//türünde
        this.mealList = mealList  //üstteki eşittir alttaki
        notifyDataSetChanged()
    }

    class PopularMealViewHolder(var binding: PopularItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
       return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),
       parent,false))
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)//yüklendi

        //tıkla
        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealList[position])
        }
    }
}