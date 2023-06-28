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
package ru.piotr.features.home.impl.domain.interactors

import ru.piotr.core.utils.extensions.isCurrentDay
import ru.piotr.core.utils.extensions.shiftMinutes
import ru.piotr.core.utils.functional.TimeShiftException
import ru.piotr.core.utils.functional.UnitDomainResult
import ru.piotr.features.home.api.domains.entities.schedules.TimeTask
import ru.piotr.features.home.api.domains.repository.TimeTaskRepository
import ru.piotr.features.home.impl.domain.common.HomeEitherWrapper
import ru.piotr.features.home.impl.domain.entities.HomeFailures
import ru.piotr.features.home.impl.domain.entities.TimeTaskImportanceException
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 04.04.2023.
 */
internal interface TimeShiftInteractor {

    suspend fun shiftUpTimeTask(task: TimeTask, shiftValue: Int): UnitDomainResult<HomeFailures>

    suspend fun shiftDownTimeTask(task: TimeTask, shiftValue: Int): UnitDomainResult<HomeFailures>

    class Base @Inject constructor(
        private val timeTaskRepository: TimeTaskRepository,
        private val eitherWrapper: HomeEitherWrapper,
    ) : TimeShiftInteractor {

        override suspend fun shiftUpTimeTask(
            task: TimeTask,
            shiftValue: Int,
        ) = eitherWrapper.wrap {
            val allTimeTasks = timeTaskRepository.fetchAllTimeTaskByDate(task.date).sortedBy {
                it.timeRanges.from
            }
            val nextTimeTask = allTimeTasks.firstOrNull { it.timeRanges.from >= task.timeRanges.to }
            val nextTime = nextTimeTask?.timeRanges
            val shiftTime = task.timeRanges.to.shiftMinutes(shiftValue)

            if (nextTime == null || nextTime.from.time - shiftTime.time >= shiftValue) {
                when (shiftTime.isCurrentDay(task.timeRanges.to)) {
                    true -> timeTaskRepository.updateTimeTask(
                        timeTask = task.copy(timeRanges = task.timeRanges.copy(to = shiftTime)),
                    )
                    false -> throw TimeShiftException()
                }
            } else {
                when (nextTime.to.time - shiftTime.time > 0) {
                    true -> {
                        if (nextTimeTask.isImportant) throw TimeTaskImportanceException()
                        timeTaskRepository.updateTimeTask(
                            timeTask = task.copy(timeRanges = task.timeRanges.copy(to = shiftTime)),
                        )
                        timeTaskRepository.updateTimeTask(
                            timeTask = nextTimeTask.copy(timeRanges = nextTimeTask.timeRanges.copy(from = shiftTime)),
                        )
                    }
                    false -> throw TimeShiftException()
                }
            }
        }

        override suspend fun shiftDownTimeTask(
            task: TimeTask,
            shiftValue: Int,
        ) = eitherWrapper.wrap {
            val shiftTime = task.timeRanges.to.shiftMinutes(-shiftValue)
            if (shiftTime.time - task.timeRanges.from.time > 0) {
                val timeRanges = task.timeRanges.copy(to = shiftTime)
                timeTaskRepository.updateTimeTask(task.copy(timeRanges = timeRanges))
            } else {
                throw TimeShiftException()
            }
        }
    }
}
