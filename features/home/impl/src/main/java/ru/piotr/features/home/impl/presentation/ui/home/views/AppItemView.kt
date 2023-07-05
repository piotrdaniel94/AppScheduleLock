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
package ru.piotr.features.home.impl.presentation.ui.home.views

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import ru.piotr.core.ui.views.toMinutesOrHoursTitle
import ru.piotr.features.home.api.presentation.mappers.fetchNameByLanguage
import ru.piotr.features.home.api.presentation.mappers.toDescription
import ru.piotr.features.home.api.presentation.mappers.toIconPainter
import ru.piotr.features.home.impl.presentation.models.TimeTaskUi

@Composable
internal fun LazyItemScope.AppLockedItem(
    modifier: Modifier = Modifier,
    model: TimeTaskUi,
    onMoreButtonClick: (Long) -> Unit,
    onIncreaseTime: () -> Unit,
    onReduceTime: () -> Unit,
    isCompactView: Boolean = true,
) {
    Column(
        modifier = modifier.fillMaxHeight().padding(top = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            StartTaskTimeTitle(time = model.startTime)
            Box(modifier = Modifier.padding(vertical = 8.dp).weight(1f)) {
                LinearProgressIndicator(
                    progress = model.progress,
                    modifier = Modifier.clip(MaterialTheme.shapes.small).fillMaxWidth().animateContentSize(),
                )
            }
            Text(
                text = model.leftTime.toMinutesOrHoursTitle(),
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            with(model) {
                EndTaskTimeTitle(
                    modifier = modifier.align(Alignment.Bottom),
                    time = model.endTime,
                    isVisible = isCompactView,
                )
                RunningTimeTask(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp).animateContentSize(),
                    onMoreButtonClick = { onMoreButtonClick.invoke(key) },
                    onIncreaseTime = onIncreaseTime,
                    onReduceTime = onReduceTime,
                    taskTitle = mainCategory.fetchNameByLanguage(),
                    taskSubTitle = subCategory?.name,
                    categoryIcon = mainCategory.icon?.toIconPainter(),
                    categoryIconDescription = mainCategory.icon?.toDescription(),
                    isImportant = isImportant,
                )
            }
        }
    }
}

@Composable
internal fun LazyItemScope.AppNormalItem(
    modifier: Modifier = Modifier,
    model: TimeTaskUi,
    onMoreButtonClick: (Long) -> Unit,
    onIncreaseTime: () -> Unit,
    onReduceTime: () -> Unit,
    isCompactView: Boolean = true,
) {
    Column(
        modifier = modifier.fillMaxHeight().padding(top = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            StartTaskTimeTitle(time = model.startTime)
            Box(modifier = Modifier.padding(vertical = 8.dp).weight(1f)) {
                LinearProgressIndicator(
                    progress = model.progress,
                    modifier = Modifier.clip(MaterialTheme.shapes.small).fillMaxWidth().animateContentSize(),
                )
            }
            Text(
                text = model.leftTime.toMinutesOrHoursTitle(),
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            with(model) {
                EndTaskTimeTitle(
                    modifier = modifier.align(Alignment.Bottom),
                    time = model.endTime,
                    isVisible = isCompactView,
                )
                LocalContext.current
                RunningTimeTask(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp).animateContentSize(),
                    onMoreButtonClick = { onMoreButtonClick.invoke(key) },
                    onIncreaseTime = onIncreaseTime,
                    onReduceTime = onReduceTime,
                    taskTitle = mainCategory.fetchNameByLanguage(),
                    taskSubTitle = subCategory?.name,
                    categoryIcon = mainCategory.icon?.toIconPainter(),
                    categoryIconDescription = mainCategory.icon?.toDescription(),
                    isImportant = isImportant,
                )
            }
        }
    }
}