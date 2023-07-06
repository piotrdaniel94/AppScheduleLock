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
package ru.piotr.features.editor.impl.domain.interactors

import ru.piotr.core.utils.functional.DomainResult
import ru.piotr.core.utils.functional.UnitDomainResult
import ru.piotr.features.editor.impl.domain.common.EditorEitherWrapper
import ru.piotr.features.editor.impl.domain.entites.EditorFailures
import ru.piotr.features.home.api.data.datasources.lockapps.LockedAppEntity
import ru.piotr.features.home.api.domains.entities.categories.MainCategory
import ru.piotr.features.home.api.domains.entities.lockapp.LockApp
import ru.piotr.features.home.api.domains.repository.LockAppsRepository
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 07.03.2023.
 */
internal interface LockAppsInteractor {
    suspend fun fetchLockApps(type: MainCategory): DomainResult<EditorFailures, List<LockApp>>
    suspend fun addLockApps(apps: List<LockApp>): UnitDomainResult<EditorFailures>
    suspend fun addLockApp(lock_app: LockApp): DomainResult<EditorFailures, Long>
    suspend fun updateLockApp(lock_app: LockApp): UnitDomainResult<EditorFailures>
    suspend fun deleteLockApp(lock_app: LockApp): UnitDomainResult<EditorFailures>

    class Base @Inject constructor(
        private val lockAppsRepository: LockAppsRepository,
        private val eitherWrapper: EditorEitherWrapper,
    ) : LockAppsInteractor {
        override suspend fun fetchLockApps(type: MainCategory) = eitherWrapper.wrap {
            lockAppsRepository.fetchLockApps(type)
        }

        override suspend fun addLockApp(lock_app: LockApp) = eitherWrapper.wrap {
            lockAppsRepository.addLockOneApp(lock_app)
        }

        override suspend fun addLockApps(lock_app_s: List<LockApp>) = eitherWrapper.wrap {
            lockAppsRepository.addLockApps(lock_app_s)
        }

        override suspend fun deleteLockApp(lock_app: LockApp) = eitherWrapper.wrap {
            lockAppsRepository.deleteLockApp(lock_app)
        }

        override suspend fun updateLockApp(lock_app: LockApp) = eitherWrapper.wrap {
            lockAppsRepository.updateLockApp(lock_app)
        }
    }
}
