package com.example.eater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyordersActivity : AppCompatActivity() {
    lateinit var session: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myorders)
        supportActionBar?.hide()
        var apiclient = application as LoginApplication
        session = SessionManager(this)
        var dishes: MutableList<DishData> = mutableListOf<DishData>()
        var token = session.fetchAuthToken()


        CoroutineScope(Dispatchers.IO).launch {
            var result = apiclient.myorderService.fetchOrders("Bearer " + token)
            if (result.isSuccessful) {

                var i = 0
                while (i < result.body()!!.dishOrders!!.size) {
                    var res = apiclient.dishService.GetDishes("Bearer " + token)
                    var j = 0
                    while (j < res.body()!!.dishes!!.size) {
                        if (result.body()!!.dishOrders!![i].dishId == res.body()!!.dishes!![j].id)
                            dishes.add(
                                DishData(
                                    res.body()!!.dishes!![j].id,
                                    res.body()!!.dishes!![j].url,
                                    res.body()!!.dishes!![j].name,
                                    res.body()!!.dishes!![j].price,
                                    res.body()!!.dishes!![j].contents,
                                    res.body()!!.dishes!![j].type,
                                    result.body()!!.dishOrders!![i].orderId,
                                    result.body()!!.dishOrders!![i].count
                                )
                            )

                        j += 1

                    }
                    i += 1
                }

            }

            withContext(Dispatchers.Main) {
                val emptymsg = findViewById<TextView>(R.id.textView11)

                if (dishes.isNullOrEmpty()) {
                    emptymsg.text = "You have no orders yet"
                } else {

                    var recycle = findViewById<RecyclerView>(R.id.recyclerView)

                    recycle.adapter = MyorderAdapter(dishes, this@MyordersActivity, application)
                    recycle.layoutManager = LinearLayoutManager(this@MyordersActivity)
                }
            }
        }
    }
}