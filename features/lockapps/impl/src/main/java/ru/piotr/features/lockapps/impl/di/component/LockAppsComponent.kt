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
package ru.piotr.features.lockapps.impl.di.component

import dagger.Component
import ru.piotr.core.utils.di.FeatureScope
import ru.piotr.features.lockapps.api.di.LockAppsFeatureApi
import ru.piotr.features.lockapps.impl.di.LockAppsFeatureDependencies
import ru.piotr.features.lockapps.impl.di.modules.DomainModule
import ru.piotr.features.lockapps.impl.di.modules.PresentationModule
import ru.piotr.features.lockapps.impl.presenatiton.ui.screenmodel.LockAppsScreenModel

/**
 * @author Stanislav Aleshin on 30.03.2023.
 */
@Component(
    modules = [DomainModule::class, PresentationModule::class],
    dependencies = [LockAppsFeatureDependencies::class],
)
@FeatureScope
internal interface LockAppsComponent : LockAppsFeatureApi {

    fun fetchAnalyticsScreenModel(): LockAppsScreenModel

    @Component.Builder
    interface Builder {
        fun dependencies(deps: LockAppsFeatureDependencies): Builder
        fun build(): LockAppsComponent
    }

    companion object {
        fun create(deps: LockAppsFeatureDependencies): LockAppsComponent {
            return DaggerAnalyticsComponent.builder()
                .dependencies(deps)
                .build()
        }
    }
}
