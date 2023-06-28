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
package ru.piotr.features.settings.impl.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import ru.piotr.core.ui.theme.TimePlannerRes
import ru.piotr.features.settings.impl.presentation.theme.tokens.LocalSettingsIcons
import ru.piotr.features.settings.impl.presentation.theme.tokens.LocalSettingsStrings
import ru.piotr.features.settings.impl.presentation.theme.tokens.baseSettingIcons
import ru.piotr.features.settings.impl.presentation.theme.tokens.fetchSettingsStrings

/**
 * @author Stanislav Aleshin on 17.02.2023.
 */
@Composable
internal fun SettingsTheme(content: @Composable () -> Unit) {
    val strings = fetchSettingsStrings(TimePlannerRes.language)

    CompositionLocalProvider(
        LocalSettingsStrings provides strings,
        LocalSettingsIcons provides baseSettingIcons,
        content = content,
    )
}