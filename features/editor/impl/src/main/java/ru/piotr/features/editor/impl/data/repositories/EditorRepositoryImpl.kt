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
package ru.piotr.features.editor.impl.data.repositories

import ru.piotr.features.editor.impl.data.datasources.EditorLocalDataSource
import ru.piotr.features.editor.impl.domain.entites.EditModel
import ru.piotr.features.editor.impl.domain.repositories.EditorRepository
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 07.03.2023.
 */
internal class EditorRepositoryImpl @Inject constructor(
    private val localDataSource: EditorLocalDataSource,
) : EditorRepository {

    override fun saveEditModel(model: EditModel) {
        localDataSource.saveEditModel(model)
    }

    override fun fetchEditModel(): EditModel {
        return localDataSource.fetchEditModel()
    }
}
