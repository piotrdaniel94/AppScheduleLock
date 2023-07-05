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
package ru.piotr.features.home.api.data.repository

import ru.piotr.features.home.api.data.datasources.lockapps.AppData
import ru.piotr.features.home.api.data.datasources.lockapps.LockAppsLocalDataSource
import ru.piotr.features.home.api.data.mappers.lockapps.mapToDomain
import ru.piotr.features.home.api.domains.entities.categories.MainCategory
import ru.piotr.features.home.api.domains.entities.lockapp.LockApp
import ru.piotr.features.home.api.domains.repository.LockAppsRepository
import javax.inject.Inject

class LockAppsRepositoryImpl  @Inject constructor(
    private val localDataSource: LockAppsLocalDataSource,
) : LockAppsRepository {

    override suspend fun fetchAllInstalledApps(): List<AppData> {
        return localDataSource.fetchInstalledAppList()
    }

    override suspend fun fetchLockApps(type: MainCategory): List<LockApp> {
        return localDataSource.fetchLockedAppsByType(type).map {app -> app.mapToDomain(type)}
    }

    override suspend fun addLockApps(apps: List<LockApp>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateLockApp(app: LockApp) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLockApp(app: LockApp) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllLockApps() {
        TODO("Not yet implemented")
    }


}