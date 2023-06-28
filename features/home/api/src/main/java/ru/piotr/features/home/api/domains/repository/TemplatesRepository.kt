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
package ru.piotr.features.home.api.domains.repository

import ru.piotr.features.home.api.domains.entities.template.Template

/**
 * @author Stanislav Aleshin on 08.03.2023.
 */
interface TemplatesRepository {
    suspend fun addTemplates(templates: List<Template>)
    suspend fun addTemplate(templates: Template): Int
    suspend fun fetchAllTemplates(): List<Template>
    suspend fun updateTemplate(template: Template)
    suspend fun deleteTemplateById(id: Int)
    suspend fun deleteAllTemplates()
}
