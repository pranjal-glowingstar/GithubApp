package com.apps.githubapp.common.di

import android.content.Context
import androidx.room.Room
import com.apps.githubapp.common.AppUtils
import com.apps.githubapp.data.local.GithubDatabase
import com.apps.githubapp.data.local.dao.RepositoryDao
import com.apps.githubapp.data.local.dao.UserDao
import com.apps.githubapp.data.local.dao.UserSummaryDao
import com.apps.githubapp.data.remote.GithubApiService
import com.apps.githubapp.data.remote.TokenInterceptor
import com.apps.githubapp.repository.GithubLocalRepositoryImpl
import com.apps.githubapp.repository.GithubRepositoryImpl
import com.apps.githubapp.repository.IGithubLocalRepository
import com.apps.githubapp.repository.IGithubRepository
import com.google.gson.Gson
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
    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(AppUtils.AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(
                OkHttpClient.Builder().addInterceptor(TokenInterceptor()).build()
            ).build()
    }

    @Provides
    @Singleton
    fun getGithubService(retrofit: Retrofit): GithubApiService {
        return retrofit.create(GithubApiService::class.java)
    }

    @Provides
    @Singleton
    fun getDatabaseInstance(@ApplicationContext context: Context, gson: Gson): GithubDatabase {
        return Room.databaseBuilder(
            context,
            GithubDatabase::class.java,
            AppUtils.AppConstants.DATABASE_NAME
        ).build()
    }

    @Provides
    fun getSummaryDao(database: GithubDatabase): UserSummaryDao {
        return database.getSummaryDao()
    }

    @Provides
    fun getUserDao(database: GithubDatabase): UserDao {
        return database.getUserDao()
    }

    @Provides
    fun getRepositoryDao(database: GithubDatabase): RepositoryDao {
        return database.getRepositoryDao()
    }

    @Provides
    @Singleton
    fun getGson(): Gson {
        return Gson()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class IGithubModule {

    @Binds
    @Singleton
    abstract fun provideGithubRepository(githubRepositoryImpl: GithubRepositoryImpl): IGithubRepository

    @Binds
    @Singleton
    abstract fun provideGithubLocalRepository(githubLocalRepositoryImpl: GithubLocalRepositoryImpl): IGithubLocalRepository
}