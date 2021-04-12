package com.example.bean

import com.google.gson.annotations.SerializedName


data class StudentBean(
    @SerializedName("childId") var childId: Int,
    @SerializedName("age") var age: Int,
    @SerializedName("name") var name: String

)
