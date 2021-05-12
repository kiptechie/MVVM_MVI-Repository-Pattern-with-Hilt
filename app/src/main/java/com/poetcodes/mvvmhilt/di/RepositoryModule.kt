package com.poetcodes.mvvmhilt.di

import com.poetcodes.mvvmhilt.local.BlogDao
import com.poetcodes.mvvmhilt.local.CacheMapper
import com.poetcodes.mvvmhilt.network.BlogRetrofit
import com.poetcodes.mvvmhilt.network.NetworkMapper
import com.poetcodes.mvvmhilt.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        blogDao: BlogDao,
        blogRetrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): MainRepository {
        return MainRepository(
            blogDao,
            blogRetrofit,
            cacheMapper,
            networkMapper
        )
    }
}