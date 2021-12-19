package com.example.eater

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class AdapterClass(var songs:MutableList<DishData>, var context: Context):RecyclerView.Adapter<AdapterClass.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater:LayoutInflater= LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.items,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder,position: Int) {
        holder.dishName.text=songs[position].name

        holder.dishType.text="Type: ${songs[position].type}"
        holder.dishPrice.text="${songs[position].price}$ per serving"

        Picasso.get().load(songs[position].url).into(holder.dishImage);
    }

    override fun getItemCount(): Int {
        return songs.size
    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val dishImage = itemView.findViewById<ImageView>(R.id.dishimage)
        val dishName = itemView.findViewById<TextView>(R.id.dishname)
        val dishType=itemView.findViewById<TextView>(R.id.dishtype)
        val dishPrice=itemView.findViewById<TextView>(R.id.dishprice)


        var id: Int = 0

        init {
            itemView.setOnClickListener {
                id = adapterPosition
                val intent = Intent(context, SpecificDishActivity::class.java)
                intent.putExtra("ID", songs[id].id)
                startActivity(context, intent, Bundle())
            }
        }
    }

}