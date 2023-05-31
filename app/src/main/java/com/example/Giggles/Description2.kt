package com.example.Giggles

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore


class Description2 : AppCompatActivity() {
    var imageView: ImageView? = null
    var heart: ImageView? = null
    var cost: TextView? = null
    var type: TextView? = null
    var description: TextView? = null
    //    var rating: TextView? = null
    var btnReject: Button? = null
    var btnApprove:Button? = null
    var ratingBar: RatingBar? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description2)

        var toy = intent.getSerializableExtra("toy_obj") as Toy2
        val decodedString: ByteArray = Base64.decode(toy.toyimg, Base64.URL_SAFE)
        val decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        var a = toy.toyprice?.toInt().toString()
        var b = toy.toyname

//        var pos = toy.toyid?.to()


        imageView = findViewById(R.id.imageView2)
        type = findViewById(R.id.textView2)
        cost = findViewById(R.id.textView3)
//        rating = findViewById(R.id.textView7)
        imageView?.setImageBitmap(decodedBitmap)
        type?.text = b
        cost?.text = "₹$a"
        description = findViewById(R.id.toyDesc)

        description?.setText(
            toy.toydescription?.trimIndent()
        )
        ratingBar = findViewById(R.id.ratingBar)

        ratingBar?.rating = toy.toyrating!!.toFloat()
        ratingBar?.setIsIndicator(true)

        val f = ratingBar?.getRating()

        btnApprove = findViewById(R.id.btnApprove)
        btnReject = findViewById(R.id.btnReject)


        btnApprove?.setOnClickListener{

            val myUID = toy.toyid

            val db = FirebaseFirestore.getInstance()
            val productsRef = db.collection("toys")


            val query = productsRef.whereEqualTo("toyid", myUID)
            query.get().addOnSuccessListener { documents ->
                for (doc in documents) {
                    val productRef = productsRef.document(doc.id)
                    productRef.update("toyapproved", true).addOnSuccessListener {
                        // Handle success
                        Toast.makeText(this@Description2, "Toy Approved", Toast.LENGTH_SHORT).show()

                    }.addOnFailureListener { e ->
                        Toast.makeText(this@Description2, "Toy Approved Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }.addOnFailureListener { e ->
                Toast.makeText(this@Description2, "Failure in Retrieval", Toast.LENGTH_SHORT).show()
            }

        }


    }
}