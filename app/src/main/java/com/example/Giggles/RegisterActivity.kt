package com.example.Giggles

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime

class RegisterActivity : AppCompatActivity() {
    private var mEmailField: EditText? = null
    private var mPasswordField: EditText? = null
    private var mUsernameField: EditText? = null
    private var mRegisterButton: Button? = null
    private var mAddress: EditText? = null
    private var mPhno:EditText? = null

    private lateinit var mAuth:FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mEmailField = findViewById(R.id.register_email_field)
        mPasswordField = findViewById(R.id.register_password_field)
        mUsernameField = findViewById(R.id.register_username_field)
        mRegisterButton = findViewById(R.id.register_button)
        mPhno = findViewById(R.id.register_phno)
        mAddress = findViewById(R.id.register_address)

        var ifAdmin = intent.getStringExtra("admin")




        mAuth = FirebaseAuth.getInstance();
        mRegisterButton?.setOnClickListener(View.OnClickListener {
            val email = mEmailField?.text.toString().trim { it <= ' ' }
            val password = mPasswordField?.text.toString().trim { it <= ' ' }
            val username = mUsernameField?.text.toString().trim { it <= ' ' }
            val address = mAddress?.text.toString().trim { it <= ' ' }
            val phone = mPhno?.text.toString().trim{it<=' '}.toLong()
            if (TextUtils.isEmpty(email)) {
                mEmailField?.error = "Email is required"
                return@OnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                mEmailField?.error = "Please enter a valid email address"
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                mPasswordField?.error = "Password is required"
                return@OnClickListener
            }
            if (password.length < 6) {
                mPasswordField?.error = "Password must be at least 6 characters long"
                return@OnClickListener
            }
            if (TextUtils.isEmpty(username)) {
                mUsernameField?.error = "Username is required"
                return@OnClickListener
            }


            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    mAuth.currentUser?.sendEmailVerification();
                    Toast.makeText(
                        this@RegisterActivity,
                        "Registration successful. Please verify your email address.",
                        Toast.LENGTH_SHORT
                    ).show()
                    val currUser = FirebaseAuth.getInstance().currentUser

                    val newPerson = Person(
                        uuid = currUser?.uid,
                        name = username,
                        phno = phone,
                        email = email,
                        passwordLength = password.length,
                        address = address,
                        admin = ifAdmin=="yes"
                    )
                    var dbRef = FirebaseFirestore.getInstance().collection("users")
                    dbRef.document().set(newPerson)
//                    finish()
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)

                } else Toast.makeText(this@RegisterActivity, "Registration failed. " + it.exception, Toast.LENGTH_SHORT).show()
            }
        })
    }
}