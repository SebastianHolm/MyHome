package com.example.edu.seho.myhome.fragments.foodPictures

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.example.edu.seho.myhome.R

/** @author Sebastian Holm
 * This fragment is a menu displaying all "food pictures" from where
 * the user can access all pictures independently and change them in separate fragments.
 */

class FoodPicturesFragment : Fragment() {

    private lateinit var sharedPreferences : SharedPreferences

    private lateinit var kyl_image : ImageView
    private lateinit var frys_image : ImageView
    private lateinit var skafferi_image : ImageView
    private lateinit var krydd_image : ImageView

    // Locations where the images gets stored
    private var kylUri: Uri? = null
    private var frysUri: Uri? = null
    private var skafferiUri: Uri? = null
    private var kryddUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_food_pictures, container, false)

        sharedPreferences = requireActivity().getSharedPreferences("FoodPictures", Context.MODE_PRIVATE)

        kyl_image = view.findViewById(R.id.kyl_image)
        frys_image = view.findViewById(R.id.frys_image)
        skafferi_image = view.findViewById(R.id.skafferi_image)
        krydd_image = view.findViewById(R.id.krydd_image)

        // Get any image locations has been stored and load them
        val kyl = sharedPreferences.getString("kyl", null)
        val frys = sharedPreferences.getString("frys", null)
        val skafferi = sharedPreferences.getString("skafferi", null)
        val kryddor = sharedPreferences.getString("kryddor", null)

        // Check if the image location exist and set the image to the saved one
        if (kyl != null){
            kylUri = Uri.parse(kyl)
            kyl_image.setImageURI(kylUri)
        }

        if (frys != null){
            frysUri = Uri.parse(frys)
            frys_image.setImageURI(frysUri)
        }

        if (skafferi != null){
            skafferiUri = Uri.parse(skafferi)
            skafferi_image.setImageURI(skafferiUri)
        }

        if (kryddor != null){
            kryddUri = Uri.parse(kryddor)
            krydd_image.setImageURI(kryddUri)
        }

        // Navigates to fragment upon pressing image
        kyl_image.setOnClickListener {
            val action = FoodPicturesFragmentDirections.actionFoodPicturesFragmentToPictureTakerFragment("kyl")
            findNavController().navigate(action)
        }

        frys_image.setOnClickListener {
            val action = FoodPicturesFragmentDirections.actionFoodPicturesFragmentToPictureTakerFragment("frys")
            findNavController().navigate(action)
        }

        skafferi_image.setOnClickListener {
            val action = FoodPicturesFragmentDirections.actionFoodPicturesFragmentToPictureTakerFragment("skafferi")
            findNavController().navigate(action)
        }

        krydd_image.setOnClickListener {
            val action = FoodPicturesFragmentDirections.actionFoodPicturesFragmentToPictureTakerFragment("kryddor")
            findNavController().navigate(action)
        }

        setHasOptionsMenu(true)

        return view
    }
}