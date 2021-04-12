package com.example.version2

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
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



        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)


        val list = ArrayList<View>()


        jsonPrase.students.forEach { data ->
            val rawLayout = ConstraintLayoutBinding.inflate(layoutInflater)
            rawLayout.students = data
            binding.linear.addView(rawLayout.root)

            rawLayout.age.setOnClickListener {
                binding.linear.removeView(rawLayout.root)
            }


            rawLayout.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->

                if (isChecked) {
                    list.add(rawLayout.root)
                    if (jsonPrase.students.size == list.size) {
                        // toggle to all checked
                        binding.btnSelectAll.setBackgroundColor(Color.GRAY)
                        //type cast
                        binding.btnSelectAll.text = resources.getText(R.string.un_select_all)
                    }
                } else {

                    list.remove(rawLayout.root)

                    // toggle to Uncheck
                    binding.btnSelectAll.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.teal_200
                        )
                    )

                    binding.btnSelectAll.text = resources.getText(R.string.select_all)

                }
            }

            //binding.linear.addView(rawLayout.root)
        }


        binding.btnSelectAll.setOnClickListener { it ->

            val isSelectedAll = jsonPrase.students.size != list.size
            binding.linear.forEach {
                it.findViewById<CheckBox>(R.id.checkBox)?.isChecked = isSelectedAll
            }/*
            binding.btnSelectAll.setBackgroundColor(if(isSelectedAll)Color.GRAY else ContextCompat.getColor(
                    this,
                R.color.teal_200
            ))
            binding.btnSelectAll.text =if(isSelectedAll) resources.getText(R.string.unselect_all) else resources.getText(R.string.select_all)*/
        }

        binding.btnDelete.setOnClickListener {
            list.forEach {
                binding.linear.removeView(it)
            }

        }


    }

    /*   private fun toGson(data: String): String {
           return Gson().toJson(data)
       }*/

    private fun fromJson(data: String): SerializedBean {
        return Gson().fromJson(data, SerializedBean::class.java)
    }

    private fun readFromAsset(): String {
        val filename = "student.json"
        val bufferReader = application.assets.open(filename).bufferedReader()
        val data = bufferReader.use { it.readText() }
        Log.d("readFromAsset: ", data)
        return data

    }
}