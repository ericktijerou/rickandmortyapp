/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ericktijerou.rickandmortyapp.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ericktijerou.rickandmortyapp.ui.entity.EpisodeModelView
import com.ericktijerou.rickandmortyapp.ui.episode.EpisodeScreen
import com.ericktijerou.rickandmortyapp.ui.home.HomeScreen
import com.ericktijerou.rickandmortyapp.ui.util.Screen
import com.google.gson.Gson

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route,
    ) {
        composable(
            route = Screen.MainScreen.route,
            enterTransition = { tabEnterTransition() },
            exitTransition = { tabExitTransition() }
        ) {
            HomeScreen(
                viewModel = hiltViewModel(),
                onEpisodeClick = { navController.navigate(Screen.DetailScreen.route(it)) },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable(route = Screen.DetailScreen.route,
            arguments = listOf(navArgument(Screen.DetailScreen.ARG_EPISODE) {
                type = NavType.StringType
            }),
            enterTransition = { tabEnterTransition() },
            exitTransition = { tabExitTransition() }
        ) {
            val modelString = it.arguments?.getString(Screen.DetailScreen.ARG_EPISODE)
            val model = Gson().fromJson(modelString, EpisodeModelView::class.java)
            EpisodeScreen(
                episode = model,
                onBackPressed = { navController.navigateUp() },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun AnimatedContentTransitionScope<NavBackStackEntry>.tabExitTransition(
    duration: Int = 500
) = fadeOut(tween(duration / 2, easing = LinearEasing))

@OptIn(ExperimentalAnimationApi::class)
private fun AnimatedContentTransitionScope<NavBackStackEntry>.tabEnterTransition(
    duration: Int = 500,
    delay: Int = duration - 350
) = fadeIn(tween(duration, duration - delay))