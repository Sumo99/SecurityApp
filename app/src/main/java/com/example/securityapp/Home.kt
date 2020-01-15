package com.example.securityapp

import android.content.Intent;  
import android.os.Bundle;  
import android.support.v7.app.AppCompatActivity;  
import android.widget.Button;
import kotlinx.android.synthetic.main.activity_home.*;

class home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
	val drivewayCamera=findViewById(R.id.drivewayCamera) as Button;
        val ESPcamera=findViewById(R.id.ESPcamera) as Button;
	drivewayCamera.setOnClickListener {
        val drive =Intent(this,MainActivity::class.java);
        startActivity(drive);
    }
        ESPcamera.setOnClickListener({
	  val studyRoom=Intent(this,ESPActivity::class.java);  
          startActivity(studyRoom);  
       })
        securityCamView.setInitialScale(140);
        securityCamView.loadUrl("http://csr:motion12@192.168.1.12:8081/")
       espCameraView.setInitialScale(140);
       espCameraView.loadUrl("http://192.168.5.10/motion")
    }
}
