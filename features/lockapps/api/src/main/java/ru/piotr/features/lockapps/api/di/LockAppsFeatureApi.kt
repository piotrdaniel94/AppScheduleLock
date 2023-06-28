package ru.piotr.features.lockapps.api.di

import ru.piotr.features.lockapps.api.navigation.LockAppsFeaturetarter
import ru.piotr.module_injector.BaseFeatureApi

/**
 * @author Stanislav Aleshin on 30.03.2023.
 */
interface LockAppsFeatureApi : BaseFeatureApi {
    fun fetchStarter(): LockAppsFeaturetarter
}
