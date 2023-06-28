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

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.screen.Screen
import dagger.Binds
import dagger.Module
import ru.piotr.core.utils.di.FeatureScope
import ru.piotr.features.lockapps.api.navigation.LockAppsFeatureStarter
import ru.piotr.features.lockapps.impl.navigation.AnalyticsFeatureStarterImpl
import ru.piotr.features.lockapps.impl.presenatiton.ui.AnalyticsScreen
import ru.piotr.features.lockapps.impl.presenatiton.ui.screenmodel.AnalyticsEffectCommunicator
import ru.piotr.features.lockapps.impl.presenatiton.ui.screenmodel.AnalyticsScreenModel
import ru.piotr.features.lockapps.impl.presenatiton.ui.screenmodel.AnalyticsStateCommunicator
import ru.piotr.features.lockapps.impl.presenatiton.ui.screenmodel.AnalyticsWorkProcessor

/**
 * @author Stanislav Aleshin on 30.03.2023.
 */
@Module
internal interface PresentationModule {

    @Binds
    fun bindAnalyticsFeatureStarter(starter: AnalyticsFeatureStarterImpl): LockAppsFeatureStarter

    @Binds
    @FeatureScope
    fun bindAnalyticsScreen(screen: AnalyticsScreen): Screen

    @Binds
    fun bindAnalyticsScreenModel(screenModel: AnalyticsScreenModel): ScreenModel

    @Binds
    fun bindAnalyticsWorkProcessor(workProcessor: AnalyticsWorkProcessor.Base): AnalyticsWorkProcessor

    @Binds
    @FeatureScope
    fun bindAnalyticsStateCommunicator(communicator: AnalyticsStateCommunicator.Base): AnalyticsStateCommunicator

    @Binds
    @FeatureScope
    fun bindAnalyticsEffectCommunicator(communicator: AnalyticsEffectCommunicator.Base): AnalyticsEffectCommunicator
}
