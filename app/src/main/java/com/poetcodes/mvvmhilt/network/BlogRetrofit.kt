package com.poetcodes.mvvmhilt.network

import com.poetcodes.mvvmhilt.network.response.BlogNetworkEntity
import retrofit2.http.GET

interface BlogRetrofit {

    @GET("blogs")
    suspend fun getNetworkBlogs(): List<BlogNetworkEntity>
}