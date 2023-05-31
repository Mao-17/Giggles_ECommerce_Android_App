package com.example.Giggles

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.query
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

class SearchActivity : AppCompatActivity() {
    private var mSearchEditText: EditText? = null
    private var mSearchButton: Button? = null
    private var mProgressBar: ProgressBar? = null
    private var mNoResultsTextView: TextView? = null



    private lateinit var tvHome:TextView
    private lateinit var tvCommunity:TextView
    private lateinit var tvProfile:TextView
    private lateinit var tvCart:TextView
    private lateinit var tvSearch:TextView

    private lateinit var fabHome: FloatingActionButton
    private lateinit var fabCommunity: FloatingActionButton
    private lateinit var fabProfile: FloatingActionButton
    private lateinit var fabCart: FloatingActionButton
    private lateinit var fabSearch: FloatingActionButton


    private lateinit var fabSell: FloatingActionButton

    private lateinit var toyList:MutableList<Toy2>


    private var selectedView = "Search"



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
        if (view=="Home" || view=="Community" || view=="Profile" || view=="Cart"){
            overridePendingTransition(R.anim.slide_out_left2, R.anim.slide_in_right2)
            Log.i("myInfo","Going to Home")
        }else overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
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
        setContentView(R.layout.activity_search)

        initComponents()
        setView()
        setClickListeners()

        mSearchEditText = findViewById(R.id.search_edit_text)
        mSearchButton = findViewById(R.id.search_button)

        mProgressBar = findViewById(R.id.progress_bar)
        mNoResultsTextView = findViewById(R.id.no_results_text_view)

        mSearchButton?.setOnClickListener(View.OnClickListener {
            val queryText = mSearchEditText?.getText().toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(queryText)) {
                mSearchEditText?.setError("Search query is required.")
                mSearchEditText?.requestFocus()
                return@OnClickListener
            }
            mProgressBar?.setVisibility(View.VISIBLE)
            mNoResultsTextView?.setVisibility(View.GONE)

            var dbRef = FirebaseFirestore.getInstance().collection("toys")

            val query = dbRef
                .whereGreaterThanOrEqualTo("toyname", queryText)
                .whereLessThanOrEqualTo("toyname", queryText + "\uf8ff")

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
                        Log.i("myInfo","m"+toy.toyname.toString())
                    }
                }

                val myRecyclerView = findViewById<RecyclerView>(R.id.myRV)
                myRecyclerView.layoutManager = GridLayoutManager(this, 2)
                myRecyclerView.adapter = MyRecyclerViewAdapter(toyList, ::toyAction)
                mProgressBar?.setVisibility(View.GONE)
                if (toyList.size==0) mNoResultsTextView?.setVisibility(View.VISIBLE)
            }


        })


        fabSell.setOnClickListener {
            val intent = Intent(this,SellToyActivity::class.java)
            startActivity(intent)
        }




    }





}