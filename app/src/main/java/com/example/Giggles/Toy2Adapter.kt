package com.example.Giggles
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.View.OnClickListener
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import android.widget.Toast
//import androidx.recyclerview.widget.RecyclerView
//import com.firebase.ui.firestore.FirestoreRecyclerAdapter
//import com.firebase.ui.firestore.FirestoreRecyclerOptions
//import com.google.firebase.database.core.Context
//
//class Toy2Adapter(private val options: FirestoreRecyclerOptions<Toy2>, private val clickListener: (Int)->Unit) :
//    FirestoreRecyclerAdapter<Toy2, Toy2ViewHolder>(options) {
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):Toy2ViewHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        val listItem = layoutInflater.inflate(R.layout.list_item_toy2,parent,false)
//        return Toy2ViewHolder(listItem)
//    }
//
//    override fun getItemCount(): Int {
//        return 10
//    }
//
//
//
//    override fun onBindViewHolder(holder: Toy2ViewHolder, position: Int, model: Toy2) {
//        holder.bind(model,clickListener)
//    }
//}
//
//class Toy2ViewHolder(private val view:View):RecyclerView.ViewHolder(view){
//    private lateinit var toyName : TextView
//    private lateinit var toyPrice : TextView
//    private lateinit var toyImg : ImageView
//
//    fun bind(toy:Toy2,clickListener: (Int) -> Unit){
//        toyName = view.findViewById(R.id.toyName1)
//        toyPrice = view.findViewById(R.id.toyPrice1)
//        toyImg = view.findViewById(R.id.toyImg1)
//
//        toyName.text = toy.toyname
//        toyPrice.text = toy.toyprice.toString()
////        toyImg.setImageBitmap(toy.)
//
//
//        view.setOnClickListener {
//            clickListener(toy.toyid!!.toInt())
//        }
//    }
//}