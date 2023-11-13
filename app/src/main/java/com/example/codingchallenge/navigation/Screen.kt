package com.example.codingchallenge.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String, val arguments: List<NamedNavArgument>){

    object PodcastList: Screen(
        route = "podcastList",
        arguments = emptyList()
    )

    object PodcastDetail: Screen(
        route = "podcastDetail",
        arguments = listOf(navArgument("podcastId") {
            type = NavType.StringType
        })
    )
}