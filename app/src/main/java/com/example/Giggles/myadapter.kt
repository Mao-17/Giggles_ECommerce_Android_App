package com.example.Giggles

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room.databaseBuilder
import com.example.Giggles.myadapter.myviewholder

class myadapter(val products: MutableList<Product?>?, var rateview: TextView?) :
    RecyclerView.Adapter<myviewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.singlerowdesign, parent, false)
        return myviewholder(view)
    }

    override fun onBindViewHolder(
        holder: myviewholder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.recid.text = ((products!![position]?.pid)).toString()
        holder.recpname.text = products[position]?.pname
        holder.recpprice.text = products[position]?.price.toString()
        holder.recqnt.text = products[position]?.qnt.toString()
        holder.delbtn.setOnClickListener {
            val db = databaseBuilder(
                holder.recid.context,
                AppDatabase::class.java, "cart_db"
            ).allowMainThreadQueries().build()
            val productDao = db.ProductDao()
            productDao!!.deleteById(products[position]!!.pid)
            products.removeAt(position)
            notifyItemRemoved(position)
            updateprice()
        }

//        holder.incr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int qnt=products.get(position).getQnt();
//                qnt++;
//                products.get(position).setQnt(qnt);
//                notifyDataSetChanged();
//                updateprice();
//            }
//        });
//        holder.decr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int qnt=products.get(position).getQnt();
//                qnt--;
//                products.get(position).setQnt(qnt);
//                notifyDataSetChanged();
//                updateprice();
//            }
//        });
    }

    override fun getItemCount(): Int {
        Log.i("myInfo",products!!.size.toString())
        return products!!.size
    }

    inner class myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var recid: TextView
        var recpname: TextView
        var recqnt: TextView
        var recpprice: TextView
        var delbtn: ImageButton
        var incr: ImageView? = null
        var decr: ImageView? = null

        init {
            recid = itemView.findViewById(R.id.recid)
            recpname = itemView.findViewById(R.id.recpname)
            recpprice = itemView.findViewById(R.id.recpprice)
            recqnt = itemView.findViewById(R.id.recqnt)
            delbtn = itemView.findViewById(R.id.delbtn)
            //            incr=itemView.findViewById(R.id.incbtn);
//            decr=itemView.findViewById(R.id.decbtn);
        }
    }

    fun updateprice() {
        var sum = 0
        var i: Int
        i = 0
        while (i < products!!.size) {
            sum = sum + products[i]!!.price * products[i]!!.qnt
            i++
        }
        rateview?.text = "Total Amount : INR $sum"
    }
}