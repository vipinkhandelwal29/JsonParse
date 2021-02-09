package com.example.version2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class ActivityCallStatus : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_status)
        Log.d("lifecycle", "onCreate: invoked")

    }
    override fun onStart() {
        super.onStart()
        Log.d("lifecycle", "onStart: invoked ")
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifecycle", "onResume: invoked")
    }

    override fun onPause() {
        super.onPause()
        Log.d("lifecycle", "onPause: invoked")
    }

   override fun onStop() {
        super.onStop()
        Log.d("lifecycle", "onStop: invoked")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("lifecycle", "onDestroy: invoked")
    }
}