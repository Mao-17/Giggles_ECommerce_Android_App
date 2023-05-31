package com.example.Giggles

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MyRecyclerViewAdapter(private val toyList:List<Toy2>, private val clickListener:(Int)-> Unit ): RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.list_item_3,parent,false)
        return MyViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return toyList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(toyList[position],clickListener,position)
    }
}

class MyViewHolder(private val view:View):RecyclerView.ViewHolder(view){
    private lateinit var toyName : TextView
    private lateinit var toyPrice : TextView
    private lateinit var toyImg : ImageView

    fun bind(toy:Toy2,clickListener: (Int) -> Unit, position: Int){
        toyName = view.findViewById(R.id.toyName1)
        toyPrice = view.findViewById(R.id.toyPrice1)
        toyImg = view.findViewById(R.id.toyImg1)

        if (toy.toyimg!=null){
            val decodedString: ByteArray = Base64.decode(toy.toyimg, Base64.URL_SAFE)
            val decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            toyImg.setImageBitmap(decodedBitmap)
        }


        toyName.text = toy.toyname
        toyPrice.text = "₹" + toy.toyprice.toString().toDouble().toInt().toString()



        view.setOnClickListener {
            clickListener(position)
        }
    }
}