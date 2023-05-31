package com.example.Giggles
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Item {

    private val database  = FirebaseDatabase.getInstance().reference


    fun createToyTable(){
        val toyTable = database.child("toy")

        val toy1 = HashMap<String, Any>()
        toy1["toyid"] = 1
        toy1["toyname"] = "Superman Action Figure"
        toy1["toycategory"] = "Action Figures"
        toy1["toyrating"] = 4.5
        toy1["toyprice"] = 9.99
        toy1["toyseller"] = "Toy Store"
        toy1["toydescription"] = "A highly detailed Superman action figure, complete with cape and accessories."
        toy1["toyuploaddate"] = "2023-05-07"
        toy1["toyapproved"] = true
        toyTable.child("1").setValue(toy1)

        val toy2 = HashMap<String, Any>()
        toy2["toyid"] = 2
        toy2["toyname"] = "Barbie Dreamhouse"
        toy2["toycategory"] = "Dolls and Accessories"
        toy2["toyrating"] = 4.2
        toy2["toyprice"] = 99.99
        toy2["toyseller"] = "Toy Store"
        toy2["toydescription"] = "A luxurious Barbie Dreamhouse with multiple levels and interactive features."
        toy2["toyuploaddate"] = "2023-05-07"
        toy2["toyapproved"] = true
        toyTable.child("2").setValue(toy2)


    }
}