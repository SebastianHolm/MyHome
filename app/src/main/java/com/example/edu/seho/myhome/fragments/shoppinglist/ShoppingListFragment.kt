package com.example.edu.seho.myhome.fragments.shoppinglist

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.edu.seho.myhome.R
import com.example.edu.seho.myhome.model.ShoppingCart
import com.example.edu.seho.myhome.viewmodel.ShoppingCartViewModel
import kotlinx.android.synthetic.main.fragment_shoppinglist.*
import kotlinx.android.synthetic.main.fragment_shoppinglist.view.*

/** @author Sebastian Holm
 *  This fragment handles displaying the ShoppingCart database. From this fragment
 *  the user can also delete all items in the database and add new items to the database.
 *  With the help of the recycler view adapter it also saves the checkboxes values for all
 *  items before leaving the fragment.
 */

class ShoppingListFragment : Fragment() {

    private val adapter = ShoppingAdapter()
    private lateinit var toolbar : androidx.appcompat.widget.Toolbar

    private lateinit var mShoppingCartViewModel : ShoppingCartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_shoppinglist, container, false)

        // Recyclerview
        val recyclerView = view.shopping_recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // ShoppingCart ViewModel
        mShoppingCartViewModel = ViewModelProvider(this).get(ShoppingCartViewModel::class.java)
        mShoppingCartViewModel.readAllData.observe(viewLifecycleOwner, Observer { shoppingCart ->
            adapter.setData(shoppingCart)
        })

        // Button listener for adding to the database
        view.add_to_cart.setOnClickListener {
            addToCart()
            add_to_cart.hideKeyboard()
        }

        toolbar = activity?.findViewById(R.id.toolbar)!!

        // Toolbar listener
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.delete_menu -> deleteAllShoppingCart()
            }
            true
        }

        setHasOptionsMenu(true)

        return view
    }

    // Updates all items with the current value of the checkbox which is obtained from the
    // recycler view adapter.
    override fun onStop() {
        val shoppingList = adapter.getData()
        for (sl in shoppingList){
            mShoppingCartViewModel.updateShoppingCart(sl)
        }
        super.onStop()
    }

    // Deletes all items in the shoppingCart database
    private fun deleteAllShoppingCart(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Ja") { _, _ ->
            mShoppingCartViewModel.deleteAllShoppingCart()
            Toast.makeText(requireContext(), "Allt har tagits bort", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Nej"){_, _ -> }
        builder.setTitle("Ta bort allt?")
        builder.setMessage("Är du säker på att du vill ta bort allt?")
        builder.create().show()
    }

    // Add to database
    private fun addToCart(){
        val name = product_input_field.text.toString()

        if (!TextUtils.isEmpty(name)){
            // Create ShoppingCart Object with the checkbox set to false
            val shoppingCart = ShoppingCart(0, name, false)

            // Add Data to Database
            mShoppingCartViewModel.addToCart(shoppingCart)
            Toast.makeText(requireContext(), "Tillagd!", Toast.LENGTH_LONG).show()
            product_input_field.setText("")
        } else {
            Toast.makeText(requireContext(), "Fyll i alla fälten.", Toast.LENGTH_LONG).show()
        }
    }

    // Hides the keyboard
    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    // Displays a custom menu on the toolbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}