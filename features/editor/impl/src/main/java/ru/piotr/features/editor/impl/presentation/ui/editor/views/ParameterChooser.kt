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
package ru.piotr.features.editor.impl.presentation.ui.editor.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.piotr.core.ui.theme.TimePlannerRes
import ru.piotr.features.editor.impl.presentation.theme.EditorThemeRes

/**
 * @author Stanislav Aleshin on 25.02.2023.
 */
@Composable
internal fun ParameterChooser(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    title: String,
    description: String,
    onChangeEnabled: (Boolean) -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        tonalElevation = TimePlannerRes.elevations.levelOne,
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Switch(
                modifier = Modifier.align(Alignment.Top),
                checked = enabled,
                onCheckedChange = onChangeEnabled,
                thumbContent = {
                    Icon(
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                        imageVector = if (enabled) Icons.Default.Check else Icons.Default.Close,
                        contentDescription = EditorThemeRes.strings.parameterChooserSwitchIconDesc,
                    )
                },
            )
        }
    }
}

/* ----------------------- Release Preview -----------------------
@Composable
@Preview(showBackground = true)
internal fun ParameterChooser_Preview() {
    TimePlannerTheme(dynamicColor = false, themeColorsType = ThemeColorsUiType.LIGHT) {
        EditorTheme {
            Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                val enabled = remember { mutableStateOf(false) }
                ParameterChooser(
                    modifier = Modifier.padding(12.dp),
                    enabled = enabled.value,
                    onChangeEnabled = { enabled.value = it },
                    title = "Уведомления",
                    description = "Отправить уведомление при выполнении задачи",
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
internal fun ParameterChooser_Dark_Preview() {
    TimePlannerTheme(dynamicColor = false, themeColorsType = ThemeColorsUiType.DARK) {
        EditorTheme {
            Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                val enabled = remember { mutableStateOf(false) }
                ParameterChooser(
                    modifier = Modifier.padding(12.dp),
                    enabled = enabled.value,
                    onChangeEnabled = { enabled.value = it },
                    title = "Уведомления",
                    description = "Отправить уведомление",
                )
            }
        }
    }
}
*/
