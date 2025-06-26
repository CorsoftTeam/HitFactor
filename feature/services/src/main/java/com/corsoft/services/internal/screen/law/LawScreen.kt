package com.corsoft.services.internal.screen.law

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corsoft.resources.CoreRawRes
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.api.ServicesNavGraph
import com.corsoft.ui.components.card.InfoCard
import com.corsoft.ui.components.topbar.ToolBar
import com.corsoft.ui.theme.HitFactorTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState

@Composable
@Destination<ServicesNavGraph>
internal fun LawScreen() {
    Scaffold(
        topBar = {
            ToolBar(
                title = {
                    Text(
                        text = stringResource(id = CoreStringRes.federal_law_150)
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
                    resource = ResourceType.Asset(CoreRawRes.doc_fz_150),
                    isZoomEnable = true
                ),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
private fun LawScreenPreview() {
    HitFactorTheme {
        Scaffold {
            LawScreen()
        }
    }
}