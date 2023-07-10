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
package ru.piotr.timeplanner.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import ru.piotr.core.utils.navigation.navigator.NavigatorManager
import ru.piotr.core.utils.notifications.NotificationCreator
import ru.piotr.features.analytics.impl.di.AnalyticsFeatureDependencies
import ru.piotr.features.editor.impl.di.EditorFeatureDependencies
import ru.piotr.features.home.impl.di.HomeFeatureDependencies
import ru.piotr.features.settings.impl.di.SettingsFeatureDependencies
import ru.piotr.features.lockapps.impl.di.LockAppsFeatureDependencies
import ru.piotr.timeplanner.application.TimePlannerApp
import ru.piotr.timeplanner.di.annotation.TabNavigation
import ru.piotr.timeplanner.di.modules.*
import ru.piotr.timeplanner.presentation.ui.main.MainActivity
import ru.piotr.timeplanner.presentation.ui.splash.screenmodel.SplashScreenModel
import ru.piotr.timeplanner.presentation.ui.tabs.screenmodel.TabScreenModel
import javax.inject.Singleton

/**
 * @author Stanislav Aleshin on 14.02.2023.
 */
@Singleton
@Component(
    modules = [
        DataBaseModule::class,
        DataModule::class,
        NavigationModule::class,
        CoreModule::class,
        PresentationModule::class,
        DomainModules::class,
        DependenciesModule::class,
        FeatureModule::class,
        AndroidInjectionModule::class
    ],
)
interface AppComponent :
    HomeFeatureDependencies,
    AnalyticsFeatureDependencies,
    SettingsFeatureDependencies,
    LockAppsFeatureDependencies,
    EditorFeatureDependencies,
    AndroidInjector<DaggerApplication> {

    @TabNavigation
    fun fetchTabNavigatorManager(): NavigatorManager
    fun fetchSplashScreenModel(): SplashScreenModel
    fun fetchTabScreenModel(): TabScreenModel
    fun fetchNotificationCreator(): NotificationCreator
    fun inject(activity: MainActivity)

    @Component.Builder
    abstract interface Builder {
        @BindsInstance
        fun applicationContext(context: Context): Builder
        fun navigationModule(module: NavigationModule): Builder
        fun featureModule(module: FeatureModule): Builder
        fun dataBaseModule(module: DataBaseModule): Builder
        fun build(): AppComponent

//        fun application(context: Context): AndroidInjector<DaggerApplication>
//        companion object {
//            fun application(tcontext: Context): AndroidInjector<out DaggerApplication> {
//                return DaggerAppComponent.builder()
//                    .applicationContext(tcontext)
//                    .navigationModule(NavigationModule())
//                    .featureModule(FeatureModule())
//                    .dataBaseModule(DataBaseModule())
//                    .build()
//            }
//        }
    }

    companion object {
        fun create(context: Context): AppComponent {
            return DaggerAppComponent.builder()
                .applicationContext(context)
                .navigationModule(NavigationModule())
                .featureModule(FeatureModule())
                .dataBaseModule(DataBaseModule())
                .build()
        }
    }
}
