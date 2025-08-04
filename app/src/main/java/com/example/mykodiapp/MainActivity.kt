package com.example.mykodiapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mykodiapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var genreAdapter: GenreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        fetchGenres()
    }

    private fun setupRecyclerView() {
        genreAdapter = GenreAdapter { genre ->
            val intent = Intent(this, VideoListActivity::class.java).apply {
                putExtra("GENRE_ID", genre.page_id)
                putExtra("GENRE_TITLE", genre.name)
            }
            startActivity(intent)
        }
        binding.genresRecyclerView.apply {
            adapter = genreAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun fetchGenres() {
        binding.progressBar.visibility = View.VISIBLE
        binding.genresRecyclerView.visibility = View.GONE
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getGenres()
                binding.progressBar.visibility = View.GONE
                binding.genresRecyclerView.visibility = View.VISIBLE
                if (response.isSuccessful && response.body() != null) {
                    genreAdapter.submitList(response.body()?.chats ?: emptyList())
                } else {
                    Toast.makeText(this@MainActivity, "Error: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this@MainActivity, "Network Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}

