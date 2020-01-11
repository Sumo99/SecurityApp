package com.example.securityapp

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.*;
import android.graphics.*;
import java.net.HttpURLConnection
import java.net.URL
import android.support.v4.app.ActivityCompat
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ToggleButton
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val securityCamUrl="/";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       setContentView(R.layout.activity_main)
       ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1);
       //we need this to even save to the sd
        //   Id(R.id.recordButton) as ToggleButton;
        val recordButton=findViewById(R.id.recordButton) as ToggleButton;
        val screenshotButton=findViewById(R.id.screenshotButton) as Button;

       recordButton.setOnCheckedChangeListener { _, isChecked ->
           if (isChecked) {
               RecordButton().startRecording()
           } else {
               RecordButton().stopRecording()
           }
       }
       screenshotButton.setOnClickListener({
           screenshotButton()
       })
       securityCamView.setInitialScale(140);
       securityCamView.loadUrl(securityCamUrl)
       securityCamView.setWebViewClient(
           WebViewClient( )
       )

       val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x
        val height = size.y
      //  loadUrl();
        Log.e("Width", "" + width);
        Log.e("height", "" + height)
    }



    fun screenshotButton(){
        ScreenshotButton().takeScreenshot(this)
        Log.i("Button pressed","screenshot button")
    }

    fun loadUrl(){
        val url = URL("")
        val connection = url.openConnection() as HttpURLConnection
        val code = connection.getResponseCode();

        if (code == 200) {
            Log.i("URL","Success!");
        } else {
            Log.i("URL","Failure!");
        }

    }

}
