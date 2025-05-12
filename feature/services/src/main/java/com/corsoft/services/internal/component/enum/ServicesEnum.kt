package com.corsoft.services.internal.component.enum

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.ramcosta.composedestinations.generated.services.destinations.DocumentsScreenDestination
import com.ramcosta.composedestinations.generated.services.destinations.TimerScreenDestination
import com.ramcosta.composedestinations.generated.services.destinations.WeaponsScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

internal enum class ServicesEnum {
    TIMER,
    CALC,
    CALENDAR,
    AMMO,
    GUNS,
    DOCS,
    STAT,
    ANALYZE,
    ;

    @Composable
    fun getName(): String =
        when (this) {
            TIMER -> stringResource(id = CoreStringRes.timer)
            CALC -> stringResource(id = CoreStringRes.calculator)
            CALENDAR -> stringResource(id = CoreStringRes.trainings)
            AMMO -> stringResource(id = CoreStringRes.ammo)
            GUNS -> stringResource(id = CoreStringRes.gun_storage)
            DOCS -> stringResource(id = CoreStringRes.documents)
            STAT -> stringResource(id = CoreStringRes.statistic)
            ANALYZE -> stringResource(id = CoreStringRes.video_analyze)
        }

    @Composable
    @DrawableRes
    fun getIconRes(): Int =
        when (this) {
            TIMER -> CoreDrawableRes.ic_timer_outline
            CALC -> CoreDrawableRes.ic_calc
            CALENDAR -> CoreDrawableRes.ic_calendar
            AMMO -> CoreDrawableRes.ic_ammo
            GUNS -> CoreDrawableRes.ic_gun
            DOCS -> CoreDrawableRes.ic_document
            STAT -> CoreDrawableRes.ic_chart
            ANALYZE -> CoreDrawableRes.ic_scan
        }

    fun navigate(navigator: DestinationsNavigator){
        when (this) {
            TIMER -> navigator.navigate(TimerScreenDestination)
            CALC -> {}
            CALENDAR -> {}
            AMMO -> {}
            GUNS -> navigator.navigate(WeaponsScreenDestination)
            DOCS -> navigator.navigate(DocumentsScreenDestination)
            STAT -> {}
            ANALYZE -> {}
        }
    }

    fun isEnabled(): Boolean =
        when (this) {
            TIMER -> true
            CALC -> false
            CALENDAR -> false
            AMMO -> false
            GUNS -> true
            DOCS -> true
            STAT -> false
            ANALYZE -> false
        }

}