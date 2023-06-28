package ru.piotr.features.analytics.api.di

import ru.piotr.features.analytics.api.navigation.AnalyticsFeatureStarter
import ru.piotr.module_injector.BaseFeatureApi

/**
 * @author Stanislav Aleshin on 30.03.2023.
 */
interface AnalyticsFeatureApi : BaseFeatureApi {
    fun fetchStarter(): AnalyticsFeatureStarter
}
