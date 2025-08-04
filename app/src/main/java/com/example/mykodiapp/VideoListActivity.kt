package com.example.mykodiapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mykodiapp.databinding.ActivityVideoListBinding
import kotlinx.coroutines.launch

class VideoListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoListBinding
    private lateinit var videoAdapter: VideoAdapter
    private var genreId: String? = null
    private var genreTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        genreId = intent.getStringExtra("GENRE_ID")
        genreTitle = intent.getStringExtra("GENRE_TITLE")

        binding.toolbar.title = genreTitle ?: "Videos"
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupRecyclerView()
        if (genreId != null) fetchVideos(genreId!!) else finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.video_list_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank() && genreId != null) {
                    searchForVideos(genreId!!, query)
                    searchView.clearFocus()
                }
                return true
            }
            override fun onQueryTextChange(newText: String?) = true
        })
        searchItem.setOnActionExpandListener(object : android.view.MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: android.view.MenuItem) = true
            override fun onMenuItemActionCollapse(item: android.view.MenuItem): Boolean {
                if (genreId != null) fetchVideos(genreId!!)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupRecyclerView() {
        videoAdapter = VideoAdapter { video ->
            // !!! IMPORTANT: Construct the full URL correctly !!!
            val fullVideoUrl = "https://teledax.zorstream.dpdns.org/${video.download}"
            val intent = Intent(this, PlayerActivity::class.java).apply {
                putExtra("VIDEO_URL", fullVideoUrl)
            }
            startActivity(intent)
        }
        binding.videosRecyclerView.apply {
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(this@VideoListActivity)
        }
    }

    private fun fetchVideos(id: String) {
        showLoading(true)
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getVideos(genreId = id)
                if (response.isSuccessful && response.body() != null) {
                    videoAdapter.submitList(response.body()?.item_list ?: emptyList())
                } else { handleApiError("Error: ${response.message()}") }
            } catch (e: Exception) { handleApiError("Network Error: ${e.message}") }
            finally { showLoading(false) }
        }
    }

    private fun searchForVideos(id: String, query: String) {
        showLoading(true)
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.searchVideos(genreId = id, query = query)
                if (response.isSuccessful && response.body() != null) {
                    videoAdapter.submitList(response.body()?.item_list ?: emptyList())
                } else { handleApiError("Search Error: ${response.message()}") }
            } catch (e: Exception) { handleApiError("Network Error: ${e.message}") }
            finally { showLoading(false) }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.videoProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.videosRecyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    private fun handleApiError(message: String) {
        Toast.makeText(this@VideoListActivity, message, Toast.LENGTH_LONG).show()
    }
}

