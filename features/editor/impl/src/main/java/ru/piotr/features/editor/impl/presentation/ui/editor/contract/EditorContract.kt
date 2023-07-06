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
package ru.piotr.features.editor.impl.presentation.ui.editor.contract

import kotlinx.parcelize.Parcelize
import ru.piotr.core.utils.functional.TimeRange
import ru.piotr.core.utils.platform.screenmodel.contract.*
import ru.piotr.features.editor.impl.domain.entites.EditorFailures
import ru.piotr.features.editor.impl.presentation.models.EditModelUi
import ru.piotr.features.editor.impl.presentation.models.EditParameters
import ru.piotr.features.editor.impl.presentation.ui.editor.screenmodel.CategoryValidateError
import ru.piotr.features.editor.impl.presentation.ui.editor.screenmodel.TimeRangeError
import ru.piotr.features.home.api.domains.entities.categories.Categories
import ru.piotr.features.home.api.domains.entities.categories.MainCategory
import ru.piotr.features.home.api.domains.entities.categories.SubCategory
import ru.piotr.features.home.api.domains.entities.lockapp.LockApp
import ru.piotr.features.home.api.domains.entities.template.Template

/**
 * @author Stanislav Aleshin on 25.02.2023.
 */
@Parcelize
internal data class EditorViewState(
    val editModel: EditModelUi? = null,
    val categories: List<Categories> = emptyList(),
    val templates: List<Template>? = null,
    val timeRangeValid: TimeRangeError? = null,
    val categoryValid: CategoryValidateError? = null,
    val lockedApps: List<LockApp>?= null,
) : BaseViewState

internal sealed class EditorEvent : BaseEvent {
    object Init : EditorEvent()
    data class ChangeCategories(val category: MainCategory, val subCategory: SubCategory?) : EditorEvent()
    data class ChangeTime(val timeRange: TimeRange) : EditorEvent()
    data class ChangeParameters(val parameters: EditParameters) : EditorEvent()
    data class ApplyTemplate(val template: Template) : EditorEvent()
    data class AddSubCategory(val name: String) : EditorEvent()
    object PressControlTemplateButton : EditorEvent()
    object LoadTemplates : EditorEvent()
    object ChangeIsTemplate : EditorEvent()
    object PressDeleteButton : EditorEvent()
    data class PressSaveButton(val isTemplateUpdate: Boolean) : EditorEvent()
    object PressBackButton : EditorEvent()
}

internal sealed class EditorEffect : BaseUiEffect {
    data class ShowError(val failures: EditorFailures) : EditorEffect()
    data class ShowOverlayError(
        val currentTimeRange: TimeRange,
        val failure: EditorFailures.TimeOverlayError,
    ) : EditorEffect()
}

internal sealed class EditorAction : BaseAction {
    object Navigate : EditorAction()
    data class SetUp(val editModel: EditModelUi, val categories: List<Categories>) : EditorAction()
    data class UpdateCategories(val categories: List<Categories>) : EditorAction()
    data class UpdateTimeRange(val timeRange: TimeRange, val duration: Long) : EditorAction()
    data class UpdateTemplateId(val templateId: Int?) : EditorAction()
    data class UpdateEditModel(val editModel: EditModelUi?) : EditorAction()
    data class UpdateTemplates(val templates: List<Template>) : EditorAction()
    data class SetValidError(val timeRange: TimeRangeError?, val category: CategoryValidateError?) : EditorAction()
    data class UpdateLockedApps(val lockedApps: List<LockApp>) : EditorAction()
}
