package com.example.securityapp

import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import kotlinx.android.synthetic.main.activity_main.view.*


class ScreenshotButton {


    fun takeScreenshot(androidActivity: Activity, s: String){
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
            alertUser(androidActivity);
        }


    }

    private fun alertUser(androidActivity: Activity) {
        AlertDialog.Builder(androidActivity)
            .setTitle("Could Not Save File")
            .setMessage("Try again?")

            // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                takeScreenshot(androidActivity,""); //go for another spin
            })

            // A null listener allows the button to dismiss the dialog and take no further action.
            .setNegativeButton("No", null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

}