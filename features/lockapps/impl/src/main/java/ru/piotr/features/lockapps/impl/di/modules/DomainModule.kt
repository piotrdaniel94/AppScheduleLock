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
package ru.piotr.features.lockapps.impl.di.modules

import dagger.Binds
import dagger.Module
import ru.piotr.features.lockapps.impl.domain.common.LockAppsEitherWrapper
import ru.piotr.features.lockapps.impl.domain.common.LockAppsErrorHandler
import ru.piotr.features.lockapps.impl.domain.interactors.AnalyticsInteractor

/**
 * @author Stanislav Aleshin on 22.04.2023.
 */
@Module
internal interface DomainModule {

    @Binds
    fun bindLockAppsInteractor(interactor: AnalyticsInteractor.Base): AnalyticsInteractor

    @Binds
    fun bindLockAppsEitherWrapper(wrapper: LockAppsEitherWrapper.Base): LockAppsEitherWrapper

    @Binds
    fun bindLockAppsErrorHandler(handler: LockAppsErrorHandler.Base): LockAppsErrorHandler
}
