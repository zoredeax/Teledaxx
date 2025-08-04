package com.example.mykodiapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Data classes for JSON parsing
data class Genre(val page_id: String, val name: String)
data class Video(val insight: String, val download: String, val poster: String, val size: String?, val date: String?)
data class ApiResponse(val chats: List<Genre>?, val item_list: List<Video>?)

// API endpoint definitions
interface ApiService {
    @GET("/")
    suspend fun getGenres(@Query("api") api: String = "1"): Response<ApiResponse>

    @GET("{genre_id}")
    suspend fun getVideos(@Path("genre_id") genreId: String, @Query("page") page: Int = 1, @Query("api") api: String = "1"): Response<ApiResponse>

    @GET("search/{genre_id}")
    suspend fun searchVideos(@Path("genre_id") genreId: String, @Query("q") query: String, @Query("api") api: String = "1"): Response<ApiResponse>
}

