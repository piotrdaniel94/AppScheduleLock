/*
 * Copyright 2023 Stanislav Aleshin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * imitations under the License.
 */
package ru.piotr.features.home.api.domains.entities.schedules

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.piotr.core.utils.functional.Mapper
import ru.piotr.core.utils.functional.TimeRange
import ru.piotr.features.home.api.domains.entities.categories.MainCategory
import ru.piotr.features.home.api.domains.entities.categories.SubCategory
import java.util.Date

/**
 * @author Stanislav Aleshin on 23.02.2023.
 */
@Parcelize
data class TimeTask(
    val key: Long = 0L,
    val date: Date,
    val timeRanges: TimeRange,
    val category: MainCategory,
    val subCategory: SubCategory? = null,
    val isCompleted: Boolean = true,
    val isImportant: Boolean = false,
    val isEnableNotification: Boolean = true,
    val isConsiderInStatistics: Boolean = true,
) : Parcelable {
    fun <T> map(mapper: Mapper<TimeTask, T>) = mapper.map(this)
}
