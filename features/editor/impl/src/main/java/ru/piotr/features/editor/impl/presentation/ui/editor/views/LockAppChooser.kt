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
package ru.piotr.features.editor.impl.presentation.ui.editor.views

import android.content.Intent
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import ru.piotr.core.ui.theme.TimePlannerRes
import ru.piotr.core.ui.views.DialogButtons
import ru.piotr.features.editor.impl.presentation.theme.EditorThemeRes
import ru.piotr.features.home.api.data.datasources.lockapps.AppData
import ru.piotr.features.home.api.domains.entities.categories.MainCategory
import java.util.ArrayList

/**
 * @author Stanislav Aleshin on 26.02.2023.
 */
@Composable
internal fun LockAppChooser(
    modifier: Modifier = Modifier,
    mainCategory: MainCategory?,
    allInstalledApps: List<AppData>,
    onAddLockApp: (AppData?) -> Unit,
    onRemoveLockApp: (AppData?) -> Unit,
) {
    val openDialog = rememberSaveable { mutableStateOf(false) }
    Surface(
        onClick = {openDialog.value = true},
        modifier = modifier.sizeIn(minHeight = 68.dp),
        shape = MaterialTheme.shapes.medium,
        tonalElevation = TimePlannerRes.elevations.levelOne,
    ){
    Row(
        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier
            .weight(1f)
            .animateContentSize()) {
            Text(
                text = EditorThemeRes.strings.subCategoryChooserTitle,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelMedium,
            )
            var blockedAppNames : String = ""
            allInstalledApps.onEach {app->
                blockedAppNames += app.appName
            }

            Text(
                text = blockedAppNames ?: EditorThemeRes.strings.categoryNotSelectedTitle,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium,
            )
        }

//        val tint = when (mainCategory != null) {
//            true -> MaterialTheme.colorScheme.onSurface
//            false -> MaterialTheme.colorScheme.surfaceVariant
//        }
        Icon(
            modifier = Modifier.animateContentSize(),
            painter = painterResource(EditorThemeRes.icons.add),
            contentDescription = EditorThemeRes.strings.mainCategoryChooserExpandedIconDesc,
//            tint = tint,
        )
        }
    }

    if (openDialog.value) {
        LockAppDialogChooser(
            allInstalledApps = allInstalledApps,
            onCloseDialog = { openDialog.value = false },
            onAddAppLock = {
//                onAddCategory(it)
            },
            onChooseLockApps = {
//                onSubCategoryChange(it)
                openDialog.value = false
            },
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun LockAppDialogChooser(
    modifier: Modifier = Modifier,
    allInstalledApps: List<AppData>,
    onCloseDialog: () -> Unit,
    onChooseLockApps: (List<AppData>?) -> Unit,
    onAddAppLock: (String) -> Unit,
) {
    var selectedApps by rememberSaveable { mutableStateOf(allInstalledApps) }

    AlertDialog(onDismissRequest = onCloseDialog) {
        Surface(
            modifier = modifier
                .width(280.dp)
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = TimePlannerRes.elevations.levelThree,
        ) {
            Column {
                Column(
                    modifier = Modifier.padding(
                        top = 24.dp,
                        bottom = 8.dp,
                        start = 24.dp,
                        end = 24.dp,
                    ),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    Text(
                        text = EditorThemeRes.strings.subCategoryChooserTitle,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
                LazyColumn(modifier = Modifier.height(300.dp)) {
                    items(selectedApps) { app ->
                        LockAppDialogItem(
                            selected = false,
                            appName = app.appName,
                            description = app.packageName,
                            onSelectChange = { },
                            appIconDrawable = app.appIconDrawable,
                        )
                    }
                }
                DialogButtons(
                    onCancelClick = onCloseDialog,
                    onConfirmClick = { /*onChooseSubCategory.invoke(selectedSubCategory)*/ },
                )
            }
        }
    }
}

@Composable
internal fun LockAppDialogItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    appName: String,
    appIconDrawable: Drawable?,
    description: String?,
    onSelectChange: () -> Unit,
) {
//    Column {
        Row(
            modifier = modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .clip(MaterialTheme.shapes.medium)
                .clickable(onClick = onSelectChange)
                .padding(start = 8.dp, end = 16.dp)
                .sizeIn(minHeight = 56.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start,
            ) {
                Row(modifier = Modifier.padding(horizontal = 8.dp),verticalAlignment = Alignment.CenterVertically,)
                {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (appIconDrawable != null) {
                            Icon(
                                appIconDrawable.toBitmap(config = Bitmap.Config.ARGB_8888).asImageBitmap(),
                                modifier = Modifier.size(30.dp, 30.dp),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                        else{
                            Icon(
                                painter = painterResource(EditorThemeRes.icons.add),
                                modifier = Modifier.size(30.dp, 30.dp),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    }
                    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = appName,
                            color = MaterialTheme.colorScheme.onSurface,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        if (description != null) {
                            Text(
                                text = description,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }

                }
            }
            Checkbox(checked = selected, onCheckedChange = null)
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.outlineVariant,
        )
//    }
}



