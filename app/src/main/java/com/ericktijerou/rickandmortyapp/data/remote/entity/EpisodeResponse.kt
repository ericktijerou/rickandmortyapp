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
package com.ericktijerou.rickandmortyapp.data.remote.entity

import com.ericktijerou.rickandmortyapp.core.orZero
import com.ericktijerou.rickandmortyapp.data.entity.EpisodeModel
import com.google.gson.annotations.SerializedName

data class EpisodeResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("air_date") var airDate: String? = null,
    @SerializedName("episode") var episode: String? = null,
    @SerializedName("characters") var characters: List<String> = emptyList(),
    @SerializedName("url") var url: String? = null,
    @SerializedName("created") var created: String? = null
)

fun EpisodeResponse.toData(): EpisodeModel {
    return EpisodeModel(
        id = id.orZero(),
        name = name.orEmpty(),
        airDate = airDate.orEmpty(),
        episode = episode.orEmpty(),
        characters = characters,
        url = url.orEmpty(),
        created = created.orEmpty()
    )
}