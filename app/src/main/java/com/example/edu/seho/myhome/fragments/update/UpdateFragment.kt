package com.example.edu.seho.myhome.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.edu.seho.myhome.R
import com.example.edu.seho.myhome.fragments.SpinnerDate
import com.example.edu.seho.myhome.model.Storage
import com.example.edu.seho.myhome.viewmodel.StorageViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

/** @author Sebastian Holm
 *  This fragment handles the updating of the Storage database.
 */

class UpdateFragment : Fragment() {

    private lateinit var datePicker: DatePicker
    private lateinit var boughtPicker: DatePicker

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mStorageViewModel : StorageViewModel

    private var allItems = mutableListOf<Storage>()

    // Helper class
    private val spinnerDate = SpinnerDate()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        // Storage ViewModel
        mStorageViewModel = ViewModelProvider(this).get(StorageViewModel::class.java)

        mStorageViewModel.readAllData.observe(viewLifecycleOwner, Observer { storage ->
            allItems.addAll(storage)
        })

        // DatePickers
        datePicker = view.findViewById(R.id.update_date_picker)
        boughtPicker = view.findViewById(R.id.update_bought_picker)

        // Set current values with the help of the helper class
        spinnerDate.setCurrentDate(datePicker, boughtPicker, spinnerDate.makeString(args.currentStorage.date),
            spinnerDate.makeString(args.currentStorage.bought))

        // Updates the textView with the current items values
        view.updateName.setText(args.currentStorage.name)

        view.update_category_text.setText(args.currentStorage.category)

        view.update_button.setOnClickListener {
            updateItem()
        }

        setHasOptionsMenu(true)

        var toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)!!

        // Toolbar listener
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.delete_menu){
                deleteStorage()
            }
            true
        }

        return view
    }

    // Displays a custom menu on the toolbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // Updates an item to the database
    private fun updateItem(){
        if (!spinnerDate.checkDate(datePicker, boughtPicker)){
            Toast.makeText(requireContext(), "Inköpsdatumet är senare än bäst före-datumet.", Toast.LENGTH_LONG).show()
            return
        }

        val date = spinnerDate.makeInt(datePicker)
        val bought = spinnerDate.makeInt(boughtPicker)
        val name = updateName.text.toString()
        val category = update_category_text.text.toString()

        if (inputCheck(name, category)){
            // Create Storage Object
            val updateStorage = Storage(args.currentStorage.id, name, category, date, bought, true)
            // Update Current Storage
            mStorageViewModel.updateStorage(updateStorage)
            Toast.makeText(requireContext(), "Uppdaterade!", Toast.LENGTH_SHORT).show()
            // Navigate Back
            findNavController().navigateUp()
        } else {
            Toast.makeText(requireContext(), "Fyll i alla fälten.", Toast.LENGTH_SHORT).show()
        }
    }

    // Checks if all inputs fields has been filled
    private fun inputCheck(name : String, category: String) : Boolean{
        return !TextUtils.isEmpty(name) && !TextUtils.isEmpty(category)
    }

    // Deletes the current item from the storage database. If it is the last with that name
    // the boolean isUsed is instead set to false to indicate that the item has been removed by the user.
    // The items can then still be used to fill in slots when an item of the same name is created in the add fragment.
    private fun deleteStorage(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Ja"){ _, _ ->
            if (checkIfLast(args.currentStorage)){
                val updateStorage = Storage(args.currentStorage.id, args.currentStorage.name, args.currentStorage.category,
                    args.currentStorage.date, args.currentStorage.bought, false)
                mStorageViewModel.updateStorage(updateStorage)
            } else {
                mStorageViewModel.deleteStorage(args.currentStorage)
            }
            Toast.makeText(requireContext(), "Tog bort: ${args.currentStorage.name}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("Nej"){_, _ -> }
        builder.setTitle("Ta bort ${args.currentStorage.name}?")
        builder.setMessage("Är du säker på att du vill ta bort ${args.currentStorage.name}?")
        builder.create().show()
    }

    // Checks if the storage item is the last with that name in the database
    private fun checkIfLast(storage : Storage) : Boolean{
        var i = 0
        for (stg in allItems){
            if (stg.name == storage.name){
                i++
            }
            if (i > 1){
                return false
            }
        }
        return true
    }
}