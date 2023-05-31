package com.example.Giggles

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore


class ProfileActivity : AppCompatActivity() {


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
    private var selectedView = "Profile"


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
        if (view=="Home" || view=="Community"){
            overridePendingTransition(R.anim.slide_out_left2, R.anim.slide_in_right2)
            Log.i("myInfo","Going to Home")
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


    var full_name: TextView? = null
    var email: TextView? = null
    var contact: TextView? = null
    var password: TextView? = null
    var editProfile: TextView? = null
    var changePassword: TextView? = null
    var logout: TextView? = null
    var Name: String? = null
    var Email: String? = null
    var Contact: String? = null
    var Password: String? = null
    var imgProfile:ImageView?=null
    private var authProfile: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        initComponents()
        setView()
        setClickListeners()

        full_name = findViewById<TextView>(R.id.full_name)
        email = findViewById<TextView>(R.id.email)
        contact = findViewById<TextView>(R.id.contact)
        password = findViewById<TextView>(R.id.password)
        editProfile = findViewById<TextView>(R.id.editProfile)
        changePassword = findViewById<TextView>(R.id.changePassword)
        logout = findViewById<TextView>(R.id.logout)
        authProfile = FirebaseAuth.getInstance()
        val firebaseUser = authProfile!!.currentUser
        if (firebaseUser == null) {
            Toast.makeText(this, "User's details not available at the moment", Toast.LENGTH_SHORT)
                .show()
        } else {
            checkifemailVerified(firebaseUser)
            showUserProfile(firebaseUser)
        }
        editProfile?.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@ProfileActivity, ProfileActivity::class.java)
            startActivity(intent)
        })
        logout?.setOnClickListener(View.OnClickListener {
            authProfile!!.signOut()
            Toast.makeText(this@ProfileActivity, "Logged Out", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
            startActivity(intent)
        })
        changePassword?.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@ProfileActivity, ProfileActivity::class.java)
            startActivity(intent)
        })
    }

    private fun checkifemailVerified(firebaseUser: FirebaseUser) {
        if (!firebaseUser.isEmailVerified) {
            showAlertDialog()
        }
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this@ProfileActivity)
        builder.setTitle("Email not verified")
        builder.setMessage("Please verify your email")
        builder.setPositiveButton(
            "Continue"
        ) { dialog, which ->
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_APP_EMAIL)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun showUserProfile(firebaseUser: FirebaseUser) {

        var dbRef = FirebaseFirestore.getInstance()
        var firebaseAuth = FirebaseAuth.getInstance()
        var currUser:String? = null
        val authStateListener = FirebaseAuth.AuthStateListener {
            val user = firebaseAuth.currentUser
            if (user!=null){
                currUser = user.uid
                Log.i("myInfo",currUser!!)

                dbRef.collection("users").whereEqualTo("uuid",currUser).get().addOnSuccessListener {
                    if (!it.isEmpty){
                        val mUser2 = it.documents[0]
                        val mUser = mUser2.toObject(Person::class.java)
                        email?.text = mUser?.email
                        full_name?.text = mUser?.name
                        contact?.text = mUser?.phno.toString()
//                        imgProfile?.setImageBitmap(c)


                    }else {
                        Toast.makeText(this@ProfileActivity, "Something went wrong", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            }
        }
        firebaseAuth.addAuthStateListener(authStateListener)



    }

}