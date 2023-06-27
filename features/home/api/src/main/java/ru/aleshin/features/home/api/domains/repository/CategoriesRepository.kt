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
package ru.aleshin.features.home.api.domains.repository

import ru.aleshin.features.home.api.domains.entities.categories.Categories
import ru.aleshin.features.home.api.domains.entities.categories.MainCategory

/**
 * @author Stanislav Aleshin on 15.04.2023.
 */
interface CategoriesRepository {
    suspend fun addMainCategories(categories: List<MainCategory>)
    suspend fun fetchCategories(): List<Categories>
    suspend fun fetchCategoriesByType(mainCategory: MainCategory): Categories?
    suspend fun updateMainCategory(category: MainCategory)
    suspend fun deleteMainCategory(category: MainCategory)
    suspend fun deleteAllCategories()
}
