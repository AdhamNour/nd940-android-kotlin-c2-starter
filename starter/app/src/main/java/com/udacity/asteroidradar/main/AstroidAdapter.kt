package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R

class AstroidAdapter: RecyclerView.Adapter<AsteroidViewHolder>() {
    var data = listOf<Asteroid>()
    set(value) {
        field=value;
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.astroid_list_itm, parent, false)
        return AsteroidViewHolder(view)


    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val item = data[position]
        holder.nameTextView.text=item.codename
    }

    override fun getItemCount()=data.size;
}

class AsteroidViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
    val nameTextView= itemView.findViewById<TextView>(R.id.name)
}