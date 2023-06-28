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
package ru.piotr.features.settings.impl.domain.common

import kotlinx.parcelize.Parcelize
import ru.piotr.core.utils.functional.DomainFailures

/**
 * @author Stanislav Aleshin on 17.02.2023.
 */
@Parcelize
internal sealed class SettingsFailures : DomainFailures {
    data class BackupError(val throwable: Throwable) : SettingsFailures()
    data class OtherError(val throwable: Throwable) : SettingsFailures()
}
