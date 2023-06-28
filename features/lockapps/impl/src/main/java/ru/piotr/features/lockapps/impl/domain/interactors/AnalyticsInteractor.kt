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
package ru.piotr.features.lockapps.impl.domain.interactors

import ru.piotr.core.utils.extensions.countMonthByDays
import ru.piotr.core.utils.extensions.countWeeksByDays
import ru.piotr.core.utils.extensions.duration
import ru.piotr.core.utils.extensions.isIncludeTime
import ru.piotr.core.utils.extensions.shiftDay
import ru.piotr.core.utils.extensions.startThisDay
import ru.piotr.core.utils.functional.Constants
import ru.piotr.core.utils.functional.DomainResult
import ru.piotr.core.utils.functional.TimePeriod
import ru.piotr.core.utils.functional.TimeRange
import ru.piotr.core.utils.managers.DateManager
import ru.piotr.features.lockapps.impl.domain.common.AnalyticsEitherWrapper
import ru.piotr.features.lockapps.impl.domain.entities.AnalyticsFailure
import ru.piotr.features.lockapps.impl.domain.entities.CategoryAnalytic
import ru.piotr.features.lockapps.impl.domain.entities.ScheduleAnalytics
import ru.piotr.features.lockapps.impl.domain.entities.SubCategoryAnalytic
import ru.piotr.features.home.api.domains.entities.categories.Categories
import ru.piotr.features.home.api.domains.entities.categories.SubCategory
import ru.piotr.features.home.api.domains.entities.schedules.TimeTask
import ru.piotr.features.home.api.domains.repository.CategoriesRepository
import ru.piotr.features.home.api.domains.repository.ScheduleRepository
import java.util.Date
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 22.04.2023.
 */
internal interface AnalyticsInteractor {

    suspend fun fetchAnalytics(period: TimePeriod): DomainResult<AnalyticsFailure, ScheduleAnalytics>

    class Base @Inject constructor(
        private val scheduleRepository: ScheduleRepository,
        private val categoriesRepository: CategoriesRepository,
        private val dateManager: DateManager,
        private val eitherWrapper: AnalyticsEitherWrapper,
    ) : AnalyticsInteractor {

        override suspend fun fetchAnalytics(period: TimePeriod) = eitherWrapper.wrap {
            val currentDate = dateManager.fetchCurrentDate()
            val shiftAmount = period.convertToDays()
            val startDate = currentDate.startThisDay().shiftDay(-shiftAmount)
            val globalTimeRange = TimeRange(
                from = startDate,
                to = currentDate,
            )
            val schedules = scheduleRepository.fetchSchedulesByRange(globalTimeRange)
            val timeTasks = mutableListOf<TimeTask>().apply {
                schedules.forEach { schedule ->
                    addAll(schedule.timeTasks.filter { it.isConsiderInStatistics })
                }
            }

            val periodCount = when (period) {
                TimePeriod.WEEK -> Constants.Date.DAY
                TimePeriod.MONTH -> Constants.Date.DAYS_IN_WEEK
                TimePeriod.HALF_YEAR -> Constants.Date.DAYS_IN_MONTH
                TimePeriod.YEAR -> Constants.Date.DAYS_IN_MONTH
            }
            val periodValue = when (period) {
                TimePeriod.WEEK -> Constants.Date.DAYS_IN_WEEK
                TimePeriod.MONTH -> countWeeksByDays(shiftAmount)
                TimePeriod.HALF_YEAR -> countMonthByDays(shiftAmount)
                TimePeriod.YEAR -> countMonthByDays(shiftAmount)
            }
            val workLoadPeriodMap = mutableMapOf<TimeRange, List<TimeTask>>().apply {
                repeat(periodValue) {
                    val actualStartDate = startDate.shiftDay(1)
                    val start = actualStartDate.shiftDay(periodCount * it)
                    val end = actualStartDate.shiftDay(periodCount * (it + 1))
                    put(TimeRange(start, end), emptyList())
                }
            }
            timeTasks.forEach { task ->
                workLoadPeriodMap.keys.findLast { it.isIncludeTime(task.date) }?.let {
                    val tasks = checkNotNull(workLoadPeriodMap[it]).toMutableList()
                    workLoadPeriodMap[it] = tasks.apply { add(task) }
                }
            }

            val categoriesAnalytics = countCategoriesAnalytic(
                categories = categoriesRepository.fetchCategories(),
                timeTasks = timeTasks,
            )
            val totalTasksCount = timeTasks.count { it.isConsiderInStatistics }
            val totalTasksTime = timeTasks.map { it.timeRanges }.sumOf { duration(it) }
            val averageDayLoad = if (schedules.isNotEmpty()) totalTasksCount / schedules.size else 0
            val averageTaskTime = if (totalTasksCount != 0) totalTasksTime / totalTasksCount else 0L

            return@wrap ScheduleAnalytics(
                dateWorkLoadMap = workLoadPeriodMap,
                categoriesAnalytics = categoriesAnalytics,
                totalTasksCount = totalTasksCount,
                totalTasksTime = totalTasksTime,
                averageDayLoad = averageDayLoad,
                averageTaskTime = averageTaskTime,
            )
        }

        private fun countCategoriesAnalytic(
            categories: List<Categories>,
            timeTasks: List<TimeTask>,
        ) = mutableListOf<CategoryAnalytic>().let { analytics ->
            categories.onEach { categories ->
                val subCategories = categories.subCategories.toMutableList().apply {
                    add(SubCategory.absentSubCategory(categories.mainCategory))
                }
                val categoriesAnalytic = CategoryAnalytic(
                    mainCategory = categories.mainCategory,
                    subCategoriesInfo = subCategories.map { SubCategoryAnalytic(subCategory = it) },
                )
                analytics.add(categoriesAnalytic)
            }

            timeTasks.forEach { timeTask ->
                val currentDuration = duration(timeTask.timeRanges)
                val analyticModel = analytics.find { it.mainCategory.id == timeTask.category.id }
                if (analyticModel != null) {
                    val index = analytics.indexOf(analyticModel)
                    val duration = analyticModel.duration + currentDuration
                    val subAnalytics = analyticModel.subCategoriesInfo.toMutableList().apply {
                        val subModel = find { it.subCategory.id == (timeTask.subCategory?.id ?: -1) }
                        if (subModel != null) {
                            val subDuration = subModel.duration + currentDuration
                            set(indexOf(subModel), subModel.copy(duration = subDuration))
                        }
                    }
                    analytics[index] = analyticModel.copy(
                        duration = duration,
                        subCategoriesInfo = subAnalytics,
                    )
                }
            }
            return@let analytics.sortedBy { it.duration }.reversed()
        }
    }
}
