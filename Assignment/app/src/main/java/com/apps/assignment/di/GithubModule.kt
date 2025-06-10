package com.apps.assignment.di

import android.content.Context
import com.apps.assignment.data.remote.GithubApiService
import com.apps.assignment.data.remote.TokenInterceptor
import com.apps.assignment.repository.GithubRepositoryImpl
import com.apps.assignment.repository.IGithubRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun getRetrofitInstance(@ApplicationContext context: Context): Retrofit{
        return Retrofit.Builder().baseUrl("https://api.github.com/").addConverterFactory(GsonConverterFactory.create()).client(
            OkHttpClient.Builder().addInterceptor(TokenInterceptor(context)).build()
        ).build()
    }

    @Provides
    fun getGithubService(retrofit: Retrofit): GithubApiService{
        return retrofit.create(GithubApiService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class IGithubModule{

    @Binds
    @Singleton
    abstract fun provideGithubRepository(githubRepositoryImpl: GithubRepositoryImpl): IGithubRepository
}