package ru.piotr.features.lockapps.api.navigation

import cafe.adriel.voyager.core.screen.Screen

/**
 * @author Stanislav Aleshin on 30.03.2023.
 */
interface LockAppsFeatureStarter {
    fun provideMainScreen(): Screen
}
