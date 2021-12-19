package com.example.eater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.items.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SpecificDishActivity : AppCompatActivity() {
    lateinit var session:SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specific_dish)
        supportActionBar?.hide()

            val img=findViewById<ImageView>(R.id.images)
            val name=findViewById<TextView>(R.id.name)
            val type=findViewById<TextView>(R.id.itemname)
            val price=findViewById<TextView>(R.id.itemprice)
            val contents=findViewById<TextView>(R.id.contentstext)


            var text=intent.getIntExtra("ID",0)
            session= SessionManager(this)
            var token=session.fetchAuthToken()
            var apiClient=application as LoginApplication
            CoroutineScope(Dispatchers.IO).launch {
                val result=apiClient.dishService.GetDishes("Bearer "+ token)
                var i=0
                var res2:DishData?=null
                if(result.isSuccessful){
                    while(i<result.body()?.dishes!!.size){
                        if(result.body()?.dishes!![i].id==text){
                            res2=result.body()?.dishes!![i]
                            break
                        }
                        i+=1
                    }
                }
                else{

                }
                withContext(Dispatchers.Main){
                    Picasso.get().load(res2?.url).into(img);
                    name.text=res2?.name
                    type.text="Type : ${res2?.type}"
                    price.text="$ ${res2?.price}"
                    contents.text="Contents : ${res2?.contents}"
                }
            }
            findViewById<Button>(R.id.Createorder).setOnClickListener{
                var intent= Intent(this,CreateOrderActivity::class.java)
                intent.putExtra("Id",text)
                startActivity(intent)
            }

        }
    }

