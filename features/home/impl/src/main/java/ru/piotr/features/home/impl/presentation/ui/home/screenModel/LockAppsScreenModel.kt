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
package ru.piotr.features.home.impl.presentation.ui.home.screenModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.piotr.features.home.api.data.datasources.lockapps.AppData
import ru.piotr.features.home.api.data.datasources.lockapps.LockAppsLocalDataSource
import ru.piotr.features.home.api.data.datasources.lockapps.LockedAppEntity
import ru.piotr.features.home.api.data.datasources.lockapps.LockedAppsDao
import ru.piotr.features.home.impl.presentation.ui.home.views.lockitem.AppLockItemBaseViewState
import ru.piotr.features.home.impl.presentation.ui.home.views.lockitem.AppLockItemItemViewState
import ru.piotr.features.home.impl.presentation.ui.home.views.lockitem.LockedAppListViewStateCreator
import javax.inject.Inject

class LockAppsScreenModel @Inject constructor(
    val appsLocalDataSource: LockAppsLocalDataSource,
    val lockedAppsDao: LockedAppsDao
) : ViewModel()  {

//    private val appDataViewStateListLiveData = MutableLiveData<List<AppLockItemBaseViewState>>()
//
//    init {
//        val installedAppsObservable: List<AppData> = appsLocalDataSource.fetchInstalledAppList()
//        val lockedAppsObservable = lockedAppsDao.getLockedAppsSync()
//
//        disposables += LockedAppListViewStateCreator.create(installedAppsObservable, lockedAppsObservable)
//            .map(AddSectionHeaderViewStateFunction())
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                appDataViewStateListLiveData.value = it
//            }
//    }
//
//    fun getAppDataListLiveData(): LiveData<List<AppLockItemBaseViewState>> = appDataViewStateListLiveData
//
//    fun lockApp(appLockItemViewState: AppLockItemItemViewState) {
//        disposables += doOnBackground {
//            lockedAppsDao.lockApp(
//                LockedAppEntity(
//                    appLockItemViewState.appData.packageName
//                )
//            )
//        }
//    }
//
//    fun unlockApp(appLockItemViewState: AppLockItemItemViewState) {
//        disposables += doOnBackground {
//            lockedAppsDao.unlockApp(appLockItemViewState.appData.packageName)
//        }
//    }
}