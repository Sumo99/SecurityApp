package com.example.securityapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.*;
import android.support.v4.app.ActivityCompat
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ToggleButton
import kotlinx.android.synthetic.main.esp_activity.*
import android.webkit.WebView
import android.widget.SeekBar
import android.widget.Toast
import android.view.Menu
import android.view.MenuItem
import okhttp3.*
import java.io.IOException


class ESPActivity  : AppCompatActivity() {

       val securityCamUrl="http:192.168.5.10/webcam";
       val initialScaleCamera=140;
       val layoutUsed=R.layout.esp_activity;
       val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle? ) {

        super.onCreate(savedInstanceState)

        setContentView(layoutUsed)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1);
        setSupportActionBar(findViewById(R.id.esp_toolbar))

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
        val motorDegree = findViewById<SeekBar>(R.id.motorDegreeBar)

        motorDegree?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
                moveMotor(seek.progress)
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater=getMenuInflater()
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.home -> {
            val drive = Intent(this,home::class.java);
            startActivity(drive);
            true
        }

        R.id.ESPcamera -> {
            val drive = Intent(this,ESPActivity::class.java);
            startActivity(drive);
            true
        }
        R.id.drivewayCamera -> {
            val drive = Intent(this,this::class.java);
            startActivity(drive);
            true
        }

        R.id.exit -> {
            this.finishAffinity();
            true
        }
        R.id.refresh -> {
            espCamView.reload()
            Toast.makeText(this,"Refreshing!",Toast.LENGTH_SHORT).show()
            true
        }


        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    fun moveMotor(degrees:Int) {
        Log.i("Moving motor","$degrees");
        val request = Request.Builder()
            .url("http://192.168.5.10/params?hor=$degrees")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    for ((name, value) in response.headers) {
                        println("$name: $value")
                    }

                    println(response.body!!.string())
                }
            }
        })
    }



    fun screenshotButton(){
        ScreenshotButton().takeScreenshot(this,espCamView)

        Log.i("Button pressed","screenshot button")
    }



}
class MyWebViewClient constructor(private val activity: Activity) : WebViewClient() {
    override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
        Log.d("Webview client", "Going to override the javascript")
        webView.loadUrl(
            "javascript:"
                    + "document.getElementById('controls').style.display='none';"
                    + "document.getElementById('controls').style.display='none';"
                    + "document.getElementsByTagName(\"TH\")[0].style.display='none';"
                    + "document.getElementsByTagName(\"TH\")[1].style.display='none';"
        )
        return true
    }

    override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        view.loadUrl(
            "javascript:(function() { " + "document.getElementById('controls').style.display='none';"+
                    "document.getElementById('controls').style.display='none';" +
                    "document.getElementsByTagName(\"TH\")[0].style.display='none';"+
                    "document.getElementsByTagName(\"TH\")[1].style.display='none';" + "})()")
        Log.d("Page has begun loading", "Going to override the javascript")

    }
}






