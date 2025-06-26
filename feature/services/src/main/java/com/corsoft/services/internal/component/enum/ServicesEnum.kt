package com.corsoft.services.internal.component.enum

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.ramcosta.composedestinations.generated.services.destinations.CalculateHFScreenDestination
import com.ramcosta.composedestinations.generated.services.destinations.DocumentsScreenDestination
import com.ramcosta.composedestinations.generated.services.destinations.LawScreenDestination
import com.ramcosta.composedestinations.generated.services.destinations.RangesScreenDestination
import com.ramcosta.composedestinations.generated.services.destinations.RulesScreenDestination
import com.ramcosta.composedestinations.generated.services.destinations.TimerScreenDestination
import com.ramcosta.composedestinations.generated.services.destinations.TrainersScreenDestination
import com.ramcosta.composedestinations.generated.services.destinations.TrainingsScreenDestination
import com.ramcosta.composedestinations.generated.services.destinations.WeaponsScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

internal enum class ServicesEnum {
    TIMER,
    CALC,
    CALENDAR,
    RANGES,
    TRAINERS,
    AMMO,
    GUNS,
    DOCS,
    STAT,
    ANALYZE,
    RESULTS,
    RULES,
    LAW,
    FEEDBACK
    ;

    @Composable
    fun getName(): String =
        when (this) {
            TIMER -> stringResource(id = CoreStringRes.timer)
            CALC -> stringResource(id = CoreStringRes.calculator)
            CALENDAR -> stringResource(id = CoreStringRes.trainings)
            RANGES -> stringResource(id = CoreStringRes.ranges)
            TRAINERS -> stringResource(id = CoreStringRes.trainers)
            AMMO -> stringResource(id = CoreStringRes.ammo)
            GUNS -> stringResource(id = CoreStringRes.gun_storage)
            DOCS -> stringResource(id = CoreStringRes.documents)
            STAT -> stringResource(id = CoreStringRes.statistic)
            ANALYZE -> stringResource(id = CoreStringRes.video_analyze)
            RESULTS -> stringResource(id = CoreStringRes.results)
            RULES -> stringResource(id = CoreStringRes.rules)
            LAW -> stringResource(id = CoreStringRes.federal_law_150)
            FEEDBACK -> stringResource(id = CoreStringRes.feedback)

        }

    @Composable
    fun getDescription(): String =
        when (this) {
            TIMER -> stringResource(id = CoreStringRes.timer_desc)
            CALC -> stringResource(id = CoreStringRes.calc_desc)
            CALENDAR -> stringResource(id = CoreStringRes.calendar_desc)
            RANGES -> stringResource(id = CoreStringRes.ranges_desc)
            TRAINERS -> stringResource(id = CoreStringRes.trainers_desc)
            AMMO -> stringResource(id = CoreStringRes.ammo_desc)
            GUNS -> stringResource(id = CoreStringRes.guns_desc)
            DOCS -> stringResource(id = CoreStringRes.docs_desc)
            STAT -> stringResource(id = CoreStringRes.stat_desc)
            ANALYZE -> stringResource(id = CoreStringRes.analyze_desc)
            RESULTS -> stringResource(id = CoreStringRes.results_desc)
            RULES -> stringResource(id = CoreStringRes.rules_desc)
            LAW -> stringResource(id = CoreStringRes.law_desc)
            FEEDBACK -> stringResource(id = CoreStringRes.feedback_desc)
        }

    @Composable
    @DrawableRes
    fun getIconRes(): Int =
        when (this) {
            TIMER -> CoreDrawableRes.ic_timer_outline
            CALC -> CoreDrawableRes.ic_calc
            CALENDAR -> CoreDrawableRes.ic_calendar
            RANGES -> CoreDrawableRes.ic_home
            TRAINERS -> CoreDrawableRes.is_search_people
            AMMO -> CoreDrawableRes.ic_ammo
            GUNS -> CoreDrawableRes.ic_gun
            DOCS -> CoreDrawableRes.ic_document
            STAT -> CoreDrawableRes.ic_chart
            ANALYZE -> CoreDrawableRes.ic_scan
            RESULTS -> CoreDrawableRes.ic_results
            RULES -> CoreDrawableRes.ic_rules
            LAW -> CoreDrawableRes.ic_law
            FEEDBACK -> CoreDrawableRes.ic_feedback
        }

    fun navigate(navigator: DestinationsNavigator, context: Context) {
        when (this) {
            TIMER -> navigator.navigate(TimerScreenDestination)
            CALC -> navigator.navigate(CalculateHFScreenDestination(0))
            CALENDAR -> navigator.navigate(TrainingsScreenDestination)
            RANGES -> navigator.navigate(RangesScreenDestination)
            TRAINERS -> navigator.navigate(TrainersScreenDestination)
            AMMO -> {}
            GUNS -> navigator.navigate(WeaponsScreenDestination)
            DOCS -> navigator.navigate(DocumentsScreenDestination)
            STAT -> {}
            ANALYZE -> {}
            RESULTS -> {}
            RULES -> navigator.navigate(RulesScreenDestination)
            LAW -> navigator.navigate(LawScreenDestination)
            FEEDBACK -> {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://forms.yandex.ru/u/682bc3eeeb61468a89c0c0b5")
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
            }
        }
    }

    fun isEnabled(): Boolean =
        when (this) {
            TIMER -> true
            CALC -> true
            CALENDAR -> true
            RANGES -> true
            TRAINERS -> true
            AMMO -> false
            GUNS -> true
            DOCS -> true
            STAT -> false
            ANALYZE -> false
            RESULTS -> false
            RULES -> true
            LAW -> true
            FEEDBACK -> true
        }

}