package com.apps.githubapp.common.di

import com.apps.githubapp.data.remote.GithubApiService
import com.apps.githubapp.data.remote.TokenInterceptor
import com.apps.githubapp.repository.GithubRepositoryImpl
import com.apps.githubapp.repository.IGithubRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GithubModule {

    @Provides
    @Singleton
    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create()).client(
            OkHttpClient.Builder().addInterceptor(TokenInterceptor()).build()
        ).build()
    }

    @Provides
    fun getGithubService(retrofit: Retrofit): GithubApiService {
        return retrofit.create(GithubApiService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class IGithubModule {

    @Binds
    @Singleton
    abstract fun provideGithubRepository(githubRepositoryImpl: GithubRepositoryImpl): IGithubRepository
}