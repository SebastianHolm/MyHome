package com.example.edu.seho.myhome.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.edu.seho.myhome.R
import com.example.edu.seho.myhome.fragments.SpinnerDate
import com.example.edu.seho.myhome.model.Storage
import kotlinx.android.synthetic.main.custom_row.view.*

/** @author Sebastian Holm
 *  This adapter is used on a recycler view in the ListFragment. It helps
 *  display the database in customized rows which when clicked navigates the
 *  user to the update fragment from where the item clicked can be edited.
 */

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>(){

    private var storageList = emptyList<Storage>()

    private var spinnerDate = SpinnerDate()

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun getItemCount(): Int {
        return storageList.size
    }

    //sets the textViews on the custom row to the values of the current item and navigates the user
    // to the update fragment on click of the item
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = storageList[position]
        if (!currentItem.isUsed){
            return
        }
        holder.itemView.findViewById<TextView>(R.id.name_text).text = currentItem.name
        holder.itemView.findViewById<TextView>(R.id.date_text).text = spinnerDate.makeString(currentItem.date)
        holder.itemView.findViewById<TextView>(R.id.category_text).text = currentItem.category

        holder.itemView.rowLayout.setOnClickListener{
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(storage : List<Storage>){
        this.storageList = storage
        notifyDataSetChanged()
    }
}