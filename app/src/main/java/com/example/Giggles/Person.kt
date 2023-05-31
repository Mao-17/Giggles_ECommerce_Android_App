package com.example.Giggles

data class Person(
    val uuid : String? ="null",
    val name: String? = "User",
    val rating: Double? = 5.0,
    val email: String? = "sampleuser@sampleuser333.com",
    val phno: Long = 9999999999,
    val admin: Boolean? = false,
    val img:String? = null,
    val passwordLength:Int? = 5,
    val address:String? = "null"
) : java.io.Serializable