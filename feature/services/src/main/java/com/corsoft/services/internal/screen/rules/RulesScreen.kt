package com.corsoft.services.internal.screen.rules

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.api.ServicesNavGraph
import com.corsoft.ui.components.button.HFIconButton
import com.corsoft.ui.components.card.InfoCard
import com.corsoft.ui.components.topbar.ToolBar
import com.corsoft.ui.theme.HitFactorTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState

@Composable
@Destination<ServicesNavGraph>
internal fun RulesScreen() {
    Scaffold(
        topBar = {
            ToolBar(
                title = {
                    Text(
                        text = stringResource(id = CoreStringRes.rules)
                    )
                }
            )
        }
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
                    resource = ResourceType.Remote(
                        url = "https://ipsc.ru/wp-content/uploads/2018/09/%D0%9F%D1%80%D0%B0%D0%B2%D0%B8%D0%BB%D0%B0_%D0%B2%D0%B8%D0%B4%D0%B0_%D1%81%D0%BF%D0%BE%D1%80%D1%82%D0%B0_%D0%BF%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B0%D1%8F_%D1%81%D1%82%D1%80%D0%B5%D0%BB%D1%8C%D0%B1%D0%B0__%D0%9A%D0%9E%D0%9C%D0%91%D0%98%D0%9D%D0%98%D0%A0%D0%9E%D0%92%D0%90%D0%9D%D0%9D%D0%AB%D0%95_2015.pdf"
                    ),
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
private fun RulesScreenPreview() {
    HitFactorTheme {
        Scaffold {
            RulesScreen()
        }
    }
}