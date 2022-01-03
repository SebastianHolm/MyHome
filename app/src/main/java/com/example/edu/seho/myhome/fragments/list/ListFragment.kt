package com.example.edu.seho.myhome.fragments.list

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.edu.seho.myhome.R
import com.example.edu.seho.myhome.enumClasses.Order
import com.example.edu.seho.myhome.viewmodel.StorageViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

/** @author Sebastian Holm
 *  This fragment handles displaying the storage database. From this fragment
 *  the user can also delete all items in the database and move to fragment for adding
 *  and updating the database.
 */

class ListFragment : Fragment() {

    private var currentOrder : Order = Order.Id
    private val adapter = ListAdapter()
    private lateinit var toolbar : androidx.appcompat.widget.Toolbar

    private lateinit var mStorageViewModel : StorageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        // Recyclerview
        val recyclerView = view.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // StorageViewModel
        mStorageViewModel = ViewModelProvider(this).get(StorageViewModel::class.java)
        mStorageViewModel.readAllData.observe(viewLifecycleOwner, Observer { storage ->
            adapter.setData(storage)
        })

        // Floating action button navigation to add fragment
        view.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        toolbar = activity?.findViewById(R.id.toolbar)!!

        // Listener for toolbar items
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.delete_menu -> deleteAllStorage()
                R.id.select_ordering -> changeOrdering(it)
            }
            true
        }

        setHasOptionsMenu(true)

        // Hides the keyboard upon entering the list fragment
        toolbar.hideKeyboard()

        return view
    }

    // Hides the keyboard
    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    // Inflate custom menu for the toolbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // Changes the ordering of all items displayed from the database
    private fun changeOrdering(item : MenuItem){
        currentOrder = when (currentOrder){
            Order.Id -> Order.Category
            Order.Category -> Order.Date
            Order.Date -> Order.Name
            else -> Order.Id
        }

        when (currentOrder){
            Order.Id -> item.setIcon(R.drawable.ic_age)
            Order.Category -> item.setIcon(R.drawable.ic_category)
            Order.Date -> item.setIcon(R.drawable.ic_date)
            else -> item.setIcon(R.drawable.ic_name)
        }
        mStorageViewModel.changeOrdering(currentOrder)
        mStorageViewModel.readAllData.observe(viewLifecycleOwner, Observer { storage ->
            adapter.setData(storage)
        })
    }

    // Deletes all items in the database
    private fun deleteAllStorage(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Ja") { _, _ ->
            mStorageViewModel.deleteAllStorage()
            Toast.makeText(requireContext(), "Allt har tagits bort", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Nej"){_, _ -> }
        builder.setTitle("Ta bort allt?")
        builder.setMessage("Är du säker på att du vill ta bort allt?")
        builder.create().show()
    }
}