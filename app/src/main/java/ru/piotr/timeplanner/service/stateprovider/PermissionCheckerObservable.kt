package ru.piotr.timeplanner.service.stateprovider

import android.content.Context
import io.reactivex.Flowable
import ru.piotr.timeplanner.presentation.ui.permissions.PermissionChecker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PermissionCheckerObservable @Inject constructor(val context: Context) {

    fun get(): Flowable<Boolean> {
        return Flowable.interval(30, TimeUnit.MINUTES)
            .map { PermissionChecker.checkUsageAccessPermission(context).not() }
    }
}