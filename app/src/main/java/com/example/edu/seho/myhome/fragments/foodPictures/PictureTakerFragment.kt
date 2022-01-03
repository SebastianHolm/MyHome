package com.example.edu.seho.myhome.fragments.foodPictures

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.edu.seho.myhome.R

class PictureTakerFragment : Fragment() {

    private lateinit var button: Button
    private lateinit var image : ImageView
    private lateinit var sharedPreferences : SharedPreferences

    private val args by navArgs<PictureTakerFragmentArgs>()

    private lateinit var pictureString : String
    private lateinit var pictureUriString : String

    private var pictureUri: Uri? = null

    private val pictureHelper = PictureHelper()

    // handles errors and successful attempts at taking pictures.
    private val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess){
            image.setImageURI(pictureUri)
        } else {
            // Removes the Uri string from the sharedPreferences since there is no picture there to display
            sharedPreferences.edit().apply{
                putString(pictureString, null)
            }.apply()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_picture_taker, container, false)

        image = view.findViewById(R.id.picture_image_view)
        button = view.findViewById(R.id.take_picture_button)

        // Gets location of possible existing picture and tries to load any pictures stored
        sharedPreferences = requireActivity().getSharedPreferences("FoodPictures", Context.MODE_PRIVATE)

        when (args.picture){
            "kyl" -> {
                pictureString = "kyl"
                pictureUriString = "kyl_image_file"
            }
            "frys" -> {
                pictureString = "frys"
                pictureUriString = "frys_image_file"
            }
            "skafferi" -> {
                pictureString = "skafferi"
                pictureUriString = "skafferi_image_file"
            }
            else -> {
                pictureString = "kryddor"
                pictureUriString = "kryddor_image_file"
            }
        }

        val uri = sharedPreferences.getString(pictureString, null)

        if (uri != null){
            pictureUri = Uri.parse(uri)
            image.setImageURI(pictureUri)
        }

        // Handles attempts at taking picture and saves the picture location in sharedPreferences
        button.setOnClickListener {
            if (pictureUri == null){
                pictureUri = pictureHelper.getFileUri(this.requireContext(),pictureUriString)
                sharedPreferences.edit().apply{
                    val tmp = pictureUri.toString()
                    putString(pictureString, tmp)
                }.apply()
            }
            takeImage()
        }

        return view
    }

    // Starts the inbuilt activity for taking pictures in android
    private fun takeImage() {
        lifecycleScope.launchWhenStarted {
            takeImageResult.launch(pictureUri)
        }
    }
}