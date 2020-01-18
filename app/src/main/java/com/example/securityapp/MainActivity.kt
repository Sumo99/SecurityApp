package com.example.securityapp

import android.Manifest
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.*;
import android.support.v4.app.ActivityCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import android.widget.ToggleButton
import kotlinx.android.synthetic.main.activity_main.*


open class MainActivity : AppCompatActivity() {

    open val securityCamUrl="http://csr:motion12@192.168.1.12:8081/";
    open val layoutUsed=R.layout.activity_main;
    open val initialScaleCamera=140;

    override fun onCreate(savedInstanceState: Bundle? ) {

        super.onCreate(savedInstanceState)

        setContentView(layoutUsed)
        setSupportActionBar(findViewById(R.id.my_toolbar))

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
        securityCamView.setInitialScale(initialScaleCamera);
        securityCamView.loadUrl(securityCamUrl)
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
            securityCamView.reload()
            Toast.makeText(this,"Refreshing!",Toast.LENGTH_SHORT).show()
            true
        }


        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i("Resuming","Present")
    }


    fun screenshotButton(){
        ScreenshotButton().takeScreenshot(this,securityCamView)

        Log.i("Button pressed","screenshot button")
    }



}
