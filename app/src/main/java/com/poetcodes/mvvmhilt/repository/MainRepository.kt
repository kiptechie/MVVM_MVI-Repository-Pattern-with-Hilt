package com.poetcodes.mvvmhilt.repository

import com.poetcodes.mvvmhilt.local.BlogDao
import com.poetcodes.mvvmhilt.local.CacheMapper
import com.poetcodes.mvvmhilt.models.Blog
import com.poetcodes.mvvmhilt.network.BlogRetrofit
import com.poetcodes.mvvmhilt.network.NetworkMapper
import com.poetcodes.mvvmhilt.utils.DataState
import kotlinx.coroutines.flow.flow
import java.util.concurrent.Flow

class MainRepository
constructor(
    private val blogDao: BlogDao,
    private val blogRetrofit: BlogRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
) {

    suspend fun getBlogs() = flow {
        emit(DataState.Loading)
        kotlinx.coroutines.delay(1_000)
        try {
            val networkBlogs = blogRetrofit.getNetworkBlogs()
            val blogs = networkMapper.mapFromEntityList(networkBlogs)
            for (blog in blogs) {
                blogDao.insert(cacheMapper.mapToEntity(blog))
            }
            val cachedBlogs = blogDao.getLocalBlogs()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedBlogs)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}