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


class Description : AppCompatActivity() {
    var imageView: ImageView? = null
    var heart: ImageView? = null
    var cost: TextView? = null
    var type: TextView? = null
    var description: TextView? = null
//    var rating: TextView? = null
    var add_to_bag: Button? = null
    var ratingBar: RatingBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        var toy = intent.getSerializableExtra("toy_obj") as Toy2
        val decodedString: ByteArray = Base64.decode(toy.toyimg, Base64.URL_SAFE)
        val decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        var a = toy.toyprice?.toInt().toString()
        var b = toy.toyname
//        Log.i("myInfo",a.toString())
        var pos = toy.toyid?.toLong()
//        var pos = intent.getIntExtra("pos",0)
//        val img = intent.getIntExtra("image",0)
//        val a = intent.getStringExtra("cost")
//        val b = intent.getStringExtra("type")


        imageView = findViewById(R.id.imageView2)
        type = findViewById(R.id.textView2)
        cost = findViewById(R.id.textView3)
//        rating = findViewById(R.id.textView7)
        imageView?.setImageBitmap(decodedBitmap)
        type?.text = b
        cost?.text = "₹$a"


        description = findViewById(R.id.toyDesc)
        //        description.setText("good condition, \ngood product");
        description?.setText(
            toy.toydescription?.trimIndent()
        )
        ratingBar = findViewById<RatingBar>(R.id.ratingBar)

        ratingBar?.rating = toy.toyrating!!.toFloat()
        ratingBar?.setIsIndicator(true)
//        val myDraw = ratingBar?.progressDrawable
//        myDraw?.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP)
        val f = ratingBar?.getRating()
//        rating?.setText(f?.toString())
        add_to_bag = findViewById<Button>(R.id.bag)
        add_to_bag?.setOnClickListener{ //                Intent intent = new Intent(Description.this,nextclass);
////                intent.putExtra("type", type.getText().toString());
//                startActivity(intent);


            val db: AppDatabase =
                Room.databaseBuilder(getApplicationContext(), AppDatabase::class.java, "cart_db")
                    .allowMainThreadQueries().build()
            val productDao: ProductDao? = db.ProductDao()
            val check = productDao?.is_exist(pos!!)
            if (check == false) {
                val pname: String? = b
                val priceInt = a?.substring(0)
                val price: Int = priceInt!!.toInt()
                val qnt: Int = 1
                productDao.insertrecord(Product(pos!!, pname!!, price, qnt))
            }else{
                val pname: String? = b
                val priceInt = a?.substring(0)
                val price: Int = priceInt!!.toInt()
                val qnt: Int? = productDao?.getQuantity(pos!!)
                productDao?.updateRecord((Product(pos!!,pname!!,price,qnt!!+1)))
            }
            Toast.makeText(this@Description, "Added to Cart", Toast.LENGTH_SHORT).show()
        }
        val drawable = ContextCompat.getDrawable(this, R.drawable.heart_filled)
        val drawable2 = ContextCompat.getDrawable(this, R.drawable.heart)
        heart = findViewById<ImageView>(R.id.imageView4)
        heart?.setOnClickListener(View.OnClickListener {
            val d = heart?.getDrawable()
            if (d == drawable) {
                heart?.setImageDrawable(drawable2)
            } else {
                heart?.setImageDrawable(drawable)
            }
        })
    }
}