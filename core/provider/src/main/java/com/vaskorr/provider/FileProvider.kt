package com.vaskorr.provider

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FileProvider : FileProvider(
    R.xml.file_paths_gallery
) {
    companion object {
        private const val IMAGE_DIRECTORY_NAME = "images"
        private const val FILE_NAME_FORMAT = "yyyyMMdd_HHmmss"
        private const val FILE_SUFFIX = ".jpg"
        private const val FILE_PROVIDER = ".fileprovider"
        fun getImageUri(context: Context): Uri {
            val timeStamp = SimpleDateFormat(FILE_NAME_FORMAT, Locale.getDefault()).format(Date())
            val directory = File(context.cacheDir, IMAGE_DIRECTORY_NAME)
            directory.mkdirs()
            val file = File.createTempFile(
                timeStamp,
                FILE_SUFFIX,
                directory
            )
            val authority = context.packageName + FILE_PROVIDER
            return getUriForFile(
                context,
                authority,
                file,
            )
        }
    }
}