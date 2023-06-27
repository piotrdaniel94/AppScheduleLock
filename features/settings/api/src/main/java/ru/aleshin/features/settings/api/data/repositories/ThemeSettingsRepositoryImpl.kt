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
package ru.aleshin.features.settings.api.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.aleshin.features.settings.api.data.datasources.ThemeSettingsLocalDataSource
import ru.aleshin.features.settings.api.data.models.ThemeSettingsEntity
import ru.aleshin.features.settings.api.domain.entities.ThemeSettings
import ru.aleshin.features.settings.api.domain.repositories.ThemeSettingsRepository
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 14.02.2023.
 */
class ThemeSettingsRepositoryImpl @Inject constructor(
    private val localDataSource: ThemeSettingsLocalDataSource,
) : ThemeSettingsRepository {

    override fun fetchSettingsFlow(): Flow<ThemeSettings> {
        return localDataSource.fetchSettingsFlow().map { it.settings }
    }

    override suspend fun fetchSettings(): ThemeSettings {
        return localDataSource.fetchSettings().settings
    }

    override suspend fun updateSettings(settings: ThemeSettings) {
        localDataSource.updateSettings(ThemeSettingsEntity(settings = settings))
    }
}
