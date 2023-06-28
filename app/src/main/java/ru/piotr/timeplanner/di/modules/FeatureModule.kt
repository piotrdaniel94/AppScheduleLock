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

import dagger.Module
import dagger.Provides
import ru.piotr.features.analytics.impl.di.AnalyticsFeatureDependencies
import ru.piotr.features.analytics.impl.di.holder.AnalyticsComponentHolder
import ru.piotr.features.editor.impl.di.EditorFeatureDependencies
import ru.piotr.features.editor.impl.di.holder.EditorComponentHolder
import ru.piotr.features.home.impl.di.HomeFeatureDependencies
import ru.piotr.features.home.impl.di.holder.HomeComponentHolder
import ru.piotr.features.settings.impl.di.SettingsFeatureDependencies
import ru.piotr.features.settings.impl.di.holder.SettingsComponentHolder
//import ru.piotr.features.lockapps.impl.di.LockAppsFeatureDependecies
//import ru.piotr.features.lockapps.impl.di.holder.AnalysticsComponentHolder


/**
 * @author Stanislav Aleshin on 14.02.2023.
 */
@Module
class FeatureModule {

    @Provides
    fun provideHomeFeatureStarter(
        dependencies: HomeFeatureDependencies,
    ) = with(HomeComponentHolder) {
        init(dependencies)
        fetchApi().fetchStarter()
    }

    @Provides
    fun provideAnalyticsFeatureStarter(
        dependencies: AnalyticsFeatureDependencies,
    ) = with(AnalyticsComponentHolder) {
        init(dependencies)
        fetchApi().fetchStarter()
    }

    @Provides
    fun provideEditorFeatureStarter(
        dependencies: EditorFeatureDependencies,
    ) = with(EditorComponentHolder) {
        init(dependencies)
        fetchApi().fetchStarter()
    }

    @Provides
    fun provideSettingsFeatureStarter(
        dependencies: SettingsFeatureDependencies,
    ) = with(SettingsComponentHolder) {
        init(dependencies)
        fetchApi().fetchStarter()
    }

//    @Provides
//    fun provideLockAppsFeatureStarter(
//        dependencies: LockAppsFeatureDependencies,
//    ) = with(SettingsComponentHolder) {
//        init(dependencies)
//        fetchApi().fetchStarter()
//    }
}
