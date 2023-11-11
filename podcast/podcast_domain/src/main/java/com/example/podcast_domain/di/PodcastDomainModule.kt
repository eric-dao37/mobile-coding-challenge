package com.example.podcast_domain.di

import com.example.podcast_domain.repository.PodcastRepository
import com.example.podcast_domain.use_case.GetPodcast
import com.example.podcast_domain.use_case.PodcastUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
object PodcastDomainModule {

    @ViewModelScoped
    @Provides
    fun providePodcastUseCases(
        repository: PodcastRepository
    ): PodcastUseCases {
        return PodcastUseCases(
            getPodcast = GetPodcast(repository)
        )
    }
}