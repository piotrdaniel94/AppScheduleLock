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
package ru.piotr.features.home.api.data.models.timetasks

import androidx.room.*
import ru.piotr.features.home.api.data.models.categories.MainCategoryEntity
import ru.piotr.features.home.api.data.models.categories.SubCategoryEntity
import ru.piotr.features.home.api.data.models.schedules.DailyScheduleEntity

/**
 * @author Stanislav Aleshin on 21.02.2023.
 */
@Entity(
    tableName = "timeTasks",
    foreignKeys = [
        ForeignKey(
            entity = DailyScheduleEntity::class,
            parentColumns = arrayOf("date"),
            childColumns = arrayOf("daily_schedule_date"),
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = MainCategoryEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("main_category_id"),
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = SubCategoryEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("sub_category_id"),
            onDelete = ForeignKey.SET_NULL,
        ),
    ],
)
data class TimeTaskEntity(
    @PrimaryKey(autoGenerate = false) val key: Long,
    @ColumnInfo("daily_schedule_date", index = true) val dailyScheduleDate: Long,
    @ColumnInfo("start_time") val startTime: Long,
    @ColumnInfo("end_time") val endTime: Long,
    @ColumnInfo("main_category_id", index = true) val mainCategoryId: Int,
    @ColumnInfo("sub_category_id", index = true) val subCategoryId: Int?,
    @ColumnInfo("is_completed", defaultValue = "1") val isCompleted: Boolean,
    @ColumnInfo("is_important") val isImportant: Boolean,
    @ColumnInfo("is_enable_notification") val isEnableNotification: Boolean,
    @ColumnInfo("is_consider_in_statistics") val isConsiderInStatistics: Boolean,
)
