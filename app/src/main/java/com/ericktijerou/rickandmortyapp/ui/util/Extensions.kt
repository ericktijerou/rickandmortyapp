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
package com.ericktijerou.rickandmortyapp.ui.util

import androidx.compose.ui.graphics.Color
import java.util.*

fun randomColorHex(): String {
    val obj = Random()
    val randNum: Int = obj.nextInt(0xffffff + 1)
    return String.format("%06x", randNum)
}

fun String.toColor(): Color {
    return Color(android.graphics.Color.parseColor("#$this"))
}