package com.example.eater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class OtherusersAdapter(var UsersVal:MutableList<OtherusersData>, var context: Context):
        RecyclerView.Adapter<OtherusersAdapter.ViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): OtherusersAdapter.ViewHolder {
            val inflater: LayoutInflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.otheruserscontent, parent, false)
            return ViewHolder(view)

        }

        override fun getItemCount(): Int {
            return UsersVal.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val otherUserEmail = itemView.findViewById<TextView>(R.id.otheruserEmail)
            val ordereddish = itemView.findViewById<TextView>(R.id.ordereddishtext)
            val firstEmail=itemView.findViewById<TextView>(R.id.emailletter)

        }


        override fun onBindViewHolder(holder: OtherusersAdapter.ViewHolder, position: Int) {
            holder.otherUserEmail.text="${UsersVal[position].email}"
            holder.ordereddish.text = "Ordered: ${UsersVal[position].orderedDishes}"

            val emailId=UsersVal[position].email.toString()
            val photoText = emailId.substring(0, 1).uppercase(Locale.getDefault())
            holder.firstEmail.text= "$photoText"

        }

}