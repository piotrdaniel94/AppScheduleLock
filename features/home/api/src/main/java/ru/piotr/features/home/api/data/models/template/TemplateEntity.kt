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
package ru.piotr.features.home.api.data.models.template

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.piotr.features.home.api.data.models.categories.MainCategoryEntity
import ru.piotr.features.home.api.data.models.categories.SubCategoryEntity
import java.util.*

/**
 * @author Stanislav Aleshin on 08.03.2023.
 */
@Entity(
    tableName = "timeTaskTemplates",
    foreignKeys = [
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
data class TemplateEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("start_time") val startTime: Long,
    @ColumnInfo("end_time") val endTime: Long,
    @ColumnInfo("main_category_id", index = true) val categoryId: Int,
    @ColumnInfo("sub_category_id", index = true) val subCategoryId: Int? = null,
    @ColumnInfo("is_important") val isImportant: Boolean,
    @ColumnInfo("is_enable_notification") val isEnableNotification: Boolean,
    @ColumnInfo("is_consider_in_statistics") val isConsiderInStatistics: Boolean,
)
