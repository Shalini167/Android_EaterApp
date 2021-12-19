package com.example.eater

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()


        val sharedPreferences=getSharedPreferences("user", Context.MODE_PRIVATE)
        val token=sharedPreferences.getString("token",null)

        findViewById<Button>(R.id.regbutton).setOnClickListener {
            val loading=LoadingDialog(this)
            loading.startLoading()
            val handler=Handler()
            handler.postDelayed(object:Runnable{
                override fun run(){
                    loading.isDismiss()
                }
            },3000)
            val newScreenIntent= Intent(this,RegisterActivity::class.java)

              startActivity(newScreenIntent)
        }

        sessionManager = SessionManager(this,)


        findViewById<Button>(R.id.loginbutton).setOnClickListener {
            val email = findViewById<TextInputLayout>(R.id.email).editText?.text
            val password = findViewById<TextInputLayout>(R.id.password).editText?.text

            val user=UserData(email.toString(),password.toString())
            val token=sharedPreferences.getString("token",null)
            val email1=sharedPreferences.getString("email",null)

            CoroutineScope(Dispatchers.IO).launch {
                val sampleApplication=application as LoginApplication
                val service=sampleApplication.loginService
                service.postData(user).enqueue(object : Callback<LoginData?> {
                    override fun onResponse(call: Call<LoginData?>, response: Response<LoginData?>) {
                        if(response.isSuccessful)
                        {
                            val editor=sharedPreferences.edit()
                            editor.apply(){
                                putString("email",response.body()!!.email)
                                putBoolean("loginStatus",true)
                            }.apply()


                            val dishesintent = Intent(this@LoginActivity,DishActivity::class.java)
                            sessionManager.saveAuthToken(response.body()?.token)

                            sessionManager.saveEmail(response.body()?.email)
                            sessionManager.saveMember(response.body()!!.memberSince)
                            intent.putExtra("string",response.body()?.token)


                            intent.putExtra("string",response.body()?.token)
                            startActivity(dishesintent)

                        }
                        else
                        {
                        }
                    }

                    override fun onFailure(call: Call<LoginData?>, t: Throwable) {

                    }
                })
            }
        }
    }

}



