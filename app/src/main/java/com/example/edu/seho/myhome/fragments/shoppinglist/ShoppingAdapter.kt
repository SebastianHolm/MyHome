package com.example.edu.seho.myhome.fragments.shoppinglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.edu.seho.myhome.R
import com.example.edu.seho.myhome.model.ShoppingCart

/** @author Sebastian Holm
 *  This adapter is used in the Shopping list fragment for displaying
 *  the ShoppingCart database in a recycler view. It also helps save
 *  if the checkbox is marked for each item.
 */

class ShoppingAdapter : RecyclerView.Adapter<ShoppingAdapter.MyViewHolder>(){

    private var shoppingList = emptyList<ShoppingCart>()

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.shopping_row, parent, false))
        return view
    }

    override fun getItemCount(): Int {
        return shoppingList.size
    }

    // Sets all textViews to show the current item for the custom row and saves the
    // checkBox status when changed and saves it.
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = shoppingList[position]
        val checkBox = holder.itemView.findViewById<CheckBox>(R.id.checkBox)
        holder.itemView.findViewById<TextView>(R.id.shopping_name_text).text = currentItem.name
        checkBox.isChecked = currentItem.done

        checkBox.setOnClickListener {
            shoppingList[position].done = checkBox.isChecked
        }
    }

    fun setData(shoppingCart : List<ShoppingCart>){
        this.shoppingList = shoppingCart
        notifyDataSetChanged()
    }

    fun getData() : List<ShoppingCart>{
        return shoppingList
    }
}