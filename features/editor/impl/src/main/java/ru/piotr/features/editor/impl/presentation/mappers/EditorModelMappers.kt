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
package ru.piotr.features.editor.impl.presentation.mappers

import ru.piotr.core.utils.extensions.duration
import ru.piotr.core.utils.functional.TimeRange
import ru.piotr.features.editor.impl.domain.entites.EditModel
import ru.piotr.features.editor.impl.presentation.models.EditModelUi
import ru.piotr.features.editor.impl.presentation.models.EditParameters

/**
 * @author Stanislav Aleshin on 16.05.2023.
 */
internal fun EditModel.mapToUi() = EditModelUi(
    key = key,
    date = date,
    timeRanges = TimeRange(startTime, endTime),
    duration = duration(startTime, endTime),
    mainCategory = mainCategory,
    subCategory = subCategory,
    parameters = EditParameters(isImportant, isEnableNotification, isConsiderInStatistics),
    isCompleted = isCompleted,
    templateId = templateId,
    lockedApps = lockedApps,
)

internal fun EditModelUi.mapToDomain() = EditModel(
    key = key,
    date = date,
    startTime = timeRanges.from,
    endTime = timeRanges.to,
    mainCategory = mainCategory,
    subCategory = subCategory,
    isCompleted = isCompleted,
    isImportant = parameters.isImportant,
    isEnableNotification = parameters.isEnableNotification,
    isConsiderInStatistics = parameters.isConsiderInStatistics,
    templateId = templateId,
    lockedApps = lockedApps,
)
