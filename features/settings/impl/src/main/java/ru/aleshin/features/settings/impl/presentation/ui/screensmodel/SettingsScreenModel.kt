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
package ru.aleshin.features.settings.impl.presentation.ui.screensmodel

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.Dispatchers
import ru.aleshin.core.utils.managers.CoroutineManager
import ru.aleshin.core.utils.platform.screenmodel.BaseScreenModel
import ru.aleshin.core.utils.platform.screenmodel.work.BackgroundWorkKey
import ru.aleshin.core.utils.platform.screenmodel.work.WorkScope
import ru.aleshin.features.settings.impl.di.holder.SettingsComponentHolder
import ru.aleshin.features.settings.impl.presentation.ui.contract.SettingsAction
import ru.aleshin.features.settings.impl.presentation.ui.contract.SettingsEffect
import ru.aleshin.features.settings.impl.presentation.ui.contract.SettingsEvent
import ru.aleshin.features.settings.impl.presentation.ui.contract.SettingsViewState
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 17.02.2023.
 */
internal class SettingsScreenModel @Inject constructor(
    private val settingsWorkProcessor: SettingsWorkProcessor,
    private val dataWorkProcessor: DataWorkProcessor,
    stateCommunicator: SettingsStateCommunicator,
    effectCommunicator: SettingsEffectCommunicator,
    coroutineManager: CoroutineManager,
) : BaseScreenModel<SettingsViewState, SettingsEvent, SettingsAction, SettingsEffect>(
    stateCommunicator = stateCommunicator,
    effectCommunicator = effectCommunicator,
    coroutineManager = coroutineManager,
) {

    override fun init() {
        if (!isInitialize.get()) {
            dispatchEvent(SettingsEvent.Init)
            super.init()
        }
    }

    override suspend fun WorkScope<SettingsViewState, SettingsAction, SettingsEffect>.handleEvent(
        event: SettingsEvent,
    ) {
        when (event) {
            is SettingsEvent.Init -> {
                settingsWorkProcessor.loadAllSettings().handleWork()
            }
            is SettingsEvent.ChangedThemeSettings -> {
                settingsWorkProcessor.updateThemeSettings(event.themeSettings).handleWork()
            }
            is SettingsEvent.PressResetButton -> {
                settingsWorkProcessor.resetSettings().handleWork()
            }
            is SettingsEvent.PressClearDataButton -> launchBackgroundWork(SettingsWorkKey.DATA_WORK, Dispatchers.IO) {
                dataWorkProcessor.work(DataWorkCommand.ClearAllData).collectAndHandleWork()
            }
            is SettingsEvent.PressRestoreBackupData -> launchBackgroundWork(SettingsWorkKey.DATA_WORK, Dispatchers.IO) {
                dataWorkProcessor.work(DataWorkCommand.RestoreBackupData(event.uri)).collectAndHandleWork()
            }
            is SettingsEvent.PressSaveBackupData -> launchBackgroundWork(SettingsWorkKey.DATA_WORK, Dispatchers.IO) {
                dataWorkProcessor.work(DataWorkCommand.SaveBackupData(event.uri)).collectAndHandleWork()
            }
            is SettingsEvent.StopLoading -> sendAction(SettingsAction.ShowLoadingBackup(false))
        }
    }

    override suspend fun reduce(action: SettingsAction, currentState: SettingsViewState) =
        when (action) {
            is SettingsAction.ChangeAllSettings -> currentState.copy(
                themeSettings = action.settings.themeSettings,
                failure = null,
                isBackupLoading = false,
            )
            is SettingsAction.ChangeThemeSettings -> currentState.copy(
                themeSettings = action.settings,
                failure = null,
            )
            is SettingsAction.ShowLoadingBackup -> currentState.copy(
                isBackupLoading = action.isLoading,
            )
        }

    override fun onDispose() {
        super.onDispose()
        SettingsComponentHolder.clear()
    }
}

internal enum class SettingsWorkKey : BackgroundWorkKey {
    DATA_WORK,
}

@Composable
internal fun Screen.rememberSettingsScreenModel() = rememberScreenModel {
    SettingsComponentHolder.fetchComponent().fetchSettingsScreenModel()
}
