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
 * limitations under the License.
*/
package ru.aleshin.features.home.api.data.models.schedules

import androidx.room.Embedded
import androidx.room.Relation
import ru.aleshin.core.utils.functional.Mapper
import ru.aleshin.features.home.api.data.models.timetasks.TimeTaskDetails
import ru.aleshin.features.home.api.data.models.timetasks.TimeTaskEntity

/**
 * @author Stanislav Aleshin on 21.02.2023.
 */
data class ScheduleDetails(
    @Embedded
    val dailySchedule: DailyScheduleEntity,
    @Relation(
        parentColumn = "date",
        entityColumn = "daily_schedule_date",
        entity = TimeTaskEntity::class,
    )
    val timeTasks: List<TimeTaskDetails>,
) {
    fun <T> map(mapper: Mapper<ScheduleDetails, T>) = mapper.map(this)
}
