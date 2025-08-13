package com.corsoft.services.internal.screen.documents

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.api.ServicesNavGraph
import com.corsoft.ui.components.button.HFButton
import com.corsoft.ui.components.button.HFIconButton
import com.corsoft.ui.components.card.InfoCard
import com.corsoft.ui.components.snackbar.HFSnackBarHost
import com.corsoft.ui.components.topbar.ToolBar
import com.corsoft.ui.theme.HitFactorTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination<ServicesNavGraph>
internal fun DocumentsScreen(
    navigator: DestinationsNavigator,
    viewModel: DocumentsViewModel = koinViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DocumentsScreen(
        state = uiState,
        onBackClick = { navigator.popBackStack() }
    )
    HFSnackBarHost(
        hostState = snackBarHostState,
        modifier = Modifier.statusBarsPadding()
    )
}

@Composable
private fun DocumentsScreen(
    modifier: Modifier = Modifier,
    state: DocumentsScreenState,
    onBackClick: () -> Unit = {}
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
                        onClick = onBackClick
                    )
                }
            )
        },
        bottomBar = {
            HFButton(
                modifier = Modifier.padding(16.dp),
                text = stringResource(id = CoreStringRes.add),
                onClick = { }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            InfoCard(
                text = stringResource(id = CoreStringRes.local_documents_message)
            )
            Spacer(modifier = Modifier.height(24.dp))

            documentTab(
                name = "РОХа", photos = listOf(
                    Uri.parse("https://opis-cdn.tinkoffjournal.ru/mercury/ognestrel-photo-08.42in4brimb20..jpg"),
                    Uri.parse("https://загрузки.торги-россии.рф/pictures/\$2y\$10\$NrGbQimgf3.1QslM9Nb.s2sBKHi0XFVn9LNRPIKegR1KyCuYB0C.png")
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            documentTab(
                name = "Справка 002-о/y", photos = listOf(
                    Uri.parse("https://proguns.ru/wp-content/uploads/2020/09/spravka-003-oy.jpg"),
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            documentTab(
                name = "Паспорт РФ", photos = listOf(
                    Uri.parse("https://opis-cdn.tinkoffjournal.ru/mercury/easy-breezy-passport-before.hasxgdjplsgq.png"),
                    Uri.parse("https://docs.dbrain.io/~gitbook/image?url=https%3A%2F%2F1997706258-files.gitbook.io%2F%7E%2Ffiles%2Fv0%2Fb%2Fgitbook-x-prod.appspot.com%2Fo%2Fspaces%252F-MGd7t8Xm9EFLYborr9A%252Fuploads%252F0VTD0X7VPAGcdvE6lEIL%252Fpassport_registration.png%3Falt%3Dmedia%26token%3D67c4f423-2c4a-46f9-80bf-bfa38345c1b1&width=768&dpr=4&quality=100&sign=5c2bb0f5&sv=2")
                )
            )

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun documentTab(
    name: String,
    photos: List<Uri>,
    modifier: Modifier = Modifier,
) {
    Text(
        text = name,
        style = MaterialTheme.typography.titleMedium
    )
    Spacer(modifier = Modifier.height(12.dp))
    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth(),
        columns = GridCells.Fixed(3)
    ) {
        items(photos) {
            Card(
                modifier = modifier.size(100.dp),
                onClick = { },
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.primary)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = it,
                        placeholder = painterResource(id = CoreDrawableRes.default_photo_placeholder),
                        error = painterResource(id = CoreDrawableRes.default_photo_placeholder)
                    ),
                    contentScale = ContentScale.Crop,
                    contentDescription = ""
                )
            }
        }

        item {
            Card(
                modifier = modifier.size(100.dp),
                onClick = { },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(24.dp),
                        painter = painterResource(id = CoreDrawableRes.ic_plus),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = ""
                    )
                }
            }
        }
    }
}


@Preview(apiLevel = 34)
@Composable
private fun DocumentsPreviewDark() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            DocumentsScreen(
                state = DocumentsScreenState(),
            )
        }
    }
}