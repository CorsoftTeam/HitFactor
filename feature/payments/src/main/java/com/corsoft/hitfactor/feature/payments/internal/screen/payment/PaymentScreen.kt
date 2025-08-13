package com.corsoft.hitfactor.feature.payments.internal.screen.payment

import LoadingCircle
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.corsoft.hitfactor.feature.payments.api.PaymentsNavGraph
import com.corsoft.hitfactor.feature.payments.api.PaymentsNavigator
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.corsoft.ui.components.button.HFButton
import com.corsoft.ui.components.button.HFTextButton
import com.corsoft.ui.components.input.TextInputDialog
import com.corsoft.ui.components.snackbar.HFSnackBarHost
import com.corsoft.ui.theme.AppColors.GoodColor
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
    val isCodeInputShow = remember { mutableStateOf(false) }

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

    if (isCodeInputShow.value) {
        TextInputDialog(
            title = stringResource(CoreStringRes.activate_promocode),
            placeholder = stringResource(CoreStringRes.promocode),
            onDismiss = { isCodeInputShow.value = false },
            onConfirm = { viewModel.onAction(PaymentAction.CheckCode(it)) }
        )
    }

    PaymentScreen(
        state = uiState,
        onPaymentClick = { viewModel.onAction(PaymentAction.Pay) },
        onNextClick = { paymentsNavigator.back() },
        onActivatePromoClick = { isCodeInputShow.value = true },
        snackbarHostState = snackBarHostState
    )
    HFSnackBarHost(
        hostState = snackBarHostState,
        modifier = Modifier.statusBarsPadding()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PaymentScreen(
    state: PaymentScreenModel,
    onPaymentClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
    onActivatePromoClick: () -> Unit = {},
    snackbarHostState: SnackbarHostState = SnackbarHostState()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if(!state.isLoading){
            Image(
                painter = painterResource(id = CoreDrawableRes.ic_elena),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            contentColor = contentColorFor(backgroundColor = MaterialTheme.colorScheme.background),
            bottomBar = {
                if (!state.isLoading) {
                    Column (
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        HFButton(
                            text = stringResource(CoreStringRes.start_sub),
                            onClick = onPaymentClick,
                            customColor = GoodColor
                        )
                        HFTextButton(
                            text = stringResource(CoreStringRes.activate_promocode),
                            onClick = onActivatePromoClick
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            },
        ) { paddingValues ->
            if (state.isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
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
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Попробуйте полный функционал за",
                        style = MaterialTheme.typography.headlineMedium.copy(textAlign = TextAlign.Center)
                    )
                    Text(
                        text = "299 ₽",
                        style = MaterialTheme.typography.displaySmall.copy(
                            textAlign = TextAlign.Center,
                            textDecoration = TextDecoration.LineThrough
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "1 ₽",
                        style = MaterialTheme.typography.displaySmall
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "✓  Таймер для IPSC/IDPA",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "✓  Рассчет результата",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "✓  Дневник тренировок",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "✓  Уход за оружием",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "✓  Учет боеприпасов",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "✓  Хранилище документов",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "✓  Стрельбища вашего города",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "✓  Постоянные обновления",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "✓  Техническая поддержка",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = stringResource(id = CoreStringRes.next_sub_paid),
                        style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Preview(apiLevel = 34)
@Composable
fun PaymentScreenPreviewLight() {
    HitFactorTheme {
        PaymentScreen(
            PaymentScreenModel(isLoading = true)
        )
    }
}

@Preview(apiLevel = 34)
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