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
 * limitations under the License.
*/
package ru.piotr.timeplanner.presentation.ui.splash.contract

import kotlinx.parcelize.Parcelize
import ru.piotr.core.utils.platform.screenmodel.contract.*

/**
 * @author Stanislav Aleshin on 14.02.2023.
 */
@Parcelize
sealed class SplashViewState : BaseViewState {
    object Default : SplashViewState()
}

sealed class SplashEvent : BaseEvent {
    object Init : SplashEvent()
}

sealed class SplashEffect : EmptyUiEffect

sealed class SplashAction : SplashEffect(), BaseAction
