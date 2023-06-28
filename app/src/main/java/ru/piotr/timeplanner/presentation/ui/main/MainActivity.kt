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
package ru.piotr.timeplanner.presentation.ui.main

import android.content.Intent
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.CurrentScreen
import ru.piotr.core.ui.theme.TimePlannerTheme
import ru.piotr.core.utils.navigation.navigator.AppNavigator
import ru.piotr.core.utils.navigation.navigator.NavigatorManager
import ru.piotr.core.utils.platform.activity.BaseActivity
import ru.piotr.core.utils.platform.screen.ScreenContent
import ru.piotr.timeplanner.application.fetchApp
import ru.piotr.timeplanner.di.annotation.GlobalNavigation
import ru.piotr.timeplanner.presentation.ui.main.contract.MainAction
import ru.piotr.timeplanner.presentation.ui.main.contract.MainEffect
import ru.piotr.timeplanner.presentation.ui.main.contract.MainEvent
import ru.piotr.timeplanner.presentation.ui.main.contract.MainViewState
import ru.piotr.timeplanner.presentation.ui.main.viewmodel.MainViewModel
import ru.piotr.timeplanner.presentation.ui.splash.SplashScreen
import javax.inject.Inject

class MainActivity : BaseActivity<MainViewState, MainEvent, MainAction, MainEffect>() {

    @Inject
    @GlobalNavigation
    lateinit var navigatorManager: NavigatorManager

    @Inject
    lateinit var viewModelFactory: MainViewModel.Factory

    override fun initDI() = fetchApp().appComponent.inject(this)

    @Composable
    override fun Content() = ScreenContent(viewModel, MainViewState()) { state ->
        TimePlannerTheme(
            dynamicColor = state.isEnableDynamicColors,
            themeColorsType = state.colors,
            language = state.language,
        ) {
            AppNavigator(
                initialScreen = SplashScreen(),
                navigatorManager = navigatorManager,
                content = { CurrentScreen() },
            )
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun fetchViewModelFactory() = viewModelFactory

    override fun fetchViewModelClass() = MainViewModel::class.java
}
