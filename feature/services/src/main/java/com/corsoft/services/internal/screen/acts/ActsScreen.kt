package com.corsoft.services.internal.screen.acts

import androidx.compose.foundation.layout.Arrangement
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
import com.corsoft.services.internal.component.card.DocumentCard
import com.corsoft.services.internal.component.enum.DocumentsEnum
import com.corsoft.services.internal.screen.pdf_view.navigation.PdfViewNavArgs
import com.corsoft.ui.components.button.HFIconButton
import com.corsoft.ui.components.topbar.ToolBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.services.destinations.PdfViewScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination<ServicesNavGraph>
internal fun ActsScreen(
    navigator: DestinationsNavigator
) {
    Scaffold(
        topBar = {
            ToolBar(
                title = {
                    Text(
                        text = stringResource(id = CoreStringRes.documents)
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
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DocumentsEnum.entries.forEach { docItem ->
                val name = docItem.getName()
                val resId = docItem.getResId()
                DocumentCard(
                    name = name,
                    description = docItem.getDescription()
                ) {
                    navigator.navigate(
                        PdfViewScreenDestination(
                            navArgs = PdfViewNavArgs(
                                documentName = name,
                                documentResId = resId
                            )
                        )
                    )
                }
            }
        }
    }
}