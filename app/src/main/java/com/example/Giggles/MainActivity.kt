package com.example.Giggles


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.TextView
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


class MainActivity : AppCompatActivity() {


    private lateinit var tvHome:TextView
    private lateinit var tvCommunity:TextView
    private lateinit var tvProfile:TextView
    private lateinit var tvCart:TextView
    private lateinit var tvSearch:TextView

    private lateinit var fabHome: FloatingActionButton
    private lateinit var fabCommunity:FloatingActionButton
    private lateinit var fabProfile:FloatingActionButton
    private lateinit var fabCart:FloatingActionButton
    private lateinit var fabSearch:FloatingActionButton

//    private lateinit var cvToy1:CardView
//    private lateinit var cvToy2:CardView
//    private lateinit var cvToy3:CardView
//    private lateinit var cvToy4:CardView
//    private lateinit var cvToy5:CardView
//    private lateinit var cvToy6:CardView
//
//    private lateinit var toyName1:TextView
//    private lateinit var toyName2:TextView
//    private lateinit var toyName3:TextView
//    private lateinit var toyName4:TextView
//    private lateinit var toyName5:TextView
//    private lateinit var toyName6:TextView
//
//    private lateinit var toyPrice1:TextView
//    private lateinit var toyPrice2:TextView
//    private lateinit var toyPrice3:TextView
//    private lateinit var toyPrice4:TextView
//    private lateinit var toyPrice5:TextView
//    private lateinit var toyPrice6:TextView

//    private lateinit var toyImgList:List<Int>
//    private lateinit var toyNameList:List<TextView>
//    private lateinit var toyPriceList:List<TextView>

    private lateinit var fabSell:FloatingActionButton

    private lateinit var toyList:MutableList<Toy2>


    private var selectedView = "Home"



    fun setView() = when(selectedView){
        "Home"-> {
            tvHome.setTextColor(ContextCompat.getColor(applicationContext,R.color.selection_toolbar))
            tvHome.setTypeface(tvHome.typeface, Typeface.BOLD)
            fabHome.setColorFilter(ContextCompat.getColor(applicationContext,R.color.selection_toolbar))
        }
        "Community"-> {
            tvCommunity.setTextColor(ContextCompat.getColor(applicationContext,R.color.selection_toolbar))
            tvCommunity.setTypeface(tvCommunity.typeface, Typeface.BOLD)
            fabCommunity.setColorFilter(ContextCompat.getColor(applicationContext,R.color.selection_toolbar))
        }
        "Profile"-> {
            tvProfile.setTextColor(ContextCompat.getColor(applicationContext,R.color.selection_toolbar))
            tvProfile.setTypeface(tvProfile.typeface, Typeface.BOLD)
            fabProfile.setColorFilter(ContextCompat.getColor(applicationContext,R.color.selection_toolbar))
        }
        "Cart"-> {
            tvCart.setTextColor(ContextCompat.getColor(applicationContext,R.color.selection_toolbar))
            tvCart.setTypeface(tvCart.typeface, Typeface.BOLD)
            fabCart.setColorFilter(ContextCompat.getColor(applicationContext,R.color.selection_toolbar))
        }
        "Search"-> {
            tvSearch.setTextColor(ContextCompat.getColor(applicationContext,R.color.selection_toolbar))
            tvSearch.setTypeface(tvSearch.typeface, Typeface.BOLD)
            fabSearch.setColorFilter(ContextCompat.getColor(applicationContext,R.color.selection_toolbar))
        }
        else->println("null")
    }

    fun disableView(){
        tvHome.setTextColor(ContextCompat.getColor(applicationContext,R.color.unselection_toolbar))
        tvHome.setTypeface(tvHome.typeface, Typeface.NORMAL)
        fabHome.setColorFilter(ContextCompat.getColor(applicationContext,R.color.unselection_toolbar))

        tvCommunity.setTextColor(ContextCompat.getColor(applicationContext,R.color.unselection_toolbar))
        tvCommunity.setTypeface(tvCommunity.typeface, Typeface.NORMAL)
        fabCommunity.setColorFilter(ContextCompat.getColor(applicationContext,R.color.unselection_toolbar))

        tvProfile.setTextColor(ContextCompat.getColor(applicationContext,R.color.unselection_toolbar))
        tvProfile.setTypeface(tvProfile.typeface, Typeface.NORMAL)
        fabProfile.setColorFilter(ContextCompat.getColor(applicationContext,R.color.unselection_toolbar))

        tvCart.setTextColor(ContextCompat.getColor(applicationContext,R.color.unselection_toolbar))
        tvCart.setTypeface(tvCart.typeface, Typeface.NORMAL)
        fabCart.setColorFilter(ContextCompat.getColor(applicationContext,R.color.unselection_toolbar))

        tvSearch.setTextColor(ContextCompat.getColor(applicationContext,R.color.unselection_toolbar))
        tvSearch.setTypeface(tvSearch.typeface, Typeface.NORMAL)
        fabSearch.setColorFilter(ContextCompat.getColor(applicationContext,R.color.unselection_toolbar))
    }

    fun getTargetActivity(view:String) = when (view){
        "Home" -> MainActivity::class.java
        "Community" -> CommunityActivity::class.java
        "Profile" -> ProfileActivity::class.java
        "Cart" -> CartActivity::class.java
        "Search"-> SearchActivity::class.java
        else -> MainActivity::class.java
    }

    fun initComponents(){
        tvHome = findViewById(R.id.tvHome)
        tvCommunity = findViewById(R.id.tvCommunity)
        tvProfile = findViewById(R.id.tvProfile)
        tvCart = findViewById(R.id.tvCart)
        tvSearch = findViewById(R.id.tvSearch)

        fabHome = findViewById(R.id.fabHome)
        fabCommunity = findViewById(R.id.fabCommunity)
        fabProfile = findViewById(R.id.fabProfile)
        fabCart = findViewById(R.id.fabCart)
        fabSearch = findViewById(R.id.fabSearch)

        toyList = mutableListOf()

        fabSell = findViewById(R.id.fabSell)




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
        val goIntent = Intent(this,Description::class.java)
        goIntent.putExtra("pos",num)
        goIntent.putExtra("toy_obj",toyList[num])
        startActivity(goIntent)
    }

    fun setClickListeners(){
        fabHome.setOnClickListener{bottomToolbarAction("Home")}
        fabCommunity.setOnClickListener{bottomToolbarAction("Community")}
        fabProfile.setOnClickListener{bottomToolbarAction("Profile")}
        fabCart.setOnClickListener{bottomToolbarAction("Cart")}
        fabSearch.setOnClickListener{bottomToolbarAction("Search")}

        tvHome.setOnClickListener{bottomToolbarAction("Home")}
        tvCommunity.setOnClickListener{bottomToolbarAction("Community")}
        tvProfile.setOnClickListener{bottomToolbarAction("Profile")}
        tvCart.setOnClickListener{bottomToolbarAction("Cart")}
        tvSearch.setOnClickListener{bottomToolbarAction("Search")}

    }


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()
        setView()
        setClickListeners()



//
//        toysRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val toys = mutableListOf<Toy2>()
//                for (toySnapshot in snapshot.children) {
//                    val toy = toySnapshot.getValue(Toy2::class.java)
//                    if (toy != null) {
//                        toys.add(toy)
//                    }
//                }
//                Log.i("myInfo", toys[0].toyid!!)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })

        var dbRef = FirebaseFirestore.getInstance().collection("toys")

        val query = dbRef.whereEqualTo("toyapproved", true)

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

//            Log.i("myInfo", "Successfully Retrieved Toys")
//            Log.i("myInfo",toyList.size.toString())
            val myRecyclerView = findViewById<RecyclerView>(R.id.myRV)
            myRecyclerView.layoutManager = GridLayoutManager(this, 2)
            myRecyclerView.adapter = MyRecyclerViewAdapter(toyList, ::toyAction)
        }

//        var options = FirestoreRecyclerOptions.Builder<Toy2>()
//            .setQuery(query,Toy2::class.java).build()


//        myRecyclerView.layoutManager = LinearLayoutManager(this)
//        myRecyclerView.adapter = Toy2Adapter(options, ::toyAction)

            fabSell.setOnClickListener {
            val intent = Intent(this,SellToyActivity::class.java)
            startActivity(intent)
        }




    }
}
