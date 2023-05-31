package com.example.Giggles

data class Toy2(
    val toyid: String? = "999999",
    val toyname: String? = "sample",
    val toycategory: String? = "sample",
    val toyprice: Double? = 0.0,
    val toydescription: String? = "sample",
    val toyrating: Double? = 0.0,
    val toyseller: String? = "None",
    val toyuploaddate: String? = "07-02-2023",
    val toyapproved: Boolean? = true,
    val toyimg:String? = null,
    val toysold:Boolean? = false,
    val toyrejected:Boolean? = false
) : java.io.Serializable