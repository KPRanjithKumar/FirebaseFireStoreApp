package ran.am.firebasefirestoreapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    var TAG: String = "iAmMainActivity"
    lateinit var btnretreive: Button
    lateinit var btnsave: Button
    lateinit var tv: TextView
    lateinit var edname: EditText
    lateinit var edloc: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edname = findViewById(R.id.editTextTextPersonName)
        edloc = findViewById(R.id.editTextTextPersonName2)
        btnretreive = findViewById(R.id.button)
        btnsave = findViewById(R.id.button2)
        tv = findViewById(R.id.textView)
        val db = Firebase.firestore

        btnsave.setOnClickListener {
            var stname = edname.text.toString()
            var stlocation = edloc.text.toString()

            val user = hashMapOf(
                "name" to stname,
                "location" to stlocation,
            )
            db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    Toast.makeText(
                        applicationContext,
                        "Save success with id:" + "${documentReference.id}",Toast.LENGTH_SHORT
                    ).show();
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }

        }

        btnretreive.setOnClickListener {

            db.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    var details = "\n"

                    for (document in result) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        document.data.map { (key, value)
                            ->
                            details = details + "$key = $value \n\n"
                        }
                    }
                    tv.setText("" + details)
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
        }
    }
}