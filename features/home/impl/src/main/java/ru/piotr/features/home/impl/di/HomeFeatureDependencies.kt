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
package ru.piotr.features.home.impl.di

import ru.piotr.core.utils.managers.CoroutineManager
import ru.piotr.core.utils.managers.DateManager
import ru.piotr.core.utils.navigation.Router
import ru.piotr.features.editor.api.navigations.EditorFeatureStarter
import ru.piotr.features.home.api.domains.common.ScheduleStatusManager
import ru.piotr.features.home.api.domains.common.TimeTaskStatusManager
import ru.piotr.features.home.api.domains.repository.CategoriesRepository
import ru.piotr.features.home.api.domains.repository.ScheduleRepository
import ru.piotr.features.home.api.domains.repository.SubCategoriesRepository
import ru.piotr.features.home.api.domains.repository.TemplatesRepository
import ru.piotr.features.home.api.domains.repository.TimeTaskRepository
import ru.piotr.module_injector.BaseFeatureDependencies

/**
 * @author Stanislav Aleshin on 18.02.2023.
 */
interface HomeFeatureDependencies : BaseFeatureDependencies {
    val globalRouter: Router
    val editorFeatureStarter: EditorFeatureStarter
    val schedulesRepository: ScheduleRepository
    val timeTaskRepository: TimeTaskRepository
    val templatesRepository: TemplatesRepository
    val categoriesRepository: CategoriesRepository
    val subCategoriesRepository: SubCategoriesRepository
    val coroutineManager: CoroutineManager
    val scheduleStatusManager: ScheduleStatusManager
    val taskStatusManager: TimeTaskStatusManager
    val dateManger: DateManager
}
