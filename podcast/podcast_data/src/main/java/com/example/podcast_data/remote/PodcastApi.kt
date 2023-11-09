package com.example.podcast_data.remote

import com.example.podcast_data.remote.EndPoints.GET_PODCAST_URL
import com.example.podcast_data.remote.dto.GetPodcastDto
import retrofit2.http.GET

interface PodcastApi {

    @GET(GET_PODCAST_URL)
    suspend fun getPodcasts() : GetPodcastDto

}