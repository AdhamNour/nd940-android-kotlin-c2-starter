package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.ListItemAstroidBinding

class AstroidAdapter : ListAdapter<Asteroid, AsteroidViewHolder>(AstroidDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}

class AsteroidViewHolder private constructor(val binding: ListItemAstroidBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(asteroid: Asteroid) {
        binding.asteroid= asteroid;
    }

    companion object {
        fun from(parent: ViewGroup): AsteroidViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemAstroidBinding.inflate(layoutInflater, parent, false)
            return AsteroidViewHolder(binding)
        }
    }
}

class AstroidDiffCallback : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }

}