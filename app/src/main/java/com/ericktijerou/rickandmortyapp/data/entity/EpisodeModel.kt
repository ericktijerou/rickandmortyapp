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
package com.ericktijerou.rickandmortyapp.data.entity

import com.ericktijerou.rickandmortyapp.core.orZero
import com.ericktijerou.rickandmortyapp.data.local.entity.EpisodeEntity


data class EpisodeModel(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)

fun EpisodeModel.toLocal(): EpisodeEntity {
    return EpisodeEntity(
        id = id,
        name = name.orEmpty(),
        airDate = airDate.orEmpty(),
        episode = episode.orEmpty(),
        characters = characters,
        url = url.orEmpty(),
        created = created.orEmpty()
    )
}