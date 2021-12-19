package com.example.eater

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountDeleteActivity : AppCompatActivity() {
    private lateinit var session: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_delete)
        session = SessionManager(this)
        findViewById<TextView>(R.id.deletemail).text = "${session.fetchEmail()}"

        findViewById<Button>(R.id.accountdelete).setOnClickListener{
            val loading=LoadingDialog(this)
            loading.startLoading()
            val handler= Handler()
            handler.postDelayed(object:Runnable{
                override fun run(){
                    loading.isDismiss()
                }
            },3000)
            CoroutineScope(Dispatchers.IO).launch {
                val sampleApplication=application as LoginApplication
                val service=sampleApplication.accountdeleteService
                service.deleteUser("Bearer ${session.fetchAuthToken()}").enqueue(object :
                    Callback<Void?> {
                    override fun onResponse(call: Call<Void?>, response: Response<Void?>) {

                        if(response.isSuccessful)
                        {
                            Toast.makeText(applicationContext,"Successfully deleted", Toast.LENGTH_LONG).show()
                            val preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
                            val editor = preferences.edit()
                            editor.clear()
                            editor.apply()
                            finish()
                            val intent = Intent(this@AccountDeleteActivity,LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else
                        {

                        }
                    }

                    override fun onFailure(call: Call<Void?>, t: Throwable) {
                        Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()

                    }
                })
            }

        }
    }
}




