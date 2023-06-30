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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import ru.piotr.core.ui.views.toMinutesOrHoursTitle
import ru.piotr.core.utils.extensions.duration
import ru.piotr.features.home.api.presentation.mappers.fetchNameByLanguage
import ru.piotr.features.home.api.presentation.mappers.toDescription
import ru.piotr.features.home.api.presentation.mappers.toIconPainter
import ru.piotr.features.home.impl.presentation.models.TimeTaskUi
import ru.piotr.features.home.impl.presentation.theme.HomeThemeRes
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Stanislav Aleshin on 24.02.2023.
 */
@Composable
internal fun LazyItemScope.PlannedTimeTaskItem(
    modifier: Modifier = Modifier,
    model: TimeTaskUi,
    isCompactView: Boolean = true,
    onItemClick: (Long) -> Unit,
) {
    Column(
        modifier = modifier.padding(top = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
//            StartTaskTimeTitle(time = model.startTime)
            LinearProgressIndicator(
                progress = 0f,
                modifier = Modifier.fillMaxWidth().clip(MaterialTheme.shapes.small),
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            with(model) {
//                EndTaskTimeTitle(
//                    modifier = modifier.align(Alignment.Bottom),
//                    time = model.endTime,
//                    isVisible = isCompactView,
//                )
                PlannedTimeTask(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp).animateContentSize(),
                    onViewClicked = { onItemClick.invoke(key) },
                    taskTitle = mainCategory.fetchNameByLanguage(),
                    taskSubTitle = model.subCategory?.name,
                    taskDurationTitle = duration.toMinutesOrHoursTitle(),
                    categoryIcon = mainCategory.icon?.toIconPainter(),
                    categoryIconDescription = mainCategory.icon?.toDescription(),
                    isImportant = isImportant,
                )
            }
        }
    }
}

@Composable
internal fun LazyItemScope.CompletedTimeTaskItem(
    modifier: Modifier = Modifier,
    model: TimeTaskUi,
    isCompactView: Boolean = true,
    onItemClick: (Long) -> Unit,
    onDoneChange: () -> Unit,
) {
    Column(
        modifier = modifier.padding(top = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            StartTaskTimeTitle(time = model.startTime)
            LinearProgressIndicator(
                progress = 1f,
                modifier = Modifier.fillMaxWidth().clip(MaterialTheme.shapes.small),
                color = when (model.isCompleted) {
                    true -> MaterialTheme.colorScheme.tertiary
                    false -> MaterialTheme.colorScheme.secondary
                },
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            with(model) {
                EndTaskTimeTitle(
                    modifier = modifier.align(Alignment.Bottom),
                    isVisible = isCompactView,
                    time = model.endTime,
                )
                CompletedTimeTask(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp).animateContentSize(),
                    onViewClicked = { onItemClick.invoke(key) },
                    onDoneChange = onDoneChange,
                    taskTitle = mainCategory.fetchNameByLanguage(),
                    taskSubTitle = subCategory?.name,
                    categoryIcon = mainCategory.icon?.toIconPainter(),
                    categoryIconDescription = mainCategory.icon?.toDescription(),
                    isCompleted = isCompleted,
                )
            }
        }
    }
}

@Composable
internal fun LazyItemScope.RunningTimeTaskItem(
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
internal fun LazyItemScope.RunningLockTaskItem(
    modifier: Modifier = Modifier,
    model: TimeTaskUi,
    onMoreButtonClick: (Long) -> Unit,
    onChangeStartTime: (Date) -> Unit,
    onChangeEndTime: (Date) -> Unit,
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
//            StartTaskTimeTitle(time = model.startTime)
            Box(modifier = Modifier.padding(vertical = 8.dp).weight(1f)) {
                LinearProgressIndicator(
                    progress = model.progress,
                    modifier = Modifier.clip(MaterialTheme.shapes.small).fillMaxWidth().animateContentSize(),
                )
            }
//            Text(
//                text = model.leftTime.toMinutesOrHoursTitle(),
//                color = MaterialTheme.colorScheme.primary,
//            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            with(model) {
//                EndTaskTimeTitle(
//                    modifier = modifier.align(Alignment.Bottom),
//                    time = model.endTime,
//                    isVisible = isCompactView,
//                )
                LockingAppTask(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp).animateContentSize(),
                    onMoreButtonClick = { onMoreButtonClick.invoke(key) },
                    startTime = model.startTime,
                    endTime = model.endTime,
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
internal fun LazyItemScope.AddTimeTaskViewItem(
    modifier: Modifier = Modifier,
    onAddTimeTask: () -> Unit,
    startTime: Date,
    endTime: Date,
    indicatorColor: Color = MaterialTheme.colorScheme.surfaceVariant,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//        StartTaskTimeTitle(time = startTime)
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(modifier = Modifier.padding(vertical = 8.dp).weight(1f)) {
                    LinearProgressIndicator(
                        modifier = Modifier.height(4.dp).clip(MaterialTheme.shapes.small).fillMaxWidth(),
                        progress = 0f,
                        trackColor = indicatorColor,
                    )
                }
            }
            AddTimeTaskView(
                onViewClicked = { onAddTimeTask.invoke() },
                remainingTimeTitle = duration(startTime, endTime).toMinutesOrHoursTitle(),
            )
        }
    }
}

@Composable
fun StartTaskTimeTitle(
    modifier: Modifier = Modifier,
    time: Date,
) {
    val timeFormat = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT)
    Text(
        modifier = modifier.defaultMinSize(minWidth = 42.dp),
        text = timeFormat.format(time),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
fun EndTaskTimeTitle(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    time: Date,
) {
    val timeFormat = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT)
    Text(
        modifier = modifier.defaultMinSize(minWidth = 42.dp).alpha(if (isVisible) 1f else 0f),
        text = timeFormat.format(time),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.titleMedium,
    )
}

@Composable
internal fun LazyItemScope.EmptyItem(height: Dp = 50.dp) {
    Spacer(modifier = Modifier.height(height).fillMaxWidth())
}

@Composable
internal fun LazyItemScope.TimeTaskPlaceHolderItem() {
    Row(
        modifier = Modifier.padding(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = HomeThemeRes.strings.startTimeTaskTitlePlaceHolder,
            modifier = Modifier.height(24.dp).homePlaceHolder(),
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(modifier = Modifier.fillMaxWidth().height(24.dp).homePlaceHolder())
            Box(modifier = Modifier.fillMaxWidth().height(80.dp).homePlaceHolder())
        }
    }
}

@Composable
internal fun Modifier.homePlaceHolder(
    shape: Shape = MaterialTheme.shapes.medium,
) = placeholder(
    visible = true,
    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
    shape = shape,
    highlight = PlaceholderHighlight.shimmer(highlightColor = MaterialTheme.colorScheme.surfaceVariant),
)
