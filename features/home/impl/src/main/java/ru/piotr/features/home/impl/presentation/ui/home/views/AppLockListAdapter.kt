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

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.piotr.features.home.impl.presentation.ui.home.views.lockitem.AppLockItemBaseViewState
import ru.piotr.features.home.impl.presentation.ui.home.views.lockitem.AppLockItemItemViewState
//import ru.piotr.features.home.impl.presentation.ui.home.views.viewholder.AppLockItemViewHolder
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class AppLockListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var appItemClicked: ((AppLockItemItemViewState) -> Unit)? = null

    private val itemViewStateList: ArrayList<AppLockItemBaseViewState> = arrayListOf()

    @SuppressLint("CheckResult")
    fun setAppDataList(itemViewStateList: List<AppLockItemBaseViewState>) {
//        Single
//            .create<DiffUtil.DiffResult> {
//                val diffResult = DiffUtil.calculateDiff(AppLockListDiffUtil(this.itemViewStateList, itemViewStateList))
//                it.onSuccess(diffResult)
//            }.subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    this.itemViewStateList.clear()
//                    this.itemViewStateList.addAll(itemViewStateList)
//                    it.dispatchUpdatesTo(this)
//                },
//                { error ->
//                    Bugsnag.notify(error)
//                    this.itemViewStateList.clear()
//                    this.itemViewStateList.addAll(itemViewStateList)
//                    notifyDataSetChanged()
//                })
    }

    override fun getItemCount(): Int = itemViewStateList.size

    override fun getItemViewType(position: Int): Int {
        return when (itemViewStateList[position]) {
//            is AppLockItemHeaderViewState -> TYPE_HEADER
            is AppLockItemItemViewState -> TYPE_APP_ITEM
            else -> throw IllegalArgumentException("No type found")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return when (viewType) {
//            TYPE_APP_ITEM -> AppLockItemViewHolder.create(parent, appItemClicked)
//            TYPE_HEADER -> HeaderViewHolder.create(parent)
//            else -> throw IllegalStateException("No type found")
//        }
        return throw IllegalStateException("No type found")
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        when (holder) {
//            is AppLockItemViewHolder -> holder.bind(itemViewStateList[position] as AppLockItemItemViewState)
//            is HeaderViewHolder -> holder.bind(itemViewStateList[position] as AppLockItemHeaderViewState)
//        }
    }

    companion object {

        private const val TYPE_HEADER = 0
        private const val TYPE_APP_ITEM = 1
    }
}