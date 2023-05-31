package com.example.Giggles

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.InputStream
import java.time.LocalDate
import java.time.LocalDateTime

class SellToyActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    //private FirebaseAuth mAuth;
    //private DatabaseReference mDatabase;
    //private StorageReference mStorage;
    private var mToyTitleEditText: EditText? = null
    private var mToyConditionEditText: EditText? = null
    private var mExpectedPriceEditText: EditText? = null
    private var mToyCategorySpinner: Spinner? = null
    private var mToyBrandSpinner: Spinner? = null
    private var mToyImageView: ImageView? = null
    private var mImageUri: Uri? = null
    @RequiresApi(Build.VERSION_CODES.O)
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell_toy)

        //mAuth = FirebaseAuth.getInstance();
        //mDatabase = FirebaseDatabase.getInstance().getReference();
        //mStorage = FirebaseStorage.getInstance().getReference();

        var currentUser = FirebaseAuth.getInstance().currentUser
        var dbRef = FirebaseFirestore.getInstance().collection("toys")
        mToyTitleEditText = findViewById<EditText>(R.id.toy_title_edit_text)
        mToyCategorySpinner = findViewById<Spinner>(R.id.toy_category_spinner)
        mToyBrandSpinner = findViewById<Spinner>(R.id.toy_brand_spinner)
        mToyConditionEditText = findViewById<EditText>(R.id.toy_condition_edit_text)
        mExpectedPriceEditText = findViewById<EditText>(R.id.expected_price_edit_text)
        mToyImageView = findViewById<ImageView>(R.id.toy_image_view)
        val categoryAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            TOY_CATEGORIES
        )
        mToyCategorySpinner?.adapter = categoryAdapter
        mToyCategorySpinner?.onItemSelectedListener = this
        val brandAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, TOY_BRANDS)
        mToyBrandSpinner?.adapter = brandAdapter
        mToyBrandSpinner?.onItemSelectedListener = this
        findViewById<View>(R.id.choose_image_button).setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE_PICK)
        })
        findViewById<View>(R.id.capture_image_button).setOnClickListener(View.OnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        })
        findViewById<View>(R.id.post_button).setOnClickListener(View.OnClickListener {
            // Get the values from the input fields
            val toyTitle: String = mToyTitleEditText?.text.toString().trim { it <= ' ' }
            val toyCategory: String = mToyCategorySpinner?.selectedItem.toString()
            val toyBrand: String = mToyBrandSpinner?.selectedItem.toString()
            val toyCondition: String = mToyConditionEditText?.text.toString().trim { it <= ' ' }
            val expectedPriceStr: String =
                mExpectedPriceEditText?.text.toString().trim { it <= ' ' }
            // Check if all fields are filled
            if (TextUtils.isEmpty(toyTitle)) {
                mToyTitleEditText?.error = "Toy title is required."
                mToyTitleEditText?.requestFocus()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(toyCondition)) {
                mToyConditionEditText?.error = "Toy condition is required."
                mToyConditionEditText?.requestFocus()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(expectedPriceStr)) {
                mExpectedPriceEditText?.error = "Expected price is required."
                mExpectedPriceEditText?.requestFocus()
                return@OnClickListener
            }
            val expectedPrice = expectedPriceStr.toInt()
            if (expectedPrice < MIN_PRICE || expectedPrice > MAX_PRICE) {
                mExpectedPriceEditText?.error =
                    "Expected price must be between $MIN_PRICE and $MAX_PRICE."
                mExpectedPriceEditText?.requestFocus()
                return@OnClickListener
            }
            // Check if an image has been selected
            if (mImageUri == null) {
                Toast.makeText(
                    this@SellToyActivity,
                    "Please choose or take a photo of the toy.",
                    Toast.LENGTH_SHORT
                ).show()
                return@OnClickListener
            }

            // Compress the image and upload it to Firebase Storage
            try {
                val imageStream: InputStream? = contentResolver.openInputStream(mImageUri!!)
                val bitmap: Bitmap = BitmapFactory.decodeStream(imageStream)
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos)
                val imageData = baos.toByteArray()
                val imgData = Base64.encodeToString(imageData,Base64.URL_SAFE)
//                Log.i("myInfo",imgData)
                if (imageData.size > MAX_IMAGE_SIZE) {
                    Toast.makeText(
                        this@SellToyActivity,
                        "The selected image is too large. Please choose a smaller one.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@OnClickListener
                }

                // Generate a random key for the toy
                //String key = mDatabase.child("toys").push().getKey();

                // Create a Toy object

                val newToy = Toy2(
                    toyid = System.currentTimeMillis().toString(),
                    toyname = toyTitle,
                    toycategory = toyCategory,
                    toyprice = expectedPrice.toDouble(),
                    toydescription = toyCondition,
                    toyrating = 0.0,
                    toyseller = currentUser?.uid,
                    toyuploaddate = LocalDateTime.now().toString(),
                    toyapproved = false,
                    toyimg = imgData
                )

                dbRef.document().set(newToy).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(
                            this@SellToyActivity,
                            "Your Toy Sell Request has been sent",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@SellToyActivity,MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(
                            this@SellToyActivity,
                            "Sell Request Failed",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@SellToyActivity,SellToyActivity::class.java)
                        startActivity(intent)
                    }
                }



                // Upload the image to Firebase Storage
                //StorageReference imageRef = mStorage.child("toys/" + key + ".jpg");
                //UploadTask uploadTask = imageRef.putBytes(imageData);
                //uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            // Get the download URL of the image
//                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                @Override
//                                public void onSuccess(Uri uri) {
//                                    toy.setImageUrl(uri.toString());
//                                    // Add the Toy object to the database
//                                    mDatabase.child("toys").child(key).setValue(toy);
//                                    Toast.makeText(SellToyActivity.this, "Toy uploaded successfully.", Toast.LENGTH_SHORT).show();
//                                    finish();
//                                }
//                            });
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(SellToyActivity.this, "Failed to upload toy. Please try again later.", Toast.LENGTH_SHORT).show();
//                        }
//                    });
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        })
    }

    protected override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val extras: Bundle? = data?.getExtras()
            val imageBitmap: Bitmap = extras?.get("data") as Bitmap
            mToyImageView!!.setImageBitmap(imageBitmap)
            mImageUri = data.getData()

        }
        else if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                mImageUri = data.getData()
            }
            mToyImageView!!.setImageURI(mImageUri)
        }
        if (data==null){
            Log.i("MyInfo","Your URI is Null")
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
// Nothing to do here
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
// Nothing to do here
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_IMAGE_PICK = 2
        private const val MAX_IMAGE_SIZE = 1024 * 1024 // 1 MB
        private val TOY_CATEGORIES = arrayOf("Stuffed Toy", "Electronics", "Legos", "Hot Wheels")
        private val TOY_BRANDS = arrayOf("Brand A", "Brand B", "Brand C", "Brand D")
        private const val MIN_PRICE = 300
        private const val MAX_PRICE = 10000
    }
}