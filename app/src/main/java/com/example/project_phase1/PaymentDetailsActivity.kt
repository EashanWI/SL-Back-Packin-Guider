package com.example.project_phase1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.sql.RowId

class PaymentDetailsActivity : AppCompatActivity() {

    private lateinit var payId: TextView
    private lateinit var tvCardNumber: TextView
    private lateinit var tvExpiryDate: TextView
    private lateinit var tvCvv: TextView
    private lateinit var tvName: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("payId").toString(),
                intent.getStringExtra("Number").toString(),
            )
        }

        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("payId").toString()
            )
        }
    }

    private fun deleteRecord(
        id:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("payments").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Payment data Deleted",Toast.LENGTH_LONG).show()
            val intent = Intent(this,FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting error ${error.message}",Toast.LENGTH_LONG).show()
        }
    }

    private fun initView(){
        payId = findViewById(R.id.payId)
        tvCardNumber = findViewById(R.id.tvCardNumber)
        tvExpiryDate = findViewById(R.id.tvExpiryDate)
        tvCvv = findViewById(R.id.tvCvv)
        tvName = findViewById(R.id.tvName)

        btnUpdate = findViewById(R.id.btnUpdateData)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews(){

        payId.text = intent.getStringExtra("payId")
        tvCardNumber.text = intent.getStringExtra("Number")
        tvExpiryDate.text = intent.getStringExtra("Date")
        tvCvv.text = intent.getStringExtra("cvv")
        tvName.text = intent.getStringExtra("Name")
    }

    private fun openUpdateDialog(
        payId: String,
        Number: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDilogView = inflater.inflate(R.layout.update_dialog,null)

        mDialog.setView(mDilogView)

        val etCardNumber = mDilogView.findViewById<EditText>(R.id.etCardNumber)
        val etExpireDate = mDilogView.findViewById<EditText>(R.id.etExpireDate)
        val etcvv = mDilogView.findViewById<EditText>(R.id.etcvv)
        val etName = mDilogView.findViewById<EditText>(R.id.etName)
        val btnUpdateData = mDilogView.findViewById<Button>(R.id.btnUpdateData)


        tvCardNumber.setText(intent.getStringExtra("Number").toString())
        tvExpiryDate.setText(intent.getStringExtra("Date").toString())
        tvCvv.setText(intent.getStringExtra("cvv").toString())
        tvName.setText(intent.getStringExtra("Name").toString())

        mDialog.setTitle("updating $Number Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdate.setOnClickListener{
            updateData(
                payId,
                tvCardNumber.text.toString(),
                tvExpiryDate.text.toString(),
                tvCvv.text.toString(),
                tvName.text.toString()
            )

            Toast.makeText(applicationContext,"Payment updated",Toast.LENGTH_LONG).show()

            tvCardNumber.text = etCardNumber.text.toString()
            tvExpiryDate.text = etExpireDate.text.toString()
            tvCvv.text = etcvv.text.toString()
            tvName.text = etName.text.toString()

            alertDialog.dismiss()

        }


    }
    private fun updateData(
        id:String,
        cardnumber: String,
        date: String,
        cvv : String,
        name: String
    ){
        val dbref = FirebaseDatabase.getInstance().getReference("payments").child(id)
        val payinfo = paymentModel(id,cardnumber,date,cvv,name)
        dbref.setValue(payinfo)
    }
}