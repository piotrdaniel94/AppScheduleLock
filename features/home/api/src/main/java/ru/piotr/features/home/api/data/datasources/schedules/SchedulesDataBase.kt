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
package ru.piotr.features.home.api.data.datasources.schedules

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import ru.piotr.features.home.api.data.datasources.categories.MainCategoriesDao
import ru.piotr.features.home.api.data.datasources.lockapps.LockedAppEntity
import ru.piotr.features.home.api.data.datasources.lockapps.LockedAppsDao
import ru.piotr.features.home.api.data.datasources.subcategories.SubCategoriesDao
import ru.piotr.features.home.api.data.datasources.templates.TemplatesDao
import ru.piotr.features.home.api.data.models.categories.MainCategoryEntity
import ru.piotr.features.home.api.data.models.categories.SubCategoryEntity
import ru.piotr.features.home.api.data.models.schedules.DailyScheduleEntity
import ru.piotr.features.home.api.data.models.template.TemplateEntity
import ru.piotr.features.home.api.data.models.timetasks.TimeTaskEntity

/**
 * @author Stanislav Aleshin on 25.02.2023.
 */
@Database(
    version = 2,
    entities = [
        TemplateEntity::class,
        DailyScheduleEntity::class,
        TimeTaskEntity::class,
        MainCategoryEntity::class,
        SubCategoryEntity::class,
        LockedAppEntity::class,
    ],
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
    ],
)
abstract class SchedulesDataBase : RoomDatabase() {

    abstract fun fetchSchedulesDao(): SchedulesDao
    abstract fun fetchMainCategoriesDao(): MainCategoriesDao
    abstract fun fetchSubCategoriesDao(): SubCategoriesDao
    abstract fun fetchTemplatesDao(): TemplatesDao
    abstract fun fetchLockedAppsDao(): LockedAppsDao

    companion object {
        const val NAME = "SchedulesDataBase.db"
    }
}
