package com.example.edu.seho.myhome.fragments.add

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.edu.seho.myhome.R
import com.example.edu.seho.myhome.fragments.SpinnerDate
import com.example.edu.seho.myhome.model.Storage
import com.example.edu.seho.myhome.viewmodel.StorageViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

/** @author Sebastian Holm
 *  This fragment handles the additions of items to the storage database.
 *  If a name supplied matches an already existing item in the database the rest of the
 *  input fields will be filled in for the user.
 */

class AddFragment : Fragment() {

    private lateinit var datePicker: DatePicker
    private lateinit var boughtPicker: DatePicker
    private lateinit var mStorageViewModel : StorageViewModel

    //List of all current items in database
    private val allItems = mutableListOf<Storage>()

    //Helper class
    val spinnerDate = SpinnerDate()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        // Storage viewModel to access database
        mStorageViewModel = ViewModelProvider(this).get(StorageViewModel::class.java)

        mStorageViewModel.readAllData.observe(viewLifecycleOwner, Observer { storage ->
            allItems.addAll(storage)
        })

        // DatePickers
        datePicker = view.findViewById(R.id.add_date_picker)
        boughtPicker = view.findViewById(R.id.add_bought_picker)

        // Check History Database for match of name and fill in all other input slots
        view.addName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                for(stg in allItems){
                    val name = stg.name
                    if (name == s.toString()){
                        spinnerDate.setDateCorrespondingToSaved(datePicker, boughtPicker, spinnerDate.makeString(stg.date), spinnerDate.makeString(stg.bought))
                        category_text.setText(stg.category)

                        // Hides the keyboard after all slots has been filled to indicate that a match was found
                        addName.hideKeyboard()
                    }
                }
            }
        })

        // on click to handle inserts into database
        view.add_button.setOnClickListener{
            insertDataToDatabase()
        }

        return view
    }

    // Hides the keyboard
    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    // Insert into database
    private fun insertDataToDatabase(){
        if (!spinnerDate.checkDate(datePicker, boughtPicker)){
            Toast.makeText(requireContext(), "Inköpsdatumet är senare än bäst före-datumet.", Toast.LENGTH_LONG).show()
            return
        }
        val date = spinnerDate.makeInt(datePicker)
        val bought = spinnerDate.makeInt(boughtPicker)

        val name = addName.text.toString()
        val category = category_text.text.toString()

        if (inputCheck(name, category)){
            // Create Storage Object
            val storage = Storage(0, name, category, date, bought, true)
            // Add Data to Database
            mStorageViewModel.addStorage(storage)
            Toast.makeText(requireContext(), "Tillagd!", Toast.LENGTH_LONG).show()
            // Navigate back
            findNavController().navigateUp()
        } else {
            Toast.makeText(requireContext(), "Fyll i alla fälten.", Toast.LENGTH_LONG).show()
        }
    }

    //Checks that input text-fields are not empty
    private fun inputCheck(name : String, category : String) : Boolean{
        return !(TextUtils.isEmpty(name)) && !TextUtils.isEmpty(category)
    }
}