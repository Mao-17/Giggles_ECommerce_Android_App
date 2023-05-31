package com.example.Giggles

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CommunityActivity : AppCompatActivity() {


    private lateinit var tvHome: TextView
    private lateinit var tvCommunity: TextView
    private lateinit var tvProfile: TextView
    private lateinit var tvCart: TextView
    private lateinit var tvSearch: TextView
    private lateinit var fabHome: FloatingActionButton
    private lateinit var fabCommunity: FloatingActionButton
    private lateinit var fabProfile: FloatingActionButton
    private lateinit var fabCart: FloatingActionButton
    private lateinit var fabSearch: FloatingActionButton
    private var selectedView = "Community"


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

    }

    fun bottomToolbarAction(view:String){
        selectedView = view
        disableView()
        setView()
        var targetActivity = getTargetActivity(view)
        val intent = Intent(this,targetActivity)
//            intent.putExtra("selectedView",selectedView)
        startActivity(intent)
        if (view=="Home"){
            overridePendingTransition(R.anim.slide_out_left2, R.anim.slide_in_right2)
        }else overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        initComponents()
        setView()
        setClickListeners()

    }
}