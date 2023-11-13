package com.example.codingchallenge

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.example.codingchallenge.navigation.Screen
import com.example.codingchallenge.ui.theme.CodingChallengeTheme
import com.example.podcast_presentation.podcast_detail.PodcastDetailScreen
import com.example.podcast_presentation.podcast_list.PodcastListScreen
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodingChallengeTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.PodcastList.route,
                        modifier = Modifier.padding(
                                bottom = paddingValues.calculateBottomPadding(),
                                top = paddingValues.calculateTopPadding()
                        )
                    ) {
                        addPodcastList(
                            navController = navController,
                            scaffoldState = scaffoldState,
                        )
                        addPodcastDetail(
                            navController = navController,
                            scaffoldState = scaffoldState,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@ExperimentalComposeUiApi
fun NavGraphBuilder.addPodcastList(
    navController: NavController,
    scaffoldState: ScaffoldState,
) {
    composable(
        route = Screen.PodcastList.route,
    ){
        PodcastListScreen(
            scaffoldState = scaffoldState,
            onNavigateToDetail = { podcastId ->
                navController.navigate("${Screen.PodcastDetail.route}/$podcastId")
            },
        )
    }
}

@OptIn(ExperimentalCoilApi::class)
@ExperimentalComposeUiApi
fun NavGraphBuilder.addPodcastDetail(
    navController: NavController,
    scaffoldState: ScaffoldState,
) {
    composable(
        route = Screen.PodcastDetail.route + "/{podcastId}",
        arguments = Screen.PodcastDetail.arguments
    ){
        PodcastDetailScreen(
            scaffoldState = scaffoldState,
            navController = navController,
        )
    }
}
