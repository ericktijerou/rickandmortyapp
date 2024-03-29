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
package com.ericktijerou.rickandmortyapp.data.local.system

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PagingManager @Inject constructor(context: Context) {

    companion object {
        private const val PREF_PACKAGE_NAME = "com.ericktijerou.rickandmorty.preferences"
        private const val PREF_KEY_LAST_EPISODE_CURSOR = "last_episode_cursor"
    }

    private val pref: SharedPreferences =
        context.getSharedPreferences(PREF_PACKAGE_NAME, Context.MODE_PRIVATE)

    var lastEpisodeCursor: Int
        get() = pref.getInt(PREF_KEY_LAST_EPISODE_CURSOR, 1)
        set(lastCursor) = pref.edit().putInt(PREF_KEY_LAST_EPISODE_CURSOR, lastCursor).apply()
}