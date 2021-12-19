package com.example.eater

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class LoginhistoryActivity : AppCompatActivity() {
    private lateinit var session: SessionManager
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginhistory)
        supportActionBar?.hide()
        val memberdate=findViewById<TextView>(R.id.MemberDate)
        session = SessionManager(this)
        var member=session.fetchMember()

        val sdf = SimpleDateFormat("dd-MMM-YYYY")
        val netDate = Date(member)
        val displayThisDate = sdf.format(netDate)
        memberdate.text="$displayThisDate"



        val apiclient = application as LoginApplication
        session = SessionManager(this)
        var intent= Intent(this@LoginhistoryActivity,ProfileActivity::class.java)

        var token = session.fetchAuthToken()
        val items: MutableList<LoginhistoryData> = mutableListOf<LoginhistoryData>()
        if (session.fetchAuthToken() != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val result = apiclient.loginhistoryService.logDetails("Bearer " + token)
                var i = 0
                if (result.isSuccessful) {
                    while (i < result.body()?.loginEntries!!.size) {
                        items.add(result.body()?.loginEntries!![i])
                        i += 1
                    }
                } else {
                    startActivity(intent)
                }
                withContext(Dispatchers.Main) {
                    val recycle = findViewById<RecyclerView>(R.id.loginHistoryRecycler)
                    recycle.adapter = LoginHistoryAdapter(items,this@LoginhistoryActivity)
                    recycle.layoutManager = LinearLayoutManager(this@LoginhistoryActivity)
                }

            }
        } else {
            Toast.makeText(
                this@LoginhistoryActivity,
                "Not Available!!!",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }
}

