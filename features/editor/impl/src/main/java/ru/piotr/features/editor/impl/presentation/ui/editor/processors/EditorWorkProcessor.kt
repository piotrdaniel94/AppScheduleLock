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
package ru.piotr.features.editor.impl.presentation.ui.editor.processors

import ru.piotr.core.utils.extensions.duration
import ru.piotr.core.utils.functional.Either
import ru.piotr.core.utils.functional.TimeRange
import ru.piotr.core.utils.functional.rightOrElse
import ru.piotr.core.utils.platform.screenmodel.work.*
import ru.piotr.features.editor.impl.domain.common.convertToEditModel
import ru.piotr.features.editor.impl.domain.common.convertToTemplate
import ru.piotr.features.editor.impl.domain.interactors.CategoriesInteractor
import ru.piotr.features.editor.impl.domain.interactors.EditorInteractor
import ru.piotr.features.editor.impl.domain.interactors.LockAppsInteractor
import ru.piotr.features.editor.impl.domain.interactors.TemplatesInteractor
import ru.piotr.features.editor.impl.navigation.NavigationManager
import ru.piotr.features.editor.impl.presentation.mappers.mapToDomain
import ru.piotr.features.editor.impl.presentation.mappers.mapToUi
import ru.piotr.features.editor.impl.presentation.models.EditModelUi
import ru.piotr.features.editor.impl.presentation.ui.editor.contract.EditorAction
import ru.piotr.features.editor.impl.presentation.ui.editor.contract.EditorEffect
import ru.piotr.features.home.api.data.datasources.lockapps.AppData
import ru.piotr.features.home.api.domains.entities.categories.MainCategory
import ru.piotr.features.home.api.domains.entities.categories.SubCategory
import ru.piotr.features.home.api.domains.entities.lockapp.LockApp
import ru.piotr.features.home.api.domains.entities.template.Template
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 26.03.2023.
 */
internal interface EditorWorkProcessor : WorkProcessor<EditorWorkCommand, EditorAction, EditorEffect> {

    class Base @Inject constructor(
        private val editorInteractor: EditorInteractor,
        private val categoriesInteractor: CategoriesInteractor,
        private val templatesInteractor: TemplatesInteractor,
        private val navigationManager: NavigationManager,
        private val lockAppIteractor: LockAppsInteractor,
    ) : EditorWorkProcessor {

        override suspend fun work(command: EditorWorkCommand) = when (command) {
            is EditorWorkCommand.GoBack -> navigateToBack()
            is EditorWorkCommand.GoTemplates -> navigateToTemplates()
            is EditorWorkCommand.AddSubCategory -> addSubCategoryWork(command.name, command.mainCategory)
            is EditorWorkCommand.LoadSendEditModel -> loadSendModel()
            is EditorWorkCommand.ChangeIsTemplate -> changeIsTemplate(command.editModel)
            is EditorWorkCommand.ChangeTimeRange -> changeTimeRange(command.timeRange)
            is EditorWorkCommand.ApplyTemplate -> applyTemplate(command.template, command.model)
            is EditorWorkCommand.AddLockApp -> addLockAppWork(command.app, command.mainCategory)
        }

        private fun navigateToBack(): WorkResult<EditorAction, EditorEffect> {
            return navigationManager.navigateToPreviousFeature().let {
                ActionResult(EditorAction.Navigate)
            }
        }

        private fun navigateToTemplates(): WorkResult<EditorAction, EditorEffect> {
            return navigationManager.navigateToTemplatesScreen().let {
                ActionResult(EditorAction.Navigate)
            }
        }

        private suspend fun addSubCategoryWork(
            name: String,
            mainCategory: MainCategory,
        ): WorkResult<EditorAction, EditorEffect> {
            val subCategory = SubCategory(name = name, mainCategory = mainCategory)
            return when (val result = categoriesInteractor.addSubCategory(subCategory)) {
                is Either.Right -> {
                    when (val categories = categoriesInteractor.fetchCategories()) {
                        is Either.Right -> ActionResult(EditorAction.UpdateCategories(categories.data))
                        is Either.Left -> EffectResult(EditorEffect.ShowError(categories.data))
                    }
                }
                is Either.Left -> EffectResult(EditorEffect.ShowError(result.data))
            }
        }

        private suspend fun addLockAppWork(app: AppData, category: MainCategory): WorkResult<EditorAction, EditorEffect>
        {
            val lockApp = LockApp(name = app.appName, packageName = app.packageName, mainCategory = category)
            return when (val result = lockAppIteractor.addLockApp(lockApp)) {
                is Either.Right -> {
                    when (val lockedApps = lockAppIteractor.fetchLockApps(category)) {
                        is Either.Right -> ActionResult(EditorAction.UpdateLockedApps(lockedApps.data))
                        is Either.Left -> EffectResult(EditorEffect.ShowError(lockedApps.data))
                    }
                }
                is Either.Left -> EffectResult(EditorEffect.ShowError(result.data))
            }
        }

        private suspend fun loadSendModel(): WorkResult<EditorAction, EditorEffect> {
            val editModel = editorInteractor.fetchEditModel().mapToUi()
            return when (val result = categoriesInteractor.fetchCategories()) {
                is Either.Right -> ActionResult(EditorAction.SetUp(editModel, result.data))
                is Either.Left -> EffectResult(EditorEffect.ShowError(result.data))
            }
        }

        private suspend fun changeIsTemplate(editModel: EditModelUi): WorkResult<EditorAction, EditorEffect> {
            val currentTemplateId = editModel.templateId
            val newId = if (currentTemplateId == null) {
                val template = editModel.mapToDomain().convertToTemplate()
                templatesInteractor.addTemplate(template).rightOrElse(null)
            } else {
                templatesInteractor.deleteTemplateById(currentTemplateId).let { result ->
                    when (result) {
                        is Either.Right -> null
                        is Either.Left -> return EffectResult(EditorEffect.ShowError(result.data))
                    }
                }
            }
            return ActionResult(EditorAction.UpdateTemplateId(newId))
        }

        private fun changeTimeRange(timeRange: TimeRange): WorkResult<EditorAction, EditorEffect> {
            val duration = duration(timeRange)
            return ActionResult(EditorAction.UpdateTimeRange(timeRange, duration))
        }

        private fun applyTemplate(
            template: Template,
            model: EditModelUi,
        ): WorkResult<EditorAction, EditorEffect> {
            val domainEditModel = template.convertToEditModel(model.date).copy(key = model.key)
            return ActionResult(EditorAction.UpdateEditModel(domainEditModel.mapToUi()))
        }
    }
}

internal sealed class EditorWorkCommand : WorkCommand {
    object GoBack : EditorWorkCommand()
    data class AddSubCategory(val name: String, val mainCategory: MainCategory) : EditorWorkCommand()
    object GoTemplates : EditorWorkCommand()
    object LoadSendEditModel : EditorWorkCommand()
    data class ChangeIsTemplate(val editModel: EditModelUi) : EditorWorkCommand()
    data class ChangeTimeRange(val timeRange: TimeRange) : EditorWorkCommand()
    data class ApplyTemplate(val template: Template, val model: EditModelUi) : EditorWorkCommand()
    data class AddLockApp(val app: AppData, val mainCategory: MainCategory) : EditorWorkCommand()
}
