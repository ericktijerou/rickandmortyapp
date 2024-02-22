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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.ericktijerou.rickandmortyapp.R
import com.ericktijerou.rickandmortyapp.domain.usecase.GetEpisodeListUseCase
import com.ericktijerou.rickandmortyapp.ui.entity.toView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getEpisodeListUseCase: GetEpisodeListUseCase,
) : ViewModel() {

    private val userAction = Channel<HomeAction>(Channel.UNLIMITED)

    private val _episodeListState =
        MutableStateFlow<EpisodeListViewState>(EpisodeListViewState.Idle)

    val episodeListState: StateFlow<EpisodeListViewState>
        get() = _episodeListState

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userAction.consumeAsFlow().collect {
                when (it) {
                    is HomeAction.FetchEpisodes -> fetchEpisodes()
                }
            }
        }
    }

    private fun fetchEpisodes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _episodeListState.emit(EpisodeListViewState.Loading)
                val result = getEpisodeListUseCase().map { pagingData -> pagingData.map { it.toView() } }.cachedIn(viewModelScope)
                _episodeListState.emit(EpisodeListViewState.Data(result))
            } catch (e: Exception) {
                _episodeListState.emit(EpisodeListViewState.Error(R.string.error_message))
            }
        }
    }

    fun sendAction(action: HomeAction) {
        viewModelScope.launch(Dispatchers.IO) {
            userAction.send(action)
        }
    }
}