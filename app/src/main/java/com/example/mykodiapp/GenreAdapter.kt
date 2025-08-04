package com.example.mykodiapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GenreAdapter(private val onItemClicked: (Genre) -> Unit) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {
    private var genres: List<Genre> = emptyList()

    fun submitList(newGenres: List<Genre>) {
        genres = newGenres
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return GenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = genres[position]
        holder.bind(genre)
        holder.itemView.setOnClickListener { onItemClicked(genre) }
    }

    override fun getItemCount() = genres.size

    class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(android.R.id.text1)
        fun bind(genre: Genre) {
            textView.text = genre.name
        }
    }
}

