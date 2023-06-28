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
package ru.piotr.features.settings.impl.presentation.ui.contract

import android.net.Uri
import kotlinx.parcelize.Parcelize
import ru.piotr.core.utils.platform.screenmodel.contract.*
import ru.piotr.features.settings.api.domain.entities.ThemeSettings
import ru.piotr.features.settings.impl.domain.common.SettingsFailures
import ru.piotr.features.settings.impl.domain.entities.Settings

/**
 * @author Stanislav Aleshin on 17.02.2023.
 */
@Parcelize
internal data class SettingsViewState(
    val themeSettings: ThemeSettings? = null,
    val failure: SettingsFailures? = null,
    val isBackupLoading: Boolean = false,
) : BaseViewState

internal sealed class SettingsEvent : BaseEvent {
    object Init : SettingsEvent()
    object PressResetButton : SettingsEvent()
    object PressClearDataButton : SettingsEvent()
    object StopLoading : SettingsEvent()
    data class PressSaveBackupData(val uri: Uri) : SettingsEvent()
    data class PressRestoreBackupData(val uri: Uri) : SettingsEvent()
    data class ChangedThemeSettings(val themeSettings: ThemeSettings) : SettingsEvent()
}

internal sealed class SettingsEffect : BaseUiEffect {
    data class ShowError(val failures: SettingsFailures) : SettingsEffect()
}

internal sealed class SettingsAction : BaseAction {
    data class ShowLoadingBackup(val isLoading: Boolean) : SettingsAction()
    data class ChangeAllSettings(val settings: Settings) : SettingsAction()
    data class ChangeThemeSettings(val settings: ThemeSettings) : SettingsAction()
}
