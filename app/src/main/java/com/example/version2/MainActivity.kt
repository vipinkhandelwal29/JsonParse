package com.example.version2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.bean.SerializedBean
import com.example.version2.databinding.ActivityMainBinding
import com.example.version2.databinding.ConstraintLayoutBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data1 = readFromAsset()
        val jsonPrase = fromJson(data1)
        Log.d("onCreate: ", "$jsonPrase")

        /*val jsonPrase1 = toGson(data1)
        Log.d("onCreate","${jsonPrase1}")

        val binding = setContentView<ActivityMainBinding>(this,R.id.layout)*/

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        //val getData = jsonPrase.students

        jsonPrase.students.forEach { data ->
            val rawLayout = ConstraintLayoutBinding.inflate(layoutInflater)
            rawLayout.students = data
            binding.linear.addView(rawLayout.root)
            rawLayout.age.setOnClickListener {
                binding.linear.removeView(rawLayout.root)

            }
            // binding.linear.removeView(rawLayout.root)

        }


    }

    private fun toGson(data: String): String {
        val response = Gson().toJson(data)
        return response
    }

    private fun fromJson(data: String): SerializedBean {
        val response = Gson().fromJson(data, SerializedBean::class.java)
        return response
    }

    private fun readFromAsset(): String {
        val filename = "student.json"
        val bufferReader = application.assets.open(filename).bufferedReader()
        val data = bufferReader.use { it.readText() }
        Log.d("readFromAsset: ", data)
        return data

    }
}