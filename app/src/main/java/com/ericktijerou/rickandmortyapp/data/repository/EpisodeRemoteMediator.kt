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
package com.ericktijerou.rickandmortyapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.ericktijerou.rickandmortyapp.core.ApiException
import com.ericktijerou.rickandmortyapp.core.orZero
import com.ericktijerou.rickandmortyapp.data.local.EpisodeDataStore
import com.ericktijerou.rickandmortyapp.data.local.entity.EpisodeEntity
import com.ericktijerou.rickandmortyapp.data.remote.EpisodeCloudStore
import java.io.IOException

@ExperimentalPagingApi
class EpisodeRemoteMediator(
    private val local: EpisodeDataStore,
    private val remote: EpisodeCloudStore,
) : RemoteMediator<Int, EpisodeEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EpisodeEntity>
    ): MediatorResult {
        val cursor = when (val pageKeyData = getKeyPageData(loadType)) {
            is MediatorResult.Success -> return pageKeyData
            else -> pageKeyData as? Int
        }
        return try {
            val (hasNextPage, list) = remote.getEpisodeResponse(
                cursor ?: 1
            )
            local.doOperationInTransaction {
                if (loadType == LoadType.REFRESH) local.clearEpisodes()
                local.setLastEpisodeCursor(cursor.orZero().plus(1))
                local.saveEpisodeList(list.orEmpty())
            }
            MediatorResult.Success(endOfPaginationReached = !hasNextPage)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: ApiException) {
            MediatorResult.Error(exception)
        }
    }

    private fun getKeyPageData(loadType: LoadType): Any? {
        return when (loadType) {
            LoadType.REFRESH -> null
            LoadType.PREPEND -> MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> local.getLastEpisodeCursor()
        }
    }
}