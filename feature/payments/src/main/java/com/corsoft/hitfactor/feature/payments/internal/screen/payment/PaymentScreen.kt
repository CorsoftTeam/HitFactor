package com.corsoft.hitfactor.feature.payments.internal.screen.payment

import LoadingCircle
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.corsoft.hitfactor.feature.payments.api.PaymentsNavGraph
import com.corsoft.hitfactor.feature.payments.api.PaymentsNavigator
import com.corsoft.resources.CoreStringRes
import com.corsoft.ui.components.button.HFButton
import com.corsoft.ui.components.snackbar.HFSnackBarHost
import com.corsoft.ui.theme.HitFactorTheme
import com.corsoft.ui.util.observeWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Destination<PaymentsNavGraph>(start = true)
internal fun PaymentScreen(
    navigator: DestinationsNavigator,
    paymentsNavigator: PaymentsNavigator,
    viewModel: PaymentViewModel = koinViewModel()
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.effect.observeWithLifecycle { effect ->
        when (effect) {
            is PaymentEffect.GoNext -> {
                paymentsNavigator.back()
            }

            is PaymentEffect.ShowError -> scope.launch {
                snackBarHostState.showSnackbar(effect.message)
            }
        }
    }

    PaymentScreen(
        state = uiState,
        onPaymentClick = { viewModel.onAction(PaymentAction.Pay) },
        onNextClick = { paymentsNavigator.back() },
        snackbarHostState = snackBarHostState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PaymentScreen(
    state: PaymentScreenModel,
    onPaymentClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
    snackbarHostState: SnackbarHostState = SnackbarHostState()
) {
    Scaffold(
        topBar = {
            HFSnackBarHost(
                hostState = snackbarHostState,
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { paddingValues ->
        if (state.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LoadingCircle()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .padding(paddingValues)
                    .consumeWindowInsets(paddingValues)
                    .imePadding()
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = CoreStringRes.get_sub_for_more),
                    style = MaterialTheme.typography.displaySmall.copy(textAlign = TextAlign.Center)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = CoreStringRes.next_sub_paid),
                    style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(100.dp))
                HFButton(text = stringResource(id = CoreStringRes.start_sub)) {
                    onPaymentClick()
                }
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = onNextClick,
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = CoreStringRes.next_without_sub),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PaymentScreenPreviewLight() {
    HitFactorTheme {
        PaymentScreen(
            PaymentScreenModel(isLoading = true)
        )
    }
}

@Preview
@Composable
fun PaymentScreenPreviewDark() {
    HitFactorTheme(
        darkTheme = true
    ) {
        PaymentScreen(
            PaymentScreenModel(isLoading = false)
        )
    }
}