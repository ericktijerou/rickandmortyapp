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
package com.ericktijerou.rickandmortyapp.di

import com.ericktijerou.rickandmortyapp.data.remote.EpisodeCloudStore
import com.ericktijerou.rickandmortyapp.data.remote.api.RickAndMortyApi
import com.ericktijerou.rickandmortyapp.data.remote.util.buildOkHttpClient
import com.ericktijerou.rickandmortyapp.data.remote.util.buildRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return buildOkHttpClient()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): RickAndMortyApi {
        return buildRetrofit(okHttpClient).create(RickAndMortyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEpisodeCloudStore(api: RickAndMortyApi): EpisodeCloudStore {
        return EpisodeCloudStore(api)
    }
}
