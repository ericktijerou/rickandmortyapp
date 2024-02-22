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
package com.ericktijerou.rickandmortyapp.data.remote

import com.ericktijerou.rickandmortyapp.core.ApiException
import com.ericktijerou.rickandmortyapp.core.NotFoundException
import com.ericktijerou.rickandmortyapp.data.entity.EpisodeModel
import com.ericktijerou.rickandmortyapp.data.remote.api.RickAndMortyApi
import com.ericktijerou.rickandmortyapp.data.remote.entity.toData
import javax.inject.Inject

class EpisodeCloudStore @Inject constructor(private val api: RickAndMortyApi) {

    suspend fun getEpisodeResponse(
        page: Int
    ): Pair<Boolean, List<EpisodeModel>?> {
        val response = api.getEpisodeList(page)
        return try {
            (response.info.next.orEmpty().isNotEmpty() to response.results?.map { it.toData() })
        } catch (e: Exception) {
            throw ApiException()
        }
    }
}