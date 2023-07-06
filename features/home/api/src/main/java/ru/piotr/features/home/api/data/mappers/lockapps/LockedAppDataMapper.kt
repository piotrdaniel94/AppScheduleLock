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
package ru.piotr.features.home.api.data.mappers.lockapps

import ru.piotr.features.home.api.data.datasources.lockapps.LockedAppEntity
import ru.piotr.features.home.api.domains.entities.categories.MainCategory
import ru.piotr.features.home.api.domains.entities.lockapp.LockApp

fun LockedAppEntity.mapToDomain(mainCate: MainCategory)= LockApp(
    name = name,
    packageName = packageName,
    mainCategory = mainCate,
)

fun LockApp.mapToData() = LockedAppEntity(
    name = name,
    packageName = packageName,
    mainCategoryId = mainCategory?.id,
)