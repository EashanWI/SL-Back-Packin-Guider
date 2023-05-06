package com.example.project_phase1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class activity2 : AppCompatActivity() {

    private lateinit var edtNumber: EditText
    private lateinit var edtDate: EditText
    private lateinit var edtCvv: EditText
    private lateinit var edtName: EditText
    private lateinit var btnAddCard: Button


    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        edtNumber = findViewById(R.id.editTextTextPersonName)
        edtDate = findViewById(R.id.editTextTextPersonName2)
        edtCvv = findViewById(R.id.editTextTextPersonName3)
        edtName = findViewById(R.id.editTextTextPersonName4)
        btnAddCard = findViewById(R.id.button2)



        dbRef = FirebaseDatabase.getInstance().getReference("payments")

        btnAddCard.setOnClickListener{
            savePaymentData()
            val intent = Intent(this,FetchingActivity::class.java)
            startActivity(intent)
//            val iintent = Intent(this,PaymentDetailsActivity::class.java)
//            startActivity(iintent)
        }


        getSupportActionBar()?.setTitle("Add new Card");

//        val btnInsertData = findViewById<Button>(R.id.button2)
//        btnInsertData.setOnClickListener{
//            val intent = Intent(this,FetchingActivity::class.java)
//            startActivity(intent)
//        }

//        }
 //       PaymentDetailsActivity

    }

    private fun savePaymentData(){
        val Number = edtNumber.text.toString()
        val Date = edtDate.text.toString()
        val cvv = edtCvv.text.toString()
        val Name = edtName.text.toString()

        if(Number.isEmpty()){
            edtNumber.error = "Please Enter number"
        }
        if(Date.isEmpty()){
            edtDate.error = "Please Enter date"
        }
        if(cvv.isEmpty()){
            edtCvv.error = "Please Enter cvv"
        }
        if(Name.isEmpty()){
            edtName.error = "Please Enter name"
        }

        val paymentId = dbRef.push().key!!

        val payment = paymentModel(paymentId, Number, Date, cvv, Name)

        dbRef.child(paymentId).setValue(payment)
            .addOnCompleteListener{
                Toast.makeText(this,"Data inserted Successfully",Toast.LENGTH_LONG).show()

                edtNumber.text.clear()
                edtDate.text.clear()
                edtCvv.text.clear()
                edtName.text.clear()


            }.addOnFailureListener{ err ->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_LONG).show()
            }


    }





}