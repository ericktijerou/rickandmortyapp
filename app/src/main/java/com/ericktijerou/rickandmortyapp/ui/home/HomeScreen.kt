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
package com.ericktijerou.rickandmortyapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.ericktijerou.rickandmortyapp.ui.component.EpisodeCard
import com.ericktijerou.rickandmortyapp.ui.component.LoadingItem
import com.ericktijerou.rickandmortyapp.ui.component.LoadingView
import com.ericktijerou.rickandmortyapp.ui.entity.EpisodeModelView

@Composable
fun HomeScreen(viewModel: HomeViewModel, onEpisodeClick: (EpisodeModelView) -> Unit, modifier: Modifier = Modifier) {
    LaunchedEffect(Unit) {
        viewModel.sendAction(HomeAction.FetchEpisodes)
    }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        when (val viewState = viewModel.episodeListState.collectAsState().value) {
            is EpisodeListViewState.Idle -> {}
            is EpisodeListViewState.Loading -> LoadingView(Modifier.fillMaxSize())
            is EpisodeListViewState.Error -> ErrorMessage(stringResource(id = viewState.error))
            is EpisodeListViewState.Data -> EpisodeList(viewState = viewState, onEpisodeClick)
        }
    }
}

@Composable
fun ErrorMessage(message: String) {
    Text(
        text = message,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 10.dp, start = 8.dp, end = 8.dp, top = 4.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .wrapContentHeight(Alignment.CenterVertically),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun EpisodeList(viewState: EpisodeListViewState.Data, onEpisodeClick: (EpisodeModelView) -> Unit) {
    val lazyItems = viewState.episodes.collectAsLazyPagingItems()
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                count = lazyItems.itemCount,
                key = lazyItems.itemKey(),
                contentType = lazyItems.itemContentType()
            ) { index ->
                lazyItems[index]?.let {
                    EpisodeCard(episode = it, onEpisodeClick = onEpisodeClick)
                }
            }
            item {
                Spacer(modifier = Modifier.height(4.dp))
            }
            lazyItems.apply {
                if (loadState.append is LoadState.Loading) {
                    item { LoadingItem(Modifier.fillMaxWidth()) }
                }
            }
        }
    }
}