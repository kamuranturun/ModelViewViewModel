package com.turun.myeasyfood.adapters.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.turun.myeasyfood.databinding.CategoryItemBinding
import com.turun.myeasyfood.databinding.PopularItemsBinding
import com.turun.myeasyfood.pojo.CategoryAll.Categori

class CategoriesAdapter:RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {


    private var categoryList = ArrayList<Categori>()
    fun setCategoryList(categoriesList:List<Categori>){
        this.categoryList = categoriesList as ArrayList<Categori>
        notifyDataSetChanged()
   }
    var onItemClick: ((Categori)->Unit)? =null

    inner class CategoriesViewHolder(val binding:CategoryItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        Glide.with(holder.itemView).load(categoryList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text = categoryList[position].strCategory

        //click
        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoryList[position])
        }
    }
}