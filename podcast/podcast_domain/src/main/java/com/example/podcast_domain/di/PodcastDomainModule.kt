package com.example.podcast_domain.di

import com.example.podcast_domain.repository.PodcastRepository
import com.example.podcast_domain.use_case.GetPodcastDetail
import com.example.podcast_domain.use_case.GetPodcastList
import com.example.podcast_domain.use_case.PodcastUseCases
import com.example.podcast_domain.use_case.UpdatePodcast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PodcastDomainModule {

    @Singleton
    @Provides
    fun providePodcastUseCases(
        repository: PodcastRepository
    ): PodcastUseCases {
        return PodcastUseCases(
            getPodcastList = GetPodcastList(repository),
            getPodcastDetail = GetPodcastDetail(repository),
            updatePodcast = UpdatePodcast(repository)
        )
    }
}