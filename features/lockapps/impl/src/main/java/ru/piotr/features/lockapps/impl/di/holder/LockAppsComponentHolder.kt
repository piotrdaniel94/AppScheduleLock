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
package ru.piotr.features.lockapps.impl.di.holder

import ru.piotr.features.lockapps.api.di.LockAppsFeatureApi
import ru.piotr.features.lockapps.impl.di.LockAppsFeatureDependencies
import ru.piotr.features.lockapps.impl.di.component.LockAppsComponent
import ru.piotr.module_injector.BaseComponentHolder

/**
 * @author Stanislav Aleshin on 30.03.2023.
 */
object LockAppsComponentHolder : BaseComponentHolder<LockAppsFeatureApi, LockAppsFeatureDependencies> {

    private var component: LockAppsComponent? = null

    override fun init(dependencies: LockAppsFeatureDependencies) {
        if (component == null) component = LockAppsComponent.create(dependencies)
    }

    override fun fetchApi(): LockAppsFeatureApi = fetchComponent()

    override fun clear() {
        component = null
    }

    internal fun fetchComponent() = checkNotNull(component) {
        "Analytics Component is not initialized"
    }
}
