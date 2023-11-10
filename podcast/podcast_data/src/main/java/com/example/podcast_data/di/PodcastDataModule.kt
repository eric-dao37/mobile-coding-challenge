package com.example.podcast_data.di

import android.app.Application
import androidx.room.Room
import com.example.podcast_data.local.PodcastDatabase
import com.example.podcast_data.remote.EndPoints.BASE_URL
import com.example.podcast_data.remote.PodcastApi
import com.example.podcast_data.repository.PodcastRepositoryImpl
import com.example.podcast_domain.repository.PodcastRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PodcastDataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()

    @Provides
    @Singleton
    fun providePodcastApi(client: OkHttpClient): PodcastApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create()

    @Provides
    @Singleton
    fun providePodcastDatabase(app: Application): PodcastDatabase =
        Room.databaseBuilder(
            app,
            PodcastDatabase::class.java,
            "podcast_db"
        ).build()

    @Provides
    @Singleton
    fun providePodcastRepository(
        db: PodcastDatabase,
        api: PodcastApi,
    ): PodcastRepository =
        PodcastRepositoryImpl(
            dao = db.dao,
            api = api,
        )
}