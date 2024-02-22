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
package com.ericktijerou.rickandmortyapp.data.local

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.ericktijerou.rickandmortyapp.data.entity.EpisodeModel
import com.ericktijerou.rickandmortyapp.data.entity.toLocal
import com.ericktijerou.rickandmortyapp.data.local.dao.EpisodeDao
import com.ericktijerou.rickandmortyapp.data.local.entity.EpisodeEntity
import com.ericktijerou.rickandmortyapp.data.local.system.AppDatabase
import com.ericktijerou.rickandmortyapp.data.local.system.PagingManager
import javax.inject.Inject

class EpisodeDataStore @Inject constructor(
    private val dao: EpisodeDao,
    private val database: AppDatabase,
    private val pagingManager: PagingManager
) {
    fun getEpisodeList(): PagingSource<Int, EpisodeEntity> {
        return dao.getAll()
    }

    suspend fun saveEpisodeList(list: List<EpisodeModel>) {
        dao.insert(*list.map { it.toLocal() }.toTypedArray())
    }

    suspend fun doOperationInTransaction(method: suspend () -> Unit) {
        database.withTransaction {
            method()
        }
    }

    suspend fun clearEpisodes() {
        dao.clearAll()
    }

    fun getLastEpisodeCursor(): Int = pagingManager.lastEpisodeCursor

    fun setLastEpisodeCursor(value: Int) {
        pagingManager.lastEpisodeCursor = value
    }
}