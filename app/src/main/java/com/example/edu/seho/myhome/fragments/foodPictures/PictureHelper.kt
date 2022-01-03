package com.example.edu.seho.myhome.fragments.foodPictures

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.example.edu.seho.myhome.BuildConfig
import java.io.File

/** @author Sebastian Holm
 * Helper class for fragments that needs to read pictures from a
 * specific location. This class generates the Uri or path to the location
 * where the pictures will be stored.
 */

class PictureHelper {

    // Generates a file and adds a given string to the name
    fun getFileUri(context : Context, s : String): Uri {
        val tmpFile = File.createTempFile(s, ".png").apply {
            createNewFile()
            deleteOnExit()
        }

        return FileProvider.getUriForFile(context, "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
    }
}