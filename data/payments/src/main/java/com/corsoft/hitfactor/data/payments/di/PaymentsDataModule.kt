package com.corsoft.hitfactor.data.payments.di

import android.content.Context
import com.corsoft.hitfactor.data.payments.api.PaymentsRepository
import com.corsoft.hitfactor.data.payments.internal.PaymentsRepositoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.rustore.sdk.billingclient.RuStoreBillingClientFactory
import ru.rustore.sdk.billingclient.presentation.BillingClientTheme
import ru.rustore.sdk.billingclient.provider.BillingClientThemeProvider

val paymentsDataModule = module {
    single {
        RuStoreBillingClientFactory.create(
            context = get(),
            consoleApplicationId = "2063634603",
            deeplinkScheme = "hit_factor_deeplink",
            themeProvider = BillingClientThemeProviderImpl(get())
        )
    }
    single {
        PaymentsRepositoryImpl(get())
    } bind PaymentsRepository::class
}

class BillingClientThemeProviderImpl(
    private val context: Context
) : BillingClientThemeProvider {
    override fun provide(): BillingClientTheme =
        if (context.resources.configuration.uiMode == 33)
            BillingClientTheme.Dark
        else
            BillingClientTheme.Light
}