package com.example.securityapp

import android.Manifest
import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.*;
import java.net.HttpURLConnection
import java.net.URL
import android.support.v4.app.ActivityCompat
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ToggleButton
import kotlinx.android.synthetic.main.esp_activity.*
import android.webkit.WebView




class ESPActivity  : AppCompatActivity() {

       val securityCamUrl="http:192.168.5.10/webcam";
       val initialScaleCamera=140;
       val layoutUsed=R.layout.esp_activity;

    override fun onCreate(savedInstanceState: Bundle? ) {

        super.onCreate(savedInstanceState)

        setContentView(layoutUsed)
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
        espCamView.setInitialScale(initialScaleCamera);
        espCamView.loadUrl(securityCamUrl)
        espCamView.getSettings().setDomStorageEnabled(true)
        espCamView.getSettings().setJavaScriptEnabled(true)
        espCamView.webViewClient=MyWebViewClient(this);
    }

    override fun onResume() {
        super.onResume()
        Log.i("Resuming","Present")
    }


    fun screenshotButton(){
        ScreenshotButton().takeScreenshot(this,"")

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
class MyWebViewClient internal constructor(private val activity: Activity) : WebViewClient() {
    override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
        Log.d("Webview client","Going to override the javascript")
        webView.loadUrl("javascript:"
                + "document.getElementById('controls').style.display='none';"
                + "document.getElementById('controls').style.display='none';"
                + "document.getElementsByTagName(\"TH\")[0].style.display='none';"
                + "document.getElementsByTagName(\"TH\")[1].style.display='none';")
        return false
    }

//    override fun onPageFinished(view:WebView, url:String) {
//
//view.loadUrl(
//    "javascript:"
//    + "document.getElementById('controls').style.display='none';"
//    + "document.getElementById('controls').style.display='none';"
//    + "document.getElementsByTagName(\"TH\")[0].style.display='none';"
//    + "document.getElementsByTagName(\"TH\")[1].style.display='none';"
//)
//
//}

}
/*
open class MainActivity : AppCompatActivity() {

    open val securityCamUrl="http://csr:motion12@192.168.1.12:8081/";
    open val layoutUsed=R.layout.activity_main;
    open val initialScaleCamera=140;
    open val webViewUsed=espCamView
    override fun onCreate(savedInstanceState: Bundle? ) {

        super.onCreate(savedInstanceState)

       setContentView(layoutUsed)
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
       espCamView.setInitialScale(initialScaleCamera);
       espCamView.loadUrl(securityCamUrl)
    }

    override fun onResume() {
        super.onResume()
        Log.i("Resuming","Present")
    }


    fun screenshotButton(){
        ScreenshotButton().takeScreenshot(this,"")

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

 */
