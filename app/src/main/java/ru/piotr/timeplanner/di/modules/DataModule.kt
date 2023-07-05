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
package ru.piotr.timeplanner.di.modules

import dagger.Binds
import dagger.Module
import ru.piotr.features.home.api.data.mappers.schedules.*
import ru.piotr.features.home.api.data.mappers.template.TemplatesDataToDomainMapper
import ru.piotr.features.home.api.data.mappers.template.TemplatesDomainToDataMapper
import ru.piotr.features.home.api.data.repository.*
import ru.piotr.features.home.api.domains.repository.*
import ru.piotr.features.settings.api.data.repositories.ThemeSettingsRepositoryImpl
import ru.piotr.features.settings.api.domain.repositories.ThemeSettingsRepository
import javax.inject.Singleton

/**
 * @author Stanislav Aleshin on 07.03.2023.
 */
@Module
interface DataModule {

    // Repositories

    @Singleton
    @Binds
    fun bindTemplatesRepository(repository: TemplatesRepositoryImpl): TemplatesRepository

    @Binds
    @Singleton
    fun bindTimeTaskRepository(repository: TimeTaskRepositoryImpl): TimeTaskRepository

    @Binds
    @Singleton
    fun bindThemeSettingsRepository(repository: ThemeSettingsRepositoryImpl): ThemeSettingsRepository

    @Binds
    @Singleton
    fun bindScheduleRepository(repository: ScheduleRepositoryImpl): ScheduleRepository

    @Binds
    @Singleton
    fun bindSubCategoriesRepository(repository: SubCategoriesRepositoryImpl): SubCategoriesRepository

    @Binds
    @Singleton
    fun bindCategoriesRepository(repository: CategoriesRepositoryImpl): CategoriesRepository

    @Binds
    @Singleton
    fun bindLockAppsRepository(repository: LockAppsRepositoryImpl): LockAppsRepository

    // Mappers

    @Binds
    fun bindScheduleDataToDomainMapper(mapper: ScheduleDataToDomainMapper.Base): ScheduleDataToDomainMapper

    @Binds
    fun bindTemplatesDataToDomain(mapper: TemplatesDataToDomainMapper.Base): TemplatesDataToDomainMapper

    @Binds
    fun bindTemplatesDomainToData(mapper: TemplatesDomainToDataMapper.Base): TemplatesDomainToDataMapper
}
