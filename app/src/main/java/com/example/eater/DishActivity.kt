package com.example.eater


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.view.menu.MenuView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_dish.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DishActivity : AppCompatActivity() {
    private lateinit var session: SessionManager

    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish)
        supportActionBar?.hide()
        val mytoolbar=findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        val navview=findViewById<NavigationView>(R.id.nav_view)
        setSupportActionBar(mytoolbar)
        val drawerLayout=findViewById<DrawerLayout>(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.eaterapplogo)


        navview.setNavigationItemSelectedListener{
            when(it.itemId) {
                R.id.nav_profile ->{
                    val intent = Intent(this@DishActivity,ProfileActivity::class.java)
                    startActivity(intent)

                }
                R.id.nav_myorders ->{
                    val intent = Intent(this@DishActivity,MyordersActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_otherusers ->{
                    val intent = Intent(this@DishActivity,OtherusersActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }


        val string = intent.getStringExtra("string")
        val apiclient = application as LoginApplication
        session = SessionManager(this)
        val personname= navview.getHeaderView(0).findViewById<TextView>(R.id.profilename)
        var email1 = session.fetchEmail()
        personname.text="$email1"
        var intent = Intent(this, LoginActivity::class.java)

        var token = session.fetchAuthToken()

        val items: MutableList<DishData> = mutableListOf<DishData>()
        if (session.fetchAuthToken() != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val result = apiclient.dishService.GetDishes("Bearer " + token)
                var i = 0
                withContext(Dispatchers.Main)
                {
                    if (result.isSuccessful) {
                        while (i < result.body()?.dishes!!.size) {
                            items.add(result.body()?.dishes!![i])
                            i += 1
                        }
                    } else {
                        startActivity(intent)
                    }
                    val recycle = findViewById<RecyclerView>(R.id.recycleView1)
                    recycle.adapter = AdapterClass(items,this@DishActivity)
                    recycle.layoutManager = LinearLayoutManager(this@DishActivity)
                }

            }
        } else {
            startActivity(intent)
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}


//        setSupportActionBar(toolbar)
//
//        val toggle =
//            ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
//        toggle.isDrawerIndicatorEnabled = true
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
//        supportActionBar?.setHomeButtonEnabled(true)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeAsUpIndicator(R.drawable.logomenu)
//       val navView: NavigationView = findViewById(R.id.nav_view)
//
//
//        navView.setNavigationItemSelectedListener{
//            when(it.itemId) {
//                R.id.nav_profile ->{
//                    val intent = Intent(this@DishActivity,ProfileActivity::class.java)
//                    startActivity(intent)
//                }
//                R.id.nav_myorders ->{
//                    val intent = Intent(this@DishActivity,MyordersActivity::class.java)
//                    startActivity(intent)
//                }
//                R.id.nav_otherusers ->{
//                    val intent = Intent(this@DishActivity,OtherusersActivity::class.java)
//                    startActivity(intent)
//                }
//            }
//            true
//        }



//        val navprofile=findViewById<>(R.id.nav_profile)
//        navprofile.setOnClickListener {
//            val intent = Intent(this@DishActivity,ProfileActivity::class.java)
//            startActivity(intent)
//        }



//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if(toggle.onOptionsItemSelected(item)){
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }
//   override fun onNavigatinItemSelected(item: MenuItem){
//       if(item.itemId == R.id.nav_profile){
//               val intent = Intent(this@DishActivity,ProfileActivity::class.java)
//               startActivity(intent)
//
//       }
//   }

