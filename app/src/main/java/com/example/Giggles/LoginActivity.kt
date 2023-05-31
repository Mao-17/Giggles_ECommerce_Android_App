package com.example.Giggles

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    private var mEmailEditText: EditText? = null
    private var mPasswordEditText: EditText? = null
    private var mLoginButton: Button? = null

    private lateinit var mAuth:FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        mAuth = FirebaseAuth.getInstance();

        var dbRef = FirebaseFirestore.getInstance()

        var currUser:String? = null
        val authStateListener = FirebaseAuth.AuthStateListener {
            val user = mAuth.currentUser
            if (user!=null){
                currUser = user.uid
//                Log.i("myInfo",currUser!!)

                dbRef.collection("users").whereEqualTo("uuid",currUser).get().addOnSuccessListener {
                    if (!it.isEmpty){
                        val mUser2 = it.documents[0]

                        val mUser = mUser2.toObject(Person::class.java)
//                        Log.i("myInfo",mUser?.admin!!.toString())
//                        Log.i("myInfo",mUser2.toString())
                        if (mUser?.admin!!){

                            val intent = Intent(this,AdminActivity::class.java)
                            startActivity(intent)
                        }else{
                            val intent = Intent(this,MainActivity::class.java)
                            startActivity(intent)
                        }
//


                    }else {
                        Toast.makeText(this@LoginActivity, "Something went wrong", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            }
        }
        mAuth.addAuthStateListener(authStateListener)

//        if (mAuth.currentUser!=null){
//            val intent = Intent(this,MainActivity::class.java)
//            startActivity(intent)
//        }

        mEmailEditText = findViewById(R.id.email_edit_text)
        mPasswordEditText = findViewById(R.id.password_edit_text)
        mLoginButton = findViewById(R.id.login_button)
        val mRegisterButton = findViewById<TextView>(R.id.btnRegister2)
        val mRegisterAdmin = findViewById<TextView>(R.id.btnRegisterAdmin)
        mLoginButton?.setOnClickListener(View.OnClickListener {
            val email = mEmailEditText?.text.toString().trim { it <= ' ' }
            val password = mPasswordEditText?.text.toString().trim { it <= ' ' }
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Please fill in all fields", Toast.LENGTH_SHORT)
                    .show()
            } else {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else Toast.makeText(this@LoginActivity, "Authentication failed", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        })

        mRegisterButton.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            intent.putExtra("admin","No")
            startActivity(intent)
        }

        mRegisterAdmin.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            intent.putExtra("admin","yes")
            startActivity(intent)
        }
    }
}