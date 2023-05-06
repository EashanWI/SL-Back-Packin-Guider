package com.example.project_phase1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()

        val nextButton = findViewById<Button>(R.id.button)
        nextButton.setOnClickListener {
            val Intent = Intent(this,activity2::class.java)
            startActivity(Intent)

        }

//        val fetchbutton = findViewById<Button>(R.id.button4)
//        fetchbutton.setOnClickListener{
//            val Intent = Intent(this,PaymentDetailsActivity::class.java)
//            startActivity(Intent)
//        }


    }
}


