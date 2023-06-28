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
package ru.piotr.features.home.api.domains.repository

import ru.piotr.core.utils.functional.TimeRange
import ru.piotr.features.home.api.domains.entities.schedules.Schedule

/**
 * @author Stanislav Aleshin on 25.02.2023.
 */
interface ScheduleRepository {
    suspend fun createSchedules(schedules: List<Schedule>)
    suspend fun fetchSchedulesByRange(timeRange: TimeRange?): List<Schedule>
    suspend fun fetchScheduleByDate(date: Long): Schedule?
    suspend fun updateSchedule(schedule: Schedule)
    suspend fun deleteAllSchedules()
}
