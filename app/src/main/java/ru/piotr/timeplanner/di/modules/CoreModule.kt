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
package ru.piotr.timeplanner.di.modules

import dagger.Binds
import dagger.Module
import ru.piotr.core.utils.managers.CoroutineManager
import ru.piotr.core.utils.managers.DateManager
import ru.piotr.core.utils.managers.TimeOverlayManager
import ru.piotr.core.utils.notifications.NotificationCreator
import ru.piotr.features.editor.api.presentation.AlarmReceiverProvider
import ru.piotr.features.editor.api.presentation.TimeTaskAlarmManager
import ru.piotr.features.home.api.domains.common.ScheduleStatusManager
import ru.piotr.features.home.api.domains.common.TimeTaskStatusManager
import ru.piotr.timeplanner.navigation.GlobalNavigationManager
import ru.piotr.timeplanner.navigation.TabNavigationManager
import ru.piotr.timeplanner.presentation.receiver.AlarmReceiverProviderImpl
import javax.inject.Singleton

/**
 * @author Stanislav Aleshin on 29.03.2023.
 */
@Module
interface CoreModule {

    @Binds
    fun bindAlarmReceiverProvider(provider: AlarmReceiverProviderImpl): AlarmReceiverProvider

    @Binds
    fun bindNotificationCreator(creator: NotificationCreator.Base): NotificationCreator

    @Binds
    @Singleton
    fun bindCoroutineManager(manager: CoroutineManager.Base): CoroutineManager

    @Binds
    @Singleton
    fun bindAppNavigationManager(manager: GlobalNavigationManager.Base): GlobalNavigationManager

    @Binds
    @Singleton
    fun bindTabNavigationManager(manager: TabNavigationManager.Base): TabNavigationManager

    @Binds
    fun bindTimeTaskAlarmManager(manager: TimeTaskAlarmManager.Base): TimeTaskAlarmManager

    @Binds
    @Singleton
    fun bindTimeOverlayManager(manager: TimeOverlayManager.Base): TimeOverlayManager

    @Binds
    fun bindTimeTaskStatusManager(manager: TimeTaskStatusManager.Base): TimeTaskStatusManager

    @Binds
    fun bindScheduleStatusManager(manager: ScheduleStatusManager.Base): ScheduleStatusManager

    @Binds
    fun bindDateManager(interactor: DateManager.Base): DateManager
}
