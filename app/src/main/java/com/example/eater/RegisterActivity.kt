package com.example.eater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
        val regButton = findViewById<Button>(R.id.registerbtn)
        regButton.setOnClickListener {
            val email = findViewById<TextInputLayout>(R.id.email).editText?.text
            val password = findViewById<TextInputLayout>(R.id.password).editText?.text
            val user=UserData(email.toString(),password.toString())

            CoroutineScope(Dispatchers.IO).launch {

                val regApplication = application as LoginApplication
                val service = regApplication.registerService


                service.postRegData(user).enqueue(object :
                    Callback<LoginData?> {
                    override fun onFailure(call: Call<LoginData?>, t: Throwable) {
                        Toast.makeText(
                            this@RegisterActivity,
                            t.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onResponse(
                        call: Call<LoginData?>,
                        response: Response<LoginData?>
                    ) {
                        when {
                            response.code()==200 -> {

                                Toast.makeText(this@RegisterActivity, "Registration success!", Toast.LENGTH_SHORT)
                                    .show()
                                val intent = Intent(this@RegisterActivity,LoginActivity::class.java)
                                startActivity(intent)

                            }
                            response.code()==500 -> {

                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Registration failed!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()

                            }
                            else -> {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "User Already exists!!!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    }
                })
            }
        }
    }
}
