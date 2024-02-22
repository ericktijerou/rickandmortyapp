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

import androidx.paging.PagingData
import com.ericktijerou.rickandmortyapp.ui.entity.EpisodeModelView
import kotlinx.coroutines.flow.Flow

sealed class EpisodeListViewState {
   object Idle : EpisodeListViewState()
   object Loading : EpisodeListViewState()
   data class Data(val episodes: Flow<PagingData<EpisodeModelView>>) : EpisodeListViewState()
   data class Error(val error: Int) : EpisodeListViewState()
}