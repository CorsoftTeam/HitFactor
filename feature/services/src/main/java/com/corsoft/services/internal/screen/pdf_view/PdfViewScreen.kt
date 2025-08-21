package com.corsoft.services.internal.screen.pdf_view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.api.ServicesNavGraph
import com.corsoft.services.internal.screen.pdf_view.navigation.PdfViewNavArgs
import com.corsoft.ui.components.button.HFIconButton
import com.corsoft.ui.components.card.InfoCard
import com.corsoft.ui.components.topbar.ToolBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.services.destinations.PdfViewScreenDestination
import com.ramcosta.composedestinations.generated.services.navArgs
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState

@Composable
@Destination<ServicesNavGraph>(navArgs = PdfViewNavArgs::class)
internal fun PdfViewScreen(
    navigator: DestinationsNavigator
) {
    val navArgs = navigator.getBackStackEntry(PdfViewScreenDestination)?.navArgs<PdfViewNavArgs>()
    Scaffold(
        topBar = {
            ToolBar(
                title = {
                    Text(
                        text = navArgs?.documentName ?: ""
                    )
                },
                navigationIcon = {
                    HFIconButton(
                        icon = CoreDrawableRes.ic_back,
                        onClick = { navigator.popBackStack() }
                    )
                }
            )
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            InfoCard(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                text = stringResource(id = CoreStringRes.pdf_reader_message)
            )
            VerticalPDFReader(
                state = rememberVerticalPdfReaderState(
                    resource = ResourceType.Asset(navArgs?.documentResId ?: 0),
                    isZoomEnable = true
                ),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}