package com.example.Giggles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity2 : AppCompatActivity() {

    var full_name: TextView? = null
    var email: TextView? = null
    var contact: TextView? = null
    var password: TextView? = null
    var editProfile: TextView? = null
    var changePassword: TextView? = null
    var logout: TextView? = null
    private var authProfile: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile2)

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
            val intent = Intent(this@ProfileActivity2, ProfileActivity::class.java)
            startActivity(intent)
        })
        logout?.setOnClickListener(View.OnClickListener {
            authProfile!!.signOut()
            Toast.makeText(this@ProfileActivity2, "Logged Out", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@ProfileActivity2, LoginActivity::class.java)
            startActivity(intent)
        })
        changePassword?.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@ProfileActivity2, ProfileActivity::class.java)
            startActivity(intent)
        })
    }

    private fun checkifemailVerified(firebaseUser: FirebaseUser) {
        if (!firebaseUser.isEmailVerified) {
            showAlertDialog()
        }
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this@ProfileActivity2)
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
                        Toast.makeText(this@ProfileActivity2, "Something went wrong", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            }
        }
        firebaseAuth.addAuthStateListener(authStateListener)



    }

    }
