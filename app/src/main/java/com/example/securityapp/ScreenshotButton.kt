package com.example.securityapp

import java.util.*
import android.graphics.Bitmap
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import android.app.Activity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class ScreenshotButton {

    fun takeScreenshot(androidActivity: Activity){
        val unixTime = System.currentTimeMillis() / 1000L

        try {
            // image naming and path  to include sd card  appending name you choose for file
            val mPath = Environment.getExternalStorageDirectory().toString() + "/securityCameraPhotos/" + unixTime + ".jpg" //store in own folder

            // create bitmap screen capture
            val v1 = androidActivity.getWindow().getDecorView().getRootView().securityCamView
            v1.setDrawingCacheEnabled(true)
            val bitmap = Bitmap.createBitmap(v1.getDrawingCache())
            v1.setDrawingCacheEnabled(false)

            val imageFile = File(mPath)

            val outputStream = FileOutputStream(imageFile)
            val quality = 100 //100 is the maximum quality
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, outputStream)
            outputStream.flush()
            outputStream.close()
            Log.e("Correctly saved file",mPath)

        } catch (e: Throwable) {
            // Several error may come out with file handling or DOM
            Log.e("File saving error",e.toString())
        }


    }

}