package com.example.eater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateOrderActivity : AppCompatActivity() {
    lateinit var session:SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_order)
        supportActionBar?.hide()
        val img=findViewById<ImageView>(R.id.image)
        val qty=findViewById<TextView>(R.id.qty)
        val add=findViewById<Button>(R.id.add)
        val sub=findViewById<Button>(R.id.sub)
        val pricce=findViewById<TextView>(R.id.itempricee)
        val create=findViewById<Button>(R.id.confirmorder)
        var text=intent.getIntExtra("Id",0)
        var prices:Int?=0
        var count=0
        session= SessionManager(this)
        var token=session.fetchAuthToken()
        var apiClient=application as LoginApplication
        CoroutineScope(Dispatchers.IO).launch {
            val result=apiClient.dishService.GetDishes("Bearer "+token)
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
                Picasso.get().load(res2?.url).into(img)
                prices=res2?.price?.toInt()
            }
        }
        add.setOnClickListener{
            count+=1
            qty.text="$count"
            val pp=prices!!.toInt()
            pricce.text="$ ${pp*count}"
        }
        sub.setOnClickListener{
            if(count>0){
                count-=1
            }
            else
                count=0
            qty.text="$count"
            val pp=prices!!.toInt()
            pricce.text="$ ${pp*count}"
        }
        create.setOnClickListener{
            val loading=LoadingDialog(this)
            loading.startLoading()
            val handler= Handler()
            handler.postDelayed(object:Runnable{
                override fun run(){
                    loading.isDismiss()
                }
            },3000)
            if(count>0){
                CoroutineScope(Dispatchers.IO).launch {

                    var result=apiClient.createorderService.CreateOrders("Bearer "+token, CreateOrderData(text,count))
                    withContext(Dispatchers.Main){
                        if(result!!.isSuccessful){

                            Toast.makeText(this@CreateOrderActivity,"Order successfull",Toast.LENGTH_SHORT).show()
                            val newScreenIntent= Intent(this@CreateOrderActivity,MyordersActivity::class.java)

                            startActivity(newScreenIntent)
                        }
                    }

                }
            }
            else{
                Toast.makeText(this@CreateOrderActivity,"Please select number of items",Toast.LENGTH_SHORT).show()
            }
        }
    }
}

