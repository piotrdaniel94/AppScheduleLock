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
package ru.piotr.features.home.api.data.repository

import ru.piotr.features.home.api.data.datasources.templates.TemplatesLocalDataSource
import ru.piotr.features.home.api.data.mappers.template.TemplatesDataToDomainMapper
import ru.piotr.features.home.api.data.mappers.template.TemplatesDomainToDataMapper
import ru.piotr.features.home.api.domains.entities.template.Template
import ru.piotr.features.home.api.domains.repository.TemplatesRepository
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 08.03.2023.
 */
class TemplatesRepositoryImpl @Inject constructor(
    private val localDataSource: TemplatesLocalDataSource,
    private val mapperToDomain: TemplatesDataToDomainMapper,
    private val mapperToData: TemplatesDomainToDataMapper,
) : TemplatesRepository {

    override suspend fun fetchAllTemplates(): List<Template> {
        return localDataSource.fetchAllTemplates().map { mapperToDomain.map(it) }
    }

    override suspend fun addTemplate(templates: Template): Int {
        return localDataSource.createTemplate(templates.map(mapperToData)).toInt()
    }

    override suspend fun addTemplates(templates: List<Template>) {
        localDataSource.createTemplates(templates.map { it.map(mapperToData) })
    }

    override suspend fun updateTemplate(template: Template) {
        localDataSource.updateTemplate(template.map(mapperToData))
    }

    override suspend fun deleteTemplateById(id: Int) {
        localDataSource.deleteTemplateById(id)
    }

    override suspend fun deleteAllTemplates() {
        localDataSource.deleteAllTemplates()
    }
}
