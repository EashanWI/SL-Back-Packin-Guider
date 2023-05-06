package com.example.project_phase1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_phase1.adapters.payAdapter
import com.google.firebase.database.*

class FetchingActivity : AppCompatActivity() {

    private lateinit var payRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var payList: ArrayList<paymentModel>
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        payRecyclerView = findViewById(R.id.paym)
        payRecyclerView.layoutManager = LinearLayoutManager(this)
        payRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.LoadingData)

        payList = arrayListOf<paymentModel>()

        getpaymentData()
    }

    private fun getpaymentData(){
        payRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("payments")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot){
                payList.clear()
                if(snapshot.exists()){
                    for(paySnap in snapshot.children){
                        val payData = paySnap.getValue(paymentModel::class.java)
                        payList.add(payData!!)
                    }
                    val mAdapter = payAdapter(payList)
                    payRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : payAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingActivity,PaymentDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("payID",payList[position].paymentId)
                            intent.putExtra("Number",payList[position].Number)
                            intent.putExtra("Date",payList[position].Date)
                            intent.putExtra("cvv",payList[position].cvv)
                            intent.putExtra("Name",payList[position].Name)
                            startActivity(intent)
                        }
                    })

                    payRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}