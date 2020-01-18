package com.example.securityapp

import android.support.v4.content.ContextCompat.getSystemService
import android.util.Log


class RecordButton {
   // private val  mMediaProjectionManager= getSystemService()

    fun startRecording(){
        //TODO: record video by using an application
        Log.i("Button pressed","Starting the recording button")
    }
    fun stopRecording(){
        Log.i("Button pressed","Stopping the recording button")
    }
}