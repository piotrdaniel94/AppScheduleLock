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
package ru.aleshin.timeplanner.presentation.receiver

import android.content.Context
import android.content.Intent
import ru.aleshin.core.utils.functional.Constants
import ru.aleshin.features.editor.api.presentation.AlarmReceiverProvider
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 29.03.2023.
 */
class AlarmReceiverProviderImpl @Inject constructor(
    private val context: Context,
) : AlarmReceiverProvider {

    override fun provideReceiverIntent(
        category: String,
        subCategory: String,
        icon: Int?,
        appIcon: Int,
    ) = Intent(context, TimeTaskAlarmReceiver::class.java).apply {
        action = Constants.Alarm.ALARM_NOTIFICATION_ACTION
        putExtra(Constants.Alarm.NOTIFICATION_CATEGORY, category)
        putExtra(Constants.Alarm.NOTIFICATION_SUBCATEGORY, subCategory)
        putExtra(Constants.Alarm.NOTIFICATION_ICON, icon)
        putExtra(Constants.Alarm.APP_ICON, appIcon)
    }
}
