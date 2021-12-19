package com.example.eater

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.provider.MediaStore
import android.widget.ImageView
import androidx.annotation.RequiresApi
import java.util.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var session:SessionManager
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()

        val mail=findViewById<TextView>(R.id.mailView)
        val membersince=findViewById<TextView>(R.id.historyView)

        session = SessionManager(this)
        var email = session.fetchEmail()
        mail.text="$email"

        var member = session.fetchMember()
        val sdf = SimpleDateFormat("dd-MMM-YYYY")
        val netDate = Date(member)
        val displayThisDate = sdf.format(netDate)
        membersince.text="Member since:$displayThisDate"

        findViewById<ImageView>(R.id.imageView).setOnClickListener()
        {
            val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,100)

        }
        findViewById<Button>(R.id.history).setOnClickListener {
            val intent = Intent(this@ProfileActivity,LoginhistoryActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.changeMail).setOnClickListener {
            val intent = Intent(this@ProfileActivity,ChangeEmailActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.deleteBtn).setOnClickListener {
            val intent = Intent(this@ProfileActivity,AccountDeleteActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.logoutbtn).setOnClickListener {

            val preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.clear()
            editor.apply()
            finish()
            val local = getSharedPreferences("user", Context.MODE_PRIVATE)
            val editor2 = local.edit()
            editor2.clear()
            editor2.apply()
            finish()
            val intent = Intent(this@ProfileActivity,LoginActivity::class.java)
            startActivity(intent)


        }
    }
}