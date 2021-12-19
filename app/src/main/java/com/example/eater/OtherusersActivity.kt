package com.example.eater

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OtherusersActivity : AppCompatActivity() {
    private lateinit var session: SessionManager
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otherusers)
        supportActionBar?.hide()
        val apiclient = application as LoginApplication
        session = SessionManager(this)
        var intent= Intent(this@OtherusersActivity,ProfileActivity::class.java)
        var token = session.fetchAuthToken()
        val items: MutableList<OtherusersData> = mutableListOf<OtherusersData>()
        if (session.fetchAuthToken() != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val result = apiclient.otherusersService.otherUserDetails("Bearer " + token)
                var i = 0
                if (result.isSuccessful) {
                    while (i < result.body()?.users!!.size) {
                        items.add(result.body()?.users!![i])
                        i += 1
                    }
                } else {
                    startActivity(intent)
                }
                withContext(Dispatchers.Main) {
                    val recycle = findViewById<RecyclerView>(R.id.otherUserRecylcer)
                    recycle.adapter = OtherusersAdapter(items,this@OtherusersActivity)
                    recycle.layoutManager = LinearLayoutManager(this@OtherusersActivity)
                }

            }
        } else {
            Toast.makeText(
                this@OtherusersActivity,
                "Not Available!!!",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }
}


