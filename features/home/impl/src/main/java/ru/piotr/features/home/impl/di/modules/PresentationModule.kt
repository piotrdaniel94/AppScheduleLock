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
package ru.piotr.features.home.impl.di.modules

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.screen.Screen
import dagger.Binds
import dagger.Module
import ru.piotr.core.utils.di.FeatureScope
import ru.piotr.features.home.api.navigation.HomeFeatureStarter
import ru.piotr.features.home.impl.navigation.HomeFeatureStarterImpl
import ru.piotr.features.home.impl.navigation.NavigationManager
import ru.piotr.features.home.impl.presentation.mapppers.ScheduleDomainToUiMapper
import ru.piotr.features.home.impl.presentation.mapppers.TimeTaskDomainToUiMapper
import ru.piotr.features.home.impl.presentation.ui.categories.screenmodel.CategoriesEffectCommunicator
import ru.piotr.features.home.impl.presentation.ui.categories.screenmodel.CategoriesScreenModel
import ru.piotr.features.home.impl.presentation.ui.categories.screenmodel.CategoriesStateCommunicator
import ru.piotr.features.home.impl.presentation.ui.categories.screenmodel.CategoriesWorkProcessor
import ru.piotr.features.home.impl.presentation.ui.home.screenModel.*
import ru.piotr.features.home.impl.presentation.ui.home.screenModel.HomeEffectCommunicator
import ru.piotr.features.home.impl.presentation.ui.home.screenModel.HomeScreenModel
import ru.piotr.features.home.impl.presentation.ui.home.screenModel.HomeStateCommunicator
import ru.piotr.features.home.impl.presentation.ui.home.screenModel.NavigationWorkProcessor
import ru.piotr.features.home.impl.presentation.ui.home.screenModel.ScheduleWorkProcessor
import ru.piotr.features.home.impl.presentation.ui.nav.NavScreen
import ru.piotr.features.home.impl.presentation.ui.templates.screenmodel.TemplatesEffectCommunicator
import ru.piotr.features.home.impl.presentation.ui.templates.screenmodel.TemplatesScreenModel
import ru.piotr.features.home.impl.presentation.ui.templates.screenmodel.TemplatesStateCommunicator
import ru.piotr.features.home.impl.presentation.ui.templates.screenmodel.TemplatesWorkProcessor

/**
 * @author Stanislav Aleshin on 18.02.2023.
 */
@Module
internal interface PresentationModule {

    @Binds
    @FeatureScope
    fun bindHomeFeatureStarter(starter: HomeFeatureStarterImpl): HomeFeatureStarter

    @Binds
    @FeatureScope
    fun bindNavigationManager(manager: NavigationManager.Base): NavigationManager

    // Nav ScreenModel

    @Binds
    @FeatureScope
    fun bindNavScreen(screen: NavScreen): Screen

    // Home ScreenModel

    @Binds
    fun bindHomeScreenModel(screenModel: HomeScreenModel): ScreenModel

    @Binds
    @FeatureScope
    fun bindHomeStateCommunicator(communicator: HomeStateCommunicator.Base): HomeStateCommunicator

    @Binds
    @FeatureScope
    fun bindHomeEffectCommunicator(communicator: HomeEffectCommunicator.Base): HomeEffectCommunicator

    @Binds
    fun bindScheduleWorkProcessor(processor: ScheduleWorkProcessor.Base): ScheduleWorkProcessor

    @Binds
    fun bindNavigationWorkProcessor(processor: NavigationWorkProcessor.Base): NavigationWorkProcessor

    @Binds
    fun bindTimeTaskDomainToUiMapper(mapper: TimeTaskDomainToUiMapper.Base): TimeTaskDomainToUiMapper

    @Binds
    fun bindScheduleDomainToUiMapper(mapper: ScheduleDomainToUiMapper.Base): ScheduleDomainToUiMapper

    // Templates

    @Binds
    fun bindTemplatesScreenModel(screenModel: TemplatesScreenModel): ScreenModel

    @Binds
    @FeatureScope
    fun bindTemplatesStateCommunicator(communicator: TemplatesStateCommunicator.Base): TemplatesStateCommunicator

    @Binds
    @FeatureScope
    fun bindTemplatesEffectCommunicator(communicator: TemplatesEffectCommunicator.Base): TemplatesEffectCommunicator

    @Binds
    fun bindTemplatesWorkProcessor(processor: TemplatesWorkProcessor.Base): TemplatesWorkProcessor

    // Categories

    @Binds
    fun bindCategoriesScreenModel(screenModel: CategoriesScreenModel): ScreenModel

    @Binds
    @FeatureScope
    fun bindCategoriesStateCommunicator(communicator: CategoriesStateCommunicator.Base): CategoriesStateCommunicator

    @Binds
    @FeatureScope
    fun bindCategoriesEffectCommunicator(communicator: CategoriesEffectCommunicator.Base): CategoriesEffectCommunicator

    @Binds
    fun bindCategoriesWorkProcessor(processor: CategoriesWorkProcessor.Base): CategoriesWorkProcessor
}
