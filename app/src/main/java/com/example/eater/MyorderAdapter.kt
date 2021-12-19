package com.example.eater

import android.app.Application
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyorderAdapter(var items:MutableList<DishData>, var context: Context, var application: Application):
    RecyclerView.Adapter<MyorderAdapter.ViewHolder>() {
    lateinit var session: SessionManager
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyorderAdapter.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.myorderscontent,parent,false)
        return ViewHolder(view)

    }
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(items[position]!!.url).into(holder.img)
        holder.names.text=items[position].name
        holder.ttype.text="Type:${items[position].type}"
        holder.price.text="Total Cost $ ${items[position].price!!.toInt()+items[position]!!.price}"
        holder.countt.text="${items[position].orderId} items"

        holder.cancelbtn.setOnClickListener{
            val progressDialog= ProgressDialog(context,R.style.dishdialog)
            progressDialog.setMessage("Loading...")
            progressDialog.show()

            session = SessionManager(context)
            CoroutineScope(Dispatchers.IO).launch {
                val sampleApplication=application as LoginApplication
                val service=sampleApplication.deleteorderService
                service.DeleteOrders("Bearer ${session.fetchAuthToken()}",items[position].count).enqueue(object :
                    Callback<Void?> {
                    override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                        if(response.isSuccessful)
                        {
                            var intent = Intent(context,MyordersActivity::class.java)
                            ContextCompat.startActivity(context, intent, Bundle())
                            Toast.makeText(context,"Order cancelled", Toast.LENGTH_LONG).show()
                            progressDialog.dismiss()

                        }
                        else{
                            Toast.makeText(context, "Order not cancelled", Toast.LENGTH_LONG)
                                .show()
                            progressDialog.dismiss()
                        }
                    }


                    override fun onFailure(call: Call<Void?>, t: Throwable) {

                    }

                })
            }

        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img = itemView.findViewById<ImageView>(R.id.imgg)
        var names = itemView.findViewById<TextView>(R.id.nametext)
        var price = itemView.findViewById<TextView>(R.id.pricetext)
        var ttype=itemView.findViewById<TextView>(R.id.typetext)
        var countt = itemView.findViewById<TextView>(R.id.itemtext)
        var cancelbtn = itemView.findViewById<Button>(R.id.cancelbutton)


    }
}