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
package ru.piotr.features.editor.impl.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.piotr.core.utils.extensions.duration
import ru.piotr.core.utils.functional.Mapper
import ru.piotr.core.utils.functional.TimeRange
import ru.piotr.features.home.api.domains.entities.categories.MainCategory
import ru.piotr.features.home.api.domains.entities.categories.SubCategory
import ru.piotr.features.home.api.domains.entities.lockapp.LockApp
import java.util.Date

/**
 * @author Stanislav Aleshin on 16.05.2023.
 */
@Parcelize
internal data class EditModelUi(
    val key: Long = 0L,
    val date: Date,
    val timeRanges: TimeRange,
    val duration: Long = duration(timeRanges.from, timeRanges.to),
    val mainCategory: MainCategory = MainCategory.absent(),
    val subCategory: SubCategory? = null,
    val isCompleted: Boolean = true,
    val parameters: EditParameters = EditParameters(),
    val templateId: Int? = null,
    val lockedApps: List<LockApp>?= emptyList(),
) : Parcelable {
    fun <T> map(mapper: Mapper<EditModelUi, T>) = mapper.map(this)
}
