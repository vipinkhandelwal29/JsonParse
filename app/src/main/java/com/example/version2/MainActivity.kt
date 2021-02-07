package com.example.version2

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
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
        var isAllChecked = false


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
                    if(jsonPrase.students.size == list.size){
                        // toggle to all checked
                        binding.btnSelectAll.setBackgroundColor(Color.GRAY)
                        //type cast
                        binding.btnSelectAll.text = resources.getText(R.string.unselect_all)
                        isAllChecked = true
                    }
                }
                else {
                    if(isAllChecked){
                        // toggle to Uncheck
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            binding.btnSelectAll.setBackgroundColor(resources.getColor(R.color.teal_200,theme))
                        }
                        binding.btnSelectAll.text = resources.getText(R.string.select_all)
                        isAllChecked = false
                    }
                    list.remove(rawLayout.root)
                }
            }

            //binding.linear.addView(rawLayout.root)
        }


        binding.btnSelectAll.setOnClickListener { it ->
            if(!isAllChecked) {
                // toggle to all checked
                binding.linear.forEach {
                    val checkBox: CheckBox = it.findViewById(R.id.checkBox)
                    checkBox.isChecked = true
                }
                it.setBackgroundColor(Color.GRAY)
                //type cast
                (it as AppCompatButton).text = resources.getText(R.string.unselect_all)
                isAllChecked = true
            } else {
                // toggle to all Uncheck
                binding.linear.forEach {
                    val checkBox: CheckBox = it.findViewById(R.id.checkBox)
                    if(checkBox.isChecked) checkBox.isChecked = false
                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    binding.btnSelectAll.setBackgroundColor(resources.getColor(R.color.teal_200, theme))
                }
                (it as AppCompatButton).text = resources.getText(R.string.select_all)
                isAllChecked = false
            }
        }

        binding.btnDelete.setOnClickListener{
            list.forEach{
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