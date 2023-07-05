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
package ru.piotr.features.home.api.data.datasources.lockapps

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import androidx.compose.ui.platform.LocalContext
import ru.piotr.features.home.api.domains.entities.categories.MainCategory
import ru.piotr.features.home.api.domains.entities.lockapp.LockApp
import java.util.ArrayList
import javax.inject.Inject

interface LockAppsLocalDataSource {
    suspend fun fetchInstalledAppList(): List<AppData>
    suspend fun orderAppsByLockStatus(allApps: List<AppData>, lockedApps: List<LockedAppEntity>): List<AppData>
    suspend fun fetchLockedAppsByType(type: MainCategory) : List<LockedAppEntity>
    suspend fun fetchAllLockedApps(): List<LockedAppEntity>

    class Base @Inject constructor(
//        val context: Context,
        private val lockAppsDao: LockedAppsDao,
    ) : LockAppsLocalDataSource {

        override suspend fun fetchInstalledAppList(): List<AppData> {
                val appDataList: ArrayList<AppData> = arrayListOf()

                val lockedAppList = lockAppsDao.getLockedAppsSync()

                val orderedList = orderAppsByLockStatus(appDataList, lockedAppList)

                return orderedList
            }

        override suspend fun orderAppsByLockStatus(allApps: List<AppData>, lockedApps: List<LockedAppEntity>): List<AppData> {
            val resultList = arrayListOf<AppData>()


            lockedApps.forEach { lockedAppEntity ->
                allApps.forEach { appData ->
                    if (lockedAppEntity.packageName == appData.packageName) {
                        resultList.add(appData)
                    }
                }
            }


            val alphabeticOrderList: ArrayList<AppData> = arrayListOf()

            allApps.forEach { appData ->
                if (resultList.contains(appData).not()) {
                    alphabeticOrderList.add(appData)
                }
            }
            alphabeticOrderList.sortWith(Comparator { app1, app2 -> app1.appName.compareTo(app2.appName) })
            resultList.addAll(alphabeticOrderList)

            return resultList
        }

        override suspend fun fetchLockedAppsByType(type: MainCategory): List<LockedAppEntity>{
            return lockAppsDao.fetchLockedAppsByTypeId(type.id)
        }

        override suspend fun fetchAllLockedApps(): List<LockedAppEntity> {
            return lockAppsDao.getLockedAppsSync()
        }
    }
}