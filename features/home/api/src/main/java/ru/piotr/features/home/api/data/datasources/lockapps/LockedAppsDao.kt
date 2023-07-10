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

import androidx.room.*
import io.reactivex.Flowable


@Dao
interface LockedAppsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun lockApp(lockedAppEntity: LockedAppEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun lockApps(lockedAppEntityList: List<LockedAppEntity>)

    @Query("SELECT * FROM lockedApps")
    abstract fun getLockedApps(): Flowable<List<LockedAppEntity>>

    @Query("SELECT * FROM lockedApps")
    suspend fun getLockedAppsSync(): List<LockedAppEntity>

    @Query("SELECT * FROM lockedApps WHERE main_category_id = :id")
    suspend fun fetchLockedAppsByTypeId(id: Int): List<LockedAppEntity>

    @Query("DELETE FROM lockedApps WHERE package_name = :packageName")
    suspend fun unlockApp(packageName: String)

    @Query("DELETE FROM lockedApps")
    suspend fun unlockAll()
}