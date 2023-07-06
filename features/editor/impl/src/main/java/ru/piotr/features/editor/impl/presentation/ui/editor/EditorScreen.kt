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
package ru.piotr.features.editor.impl.presentation.ui.editor

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import ru.piotr.core.ui.views.*
import ru.piotr.core.ui.views.Scaffold
import ru.piotr.core.utils.functional.TimeRange
import ru.piotr.core.utils.platform.screen.ScreenContent
import ru.piotr.features.editor.impl.presentation.mappers.mapToMessage
import ru.piotr.features.editor.impl.presentation.theme.EditorTheme
import ru.piotr.features.editor.impl.presentation.theme.EditorThemeRes
import ru.piotr.features.editor.impl.presentation.ui.editor.contract.EditorEffect
import ru.piotr.features.editor.impl.presentation.ui.editor.contract.EditorEvent
import ru.piotr.features.editor.impl.presentation.ui.editor.contract.EditorViewState
import ru.piotr.features.editor.impl.presentation.ui.editor.screenmodel.rememberEditorScreenModel
import ru.piotr.features.editor.impl.presentation.ui.editor.views.EditorTopAppBar
import ru.piotr.features.editor.impl.presentation.ui.editor.views.TemplatesBottomSheet
import java.util.*
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 25.02.2023.
 */
internal class EditorScreen @Inject constructor() : Screen {

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    override fun Content() = ScreenContent(
        screenModel = rememberEditorScreenModel(),
        initialState = EditorViewState(),
    ) { state ->
        EditorTheme {
            val hostState = remember { SnackbarHostState() }
            var isTemplatesSheetOpen by rememberSaveable { mutableStateOf(false) }
            val strings = EditorThemeRes.strings

            Scaffold(
                content = { paddingValues ->
                    EditorContent(
                        state = state,
                        modifier = Modifier.padding(paddingValues),
                        onCategoriesChange = { main, sub -> dispatchEvent(EditorEvent.ChangeCategories(main, sub)) },
                        onAddSubCategory = { dispatchEvent(EditorEvent.AddSubCategory(it)) },
                        onAddLockAdd = { app, category -> dispatchEvent(EditorEvent.AddLockApp(app, category)) },
                        onChangeTemplate = { dispatchEvent(EditorEvent.ChangeIsTemplate) },
                        onSaveClick = { dispatchEvent(EditorEvent.PressSaveButton(it)) },
                        onCancelClick = { dispatchEvent(EditorEvent.PressBackButton) },
                        onTimeRangeChange = { dispatchEvent(EditorEvent.ChangeTime(it)) },
                        onChangeParameters = { dispatchEvent(EditorEvent.ChangeParameters(it)) },
                    )
                },
                topBar = {
                    EditorTopAppBar(
                        onBackIconClick = { dispatchEvent(EditorEvent.PressBackButton) },
                        onDeleteActionClick = { dispatchEvent(EditorEvent.PressDeleteButton) },
                        onTemplatesActionClick = {
//                            dispatchEvent(EditorEvent.LoadTemplates)
//                            isTemplatesSheetOpen = true
                        },
                    )
                },
                snackbarHost = {
                    SnackbarHost(hostState = hostState) { ErrorSnackbar(snackbarData = it) }
                },
            )

            TemplatesBottomSheet(
                isShow = isTemplatesSheetOpen,
                templates = state.templates,
                onDismiss = { isTemplatesSheetOpen = false },
                onControlClick = { dispatchEvent(EditorEvent.PressControlTemplateButton) },
                onChooseTemplate = { template ->
                    dispatchEvent(EditorEvent.ApplyTemplate(template))
                    isTemplatesSheetOpen = false
                },
            )

            handleEffect { effect ->
                when (effect) {
                    is EditorEffect.ShowError -> {
                        hostState.showSnackbar(message = effect.failures.mapToMessage(strings))
                    }
                    is EditorEffect.ShowOverlayError -> {
                        val result = hostState.showSnackbar(
                            message = effect.failure.mapToMessage(strings),
                            withDismissAction = true,
                            actionLabel = strings.correctOverlayTitle,
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            val currentTimeRange = effect.currentTimeRange
                            val start = effect.failure.startOverlay ?: currentTimeRange.from
                            val end = effect.failure.endOverlay ?: currentTimeRange.to
                            dispatchEvent(EditorEvent.ChangeTime(TimeRange(start, end)))
                        }
                    }
                }
            }
        }
    }
}
