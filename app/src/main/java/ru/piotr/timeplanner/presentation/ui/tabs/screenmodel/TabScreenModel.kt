
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
*/package ru.piotr.timeplanner.presentation.ui.tabs.screenmodel

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import ru.piotr.core.utils.managers.CoroutineManager
import ru.piotr.core.utils.platform.communications.state.EffectCommunicator
import ru.piotr.core.utils.platform.screenmodel.BaseScreenModel
import ru.piotr.core.utils.platform.screenmodel.work.WorkScope
import ru.piotr.timeplanner.application.fetchAppComponent
import ru.piotr.timeplanner.navigation.TabNavigationManager
import ru.piotr.timeplanner.presentation.ui.tabs.contract.TabsAction
import ru.piotr.timeplanner.presentation.ui.tabs.contract.TabsEffect
import ru.piotr.timeplanner.presentation.ui.tabs.contract.TabsEvent
import ru.piotr.timeplanner.presentation.ui.tabs.contract.TabsViewState
import ru.piotr.timeplanner.presentation.ui.tabs.views.TabsBottomBarItems
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 18.02.2023.
 */
class TabScreenModel @Inject constructor(
    private val navigationManager: TabNavigationManager,
    communicator: TabsStateCommunicator,
    coroutineManager: CoroutineManager,
) : BaseScreenModel<TabsViewState, TabsEvent, TabsAction, TabsEffect>(
    stateCommunicator = communicator,
    effectCommunicator = EffectCommunicator.Empty(),
    coroutineManager = coroutineManager,
) {

    init {
        dispatchEvent(TabsEvent.SelectedHomeTab)
    }

    override suspend fun WorkScope<TabsViewState, TabsAction, TabsEffect>.handleEvent(
        event: TabsEvent,
    ) = when (event) {
        TabsEvent.SelectedHomeTab -> navigate(TabsBottomBarItems.HOME) { showHomeFeature() }
        TabsEvent.SelectedMainScreen -> navigate(TabsBottomBarItems.HOME) { showHomeScreen() }
        TabsEvent.SelectedTemplateScreen -> navigate(TabsBottomBarItems.HOME) { showTemplatesScreen() }
        TabsEvent.SelectedCategoriesScreen -> navigate(TabsBottomBarItems.HOME) { showCategoriesScreen() }
        TabsEvent.SelectedAnalyticsTab -> navigate(TabsBottomBarItems.ANALYTICS) { showAnalyticsFeature() }
        TabsEvent.SelectedSettingsTab -> navigate(TabsBottomBarItems.SETTINGS) { showSettingsFeature() }
        TabsEvent.SelectedLockAppsTab -> navigate(TabsBottomBarItems.LOCKAPPSLIST) { showLockAppsFeature() }
    }

    override suspend fun reduce(
        action: TabsAction,
        currentState: TabsViewState,
    ) = when (action) {
        is TabsAction.ChangeNavItems -> currentState.copy(
            bottomBarItem = action.item,
        )
    }

    private suspend fun WorkScope<TabsViewState, TabsAction, TabsEffect>.navigate(
        bottomItem: TabsBottomBarItems,
        onAction: TabNavigationManager.() -> Unit,
    ) = sendAction(TabsAction.ChangeNavItems(item = bottomItem)).apply {
        onAction(navigationManager)
    }
}

@Composable
fun Screen.rememberTabsScreenModel(): TabScreenModel {
    val component = fetchAppComponent()
    return rememberScreenModel { component.fetchTabScreenModel() }
}
