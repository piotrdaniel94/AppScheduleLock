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
package ru.piotr.features.home.impl.presentation.mapppers

import ru.piotr.core.utils.extensions.duration
import ru.piotr.core.utils.functional.ParameterizedMapper
import ru.piotr.core.utils.functional.TimeRange
import ru.piotr.core.utils.managers.DateManager
import ru.piotr.features.home.api.domains.common.TimeTaskStatusManager
import ru.piotr.features.home.api.domains.entities.schedules.TimeTask
import ru.piotr.features.home.impl.presentation.models.TimeTaskUi
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 25.02.2023.
 */
internal interface TimeTaskDomainToUiMapper : ParameterizedMapper<TimeTask, TimeTaskUi, Boolean> {
    class Base @Inject constructor(
        private val dateManager: DateManager,
        private val statusManager: TimeTaskStatusManager,
    ) : TimeTaskDomainToUiMapper {
        override fun map(input: TimeTask, parameter: Boolean) = TimeTaskUi(
            executionStatus = statusManager.fetchStatus(
                input.timeRanges,
                dateManager.fetchCurrentDate(),
            ),
            key = input.key,
            date = input.date,
            startTime = input.timeRanges.from,
            endTime = input.timeRanges.to,
            duration = duration(input.timeRanges),
            leftTime = dateManager.calculateLeftTime(input.timeRanges.to),
            progress = dateManager.calculateProgress(input.timeRanges.from, input.timeRanges.to),
            mainCategory = input.category,
            subCategory = input.subCategory,
            isCompleted = input.isCompleted,
            isImportant = input.isImportant,
            isEnableNotification = input.isEnableNotification,
            isConsiderInStatistics = input.isConsiderInStatistics,
            isTemplate = parameter,
            lockApps = input.lockApps,
        )
    }
}

internal fun TimeTaskUi.mapToDomain() = TimeTask(
    key = key,
    date = date,
    timeRanges = TimeRange(startTime, endTime),
    category = mainCategory,
    subCategory = subCategory,
    isImportant = isImportant,
    isCompleted = isCompleted,
    isEnableNotification = isEnableNotification,
    isConsiderInStatistics = isConsiderInStatistics,
    lockApps = lockApps,
)
