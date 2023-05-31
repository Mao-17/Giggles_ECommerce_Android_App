package com.example.Giggles

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.*
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Matcher
import java.util.regex.Pattern


class RegisterActivity2 : AppCompatActivity() {
    var register_username_field: EditText? = null
    var register_email_field: EditText? = null
    var register_password_field: EditText? = null
    var register_phoneno_field: EditText? = null
    var register_button: Button? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)
        register_button = findViewById(R.id.register_button)
        register_username_field = findViewById(R.id.register_username_field)
        register_password_field = findViewById(R.id.register_password_field)
        register_email_field = findViewById(R.id.register_email_field)
        register_phoneno_field = findViewById<EditText>(R.id.register_phoneno_field)
        register_button?.setOnClickListener(View.OnClickListener {
            val Name = register_username_field?.getText().toString()
            val email = register_email_field?.getText().toString()
            val PhoneNo = register_phoneno_field?.getText().toString()
            val password = register_password_field?.getText().toString()
            //validate mobile no.
            val mobileRegex = "[6-9][0-9]{9}"
            val mobileMatcher: Matcher
            val mobilePattern = Pattern.compile(mobileRegex)
            mobileMatcher = mobilePattern.matcher(PhoneNo)
            if (TextUtils.isEmpty(Name)) {
                Toast.makeText(
                    this@RegisterActivity2,
                    "Please enter your full name",
                    Toast.LENGTH_SHORT
                ).show()
                register_username_field?.setError("Full name is required")
                register_username_field?.requestFocus()
            } else if (TextUtils.isEmpty(email)) {
                Toast.makeText(this@RegisterActivity2, "Please enter your email", Toast.LENGTH_SHORT)
                    .show()
                register_email_field?.setError("Email is required")
                register_email_field?.requestFocus()
            }
//            else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                Toast.makeText(
//                    this@RegisterActivity2,
//                    "Please re-enter your email",
//                    Toast.LENGTH_SHORT
//                ).show()
//                register_email_field?.setError("Valid email is required")
//                register_email_field?.requestFocus()
//            }
            else if (TextUtils.isEmpty(PhoneNo)) {
                Toast.makeText(
                    this@RegisterActivity2,
                    "Please enter your contact Number",
                    Toast.LENGTH_SHORT
                ).show()
                register_phoneno_field?.setError("Contact Number is required")
                register_phoneno_field?.requestFocus()
            } else if (PhoneNo.length != 10) {
                Toast.makeText(
                    this@RegisterActivity2,
                    "Please re-enter your contact Number",
                    Toast.LENGTH_SHORT
                ).show()
                register_phoneno_field?.setError("Contact Number should be 10 digits")
                register_phoneno_field?.requestFocus()
            } else if (!mobileMatcher.find()) {
                Toast.makeText(
                    this@RegisterActivity2,
                    "Contact Number is not valid",
                    Toast.LENGTH_SHORT
                ).show()
                register_phoneno_field?.setError("Contact Number is required")
                register_phoneno_field?.requestFocus()
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(
                    this@RegisterActivity2,
                    "Please enter your password",
                    Toast.LENGTH_SHORT
                ).show()
                register_password_field?.setError("Password is required")
                register_password_field?.requestFocus()
            } else if (password.length < 6) {
                Toast.makeText(
                    this@RegisterActivity2,
                    "Password should be atleast 6 digits",
                    Toast.LENGTH_SHORT
                ).show()
                register_password_field?.setError("Password is too weak")
                register_password_field?.requestFocus()
            } else {
                registerUser(Name, PhoneNo, email, password)
            }
        })
    }

    private fun registerUser(Name: String, PhoneNo: String, Email: String, password: String) {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(Email, password).addOnCompleteListener(
            this@RegisterActivity2
        ) { task ->
            if (task.isSuccessful) {
                val firebaseUser = auth.currentUser
                val profileChangeRequest =
                    UserProfileChangeRequest.Builder().setDisplayName(Name).build()
                firebaseUser!!.updateProfile(profileChangeRequest)
                // enter user details in the firebase
                val writeUserDetails = ReadWriteuserDetails(PhoneNo, Email)
                val referenceProfile =
                    FirebaseDatabase.getInstance().getReference("Registered user")
                referenceProfile.child(firebaseUser.uid).setValue(writeUserDetails)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {    //Send verification email
                            firebaseUser.sendEmailVerification()
                            Toast.makeText(
                                this@RegisterActivity2,
                                "User registered successfully. Please verify you email",
                                Toast.LENGTH_SHORT
                            ).show()

                            //open login Page after successful registration
                            val intent = Intent(
                                this@RegisterActivity2,
                                ProfileActivity::class.java
                            )
                            intent.flags =
                                (Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@RegisterActivity2, "User registered failled. Please try again",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                try {
                    throw task.exception!!
                } catch (e: FirebaseAuthWeakPasswordException) {
                    register_password_field!!.error = "Your password is too weak"
                    register_password_field!!.requestFocus()
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    register_password_field!!.error = "Email Id is invalid. Please re-enter."
                    register_password_field!!.requestFocus()
                } catch (e: FirebaseAuthUserCollisionException) {
                    register_password_field!!.error = "user is already registered with this email."
                    register_password_field!!.requestFocus()
                } catch (e: Exception) {
//                    Log.e(RegisterActivity.Companion.TAG, e.message!!)
                    Toast.makeText(this@RegisterActivity2, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        const val TAG = "RegisterActivity"
    }
}