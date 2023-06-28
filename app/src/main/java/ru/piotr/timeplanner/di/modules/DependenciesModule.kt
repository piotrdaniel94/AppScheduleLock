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
 * limitations under the License.
*/
package ru.piotr.timeplanner.di.modules

import dagger.Binds
import dagger.Module
import ru.piotr.features.analytics.impl.di.AnalyticsFeatureDependencies
import ru.piotr.features.editor.impl.di.EditorFeatureDependencies
import ru.piotr.features.home.impl.di.HomeFeatureDependencies
import ru.piotr.features.lockapps.impl.di.LockAppsFeatureDependencies
import ru.piotr.features.settings.impl.di.SettingsFeatureDependencies
import ru.piotr.timeplanner.di.component.AppComponent

/**
 * @author Stanislav Aleshin on 14.02.2023.
 */
@Module
interface DependenciesModule {

    @Binds
    fun bindHomeFeatureDependencies(component: AppComponent): HomeFeatureDependencies

    @Binds
    fun bindEditorFeatureDependencies(component: AppComponent): EditorFeatureDependencies

    @Binds
    fun bindAnalyticsFeatureDependencies(component: AppComponent): AnalyticsFeatureDependencies

    @Binds
    fun bindSettingsFeatureDependencies(component: AppComponent): SettingsFeatureDependencies

    @Binds
    fun bindLockAppsFeatureDependencies(component: AppComponent): LockAppsFeatureDependencies
}
