package com.example.Giggles


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
//import com.google.firebase.appcheck.FirebaseAppCheck
//import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.firestore.Query


class AdminActivity : AppCompatActivity() {


    private lateinit var tvProfile:TextView

    private lateinit var fabProfile:FloatingActionButton

    private lateinit var toyList:MutableList<Toy2>

    private var selectedView = "Home"



    fun setView() = when(selectedView){
        "Profile"-> {
            tvProfile.setTextColor(ContextCompat.getColor(applicationContext,R.color.selection_toolbar))
            tvProfile.setTypeface(tvProfile.typeface, Typeface.BOLD)
            fabProfile.setColorFilter(ContextCompat.getColor(applicationContext,R.color.selection_toolbar))
        }
        else->println("null")
    }

    fun disableView(){

        tvProfile.setTextColor(ContextCompat.getColor(applicationContext,R.color.unselection_toolbar))
        tvProfile.setTypeface(tvProfile.typeface, Typeface.NORMAL)
        fabProfile.setColorFilter(ContextCompat.getColor(applicationContext,R.color.unselection_toolbar))

    }

    fun getTargetActivity(view:String) = when (view){

        "Profile" -> ProfileActivity2::class.java

        else -> MainActivity::class.java
    }

    fun initComponents(){

        tvProfile = findViewById(R.id.tvProfile2)

        fabProfile = findViewById(R.id.fabProfile2)


        toyList = mutableListOf()






    }

    fun bottomToolbarAction(view:String){
        if (selectedView == view && selectedView=="Home") return
        selectedView = view
        disableView()
        setView()
        var targetActivity = getTargetActivity(view)
        val intent = Intent(this,targetActivity)
//            intent.putExtra("selectedView",selectedView)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun toyAction(num:Int){
        val goIntent = Intent(this,Description2::class.java)
        goIntent.putExtra("pos",num)
        goIntent.putExtra("toy_obj",toyList[num])
        startActivity(goIntent)
    }

    fun setClickListeners(){

        fabProfile.setOnClickListener{bottomToolbarAction("Profile")}

        tvProfile.setOnClickListener{bottomToolbarAction("Profile")}

    }


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        initComponents()
        setView()
        setClickListeners()

        var dbRef = FirebaseFirestore.getInstance().collection("toys")

        val query = dbRef.whereEqualTo("toyapproved", false)

        val listenerRegistration = query.addSnapshotListener { snapshot, e ->
            if (e != null) {
                // Handle any errors that occur
                return@addSnapshotListener
            }

            toyList = mutableListOf()
            for (document in snapshot!!.documents) {
                val toy = document.toObject(Toy2::class.java)
                if (toy != null) {
                    toyList.add(toy)
                }
            }

            Log.i("myInfo", "Successfully Retrieved Toys")
            Log.i("myInfo",toyList.size.toString())
            val myRecyclerView = findViewById<RecyclerView>(R.id.myRV)
            myRecyclerView.layoutManager = GridLayoutManager(this, 2)
            myRecyclerView.adapter = MyRecyclerViewAdapter(toyList, ::toyAction)
        }



//        var dbRef = FirebaseFirestore.getInstance().collection("toys")
//
//        val listenerRegistration = dbRef.addSnapshotListener { snapshot, e ->
//            if (e != null) {
//                // Handle any errors that occur
//                return@addSnapshotListener
//            }
//        }
//
//
//            dbRef.get().addOnSuccessListener {
//            for (document in it.documents){
//                val toy = document.toObject(Toy2::class.java)
//                if (toy!=null && !toy.toyapproved!!) toyList.add(toy)
//            }
//            Log.i("myInfo","Successfully Retrieved Toys")
//
////            Log.i("myInfo",toyList.size.toString())
//            val myRecyclerView = findViewById<RecyclerView>(R.id.myRV)
//            myRecyclerView.layoutManager = GridLayoutManager(this,2)
//            myRecyclerView.adapter = MyRecyclerViewAdapter(toyList, ::toyAction)
//
//        }.addOnFailureListener{
//            Log.i("myInfo","Failed Getting Toys")
//        }



    }
}
