package com.example.edu.seho.myhome.fragments.startmenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.edu.seho.myhome.R

/** @author Sebastian Holm
 *  This fragment is the start menu of the navigationUI.
 *  From this fragment the navigation drawer can be accessed and
 *  allows the user to navigate to all but one fragment present in
 *  the app. The code for this is present in the MainActivity class.
 */

class StartMenuFragment : Fragment() {

    private lateinit var listButton: Button
    private lateinit var shoppingListButton: Button
    private lateinit var foodPicturesButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_start_menu, container, false)

        listButton = view.findViewById(R.id.listFragment_button)
        shoppingListButton = view.findViewById(R.id.shoppingListFragment_button)
        foodPicturesButton = view.findViewById(R.id.foodPicturesFragment_button)

        // Button listeners which navigate to fragments
        listButton.setOnClickListener {
            findNavController().navigate(R.id.action_startMenuFragment_to_listFragment)
        }

        shoppingListButton.setOnClickListener {
            findNavController().navigate(R.id.action_startMenuFragment_to_shoppinglist)
        }

        foodPicturesButton.setOnClickListener {
            findNavController().navigate(R.id.action_startMenuFragment_to_foodPicturesFragment)
        }

        return view
    }

}