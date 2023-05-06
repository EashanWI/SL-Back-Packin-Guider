package com.example.eduxo

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {


    private lateinit var etVehiType: EditText
    private lateinit var etVehiModel: EditText
    private lateinit var etvehiNo: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)


        etVehiType = findViewById(R.id.etVehiType)
        etVehiModel = findViewById(R.id.etVehiModel)
        etvehiNo = findViewById(R.id.etVehiNo)
        etEmail = findViewById(R.id.etEmail)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {

        //getting values
        val VehiModel = etVehiModel.text.toString()
        val vehiType = etVehiType.text.toString()
        val vehiNo = etvehiNo.text.toString()
        val Email = etEmail.text.toString()

        if (VehiModel.isEmpty()) {
            etVehiModel.error = "Please enter name"
        }
        if (vehiType.isEmpty()) {
            etVehiType.error = "Please enter age"
        }
        if (vehiNo.isEmpty()) {
            etvehiNo.error = "Please enter salary"
        }
        if (Email.isEmpty()) {
            etEmail.error = "Please enter salary"
        }

        val vehiId = dbRef.push().key!!

        val vehicle = vehicleModel(vehiId,vehiType,vehiNo,Email,VehiModel)

        dbRef.child(vehiId).setValue(vehicle)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etVehiModel.text.clear()
                etVehiType.text.clear()
                etvehiNo.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}