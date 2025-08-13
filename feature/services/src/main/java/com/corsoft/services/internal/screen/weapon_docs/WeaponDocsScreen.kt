package com.corsoft.services.internal.screen.weapon_docs

import LoadingCircle
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.corsoft.resources.CoreDrawableRes
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.api.ServicesNavGraph
import com.corsoft.services.internal.component.image.FullScreenImage
import com.corsoft.services.internal.screen.weapon_docs.navigation.WeaponDocsNavArgs
import com.corsoft.ui.components.button.HFIconButton
import com.corsoft.ui.components.card.InfoCard
import com.corsoft.ui.components.snackbar.HFSnackBarHost
import com.corsoft.ui.components.topbar.ToolBar
import com.corsoft.ui.theme.HitFactorTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.io.FileOutputStream

@Composable
@Destination<ServicesNavGraph>(navArgs = WeaponDocsNavArgs::class)
internal fun WeaponDocsScreen(
    navigator: DestinationsNavigator,
    viewModel: WeaponDocsViewModel = koinViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var showFullScreenUri by remember { mutableStateOf<String?>(null) }

    val docFirstPageLauncher = rememberImagePickerLauncher(context) { uri ->
        viewModel.onAction(WeaponDocsAction.ChangeDocFirstPageUri(uri.toString()))
    }
    val docSecondPageLauncher = rememberImagePickerLauncher(context) { uri ->
        viewModel.onAction(WeaponDocsAction.ChangeDocSecondPageUri(uri.toString()))
    }
    val licenseFirstPageLauncher = rememberImagePickerLauncher(context) { uri ->
        viewModel.onAction(WeaponDocsAction.ChangeLicenseFirstPageUri(uri.toString()))
    }
    val licenseSecondPageLauncher = rememberImagePickerLauncher(context) { uri ->
        viewModel.onAction(WeaponDocsAction.ChangeLicenseSecondPageUri(uri.toString()))
    }


    WeaponDocsScreen(
        state = uiState,
        onBackClick = { navigator.popBackStack() },
        onDocFirstPageClick = {
            if (uiState.gunDocs.docFirstPageUri.isNotEmpty()) {
                showFullScreenUri = uiState.gunDocs.docFirstPageUri
            } else {
                docFirstPageLauncher.launch("image/*")
            }
        },
        onDocSecondPageClick = {
            if (uiState.gunDocs.docSecondPageUri.isNotEmpty()) {
                showFullScreenUri = uiState.gunDocs.docSecondPageUri
            } else {
                docSecondPageLauncher.launch("image/*")
            }
        },
        onLicenseFirstPageClick = {
            if (uiState.gunDocs.licenseFirstPageUri.isNotEmpty()) {
                showFullScreenUri = uiState.gunDocs.licenseFirstPageUri
            } else {
                licenseFirstPageLauncher.launch("image/*")
            }
        },
        onLicenseSecondPageClick = {
            if (uiState.gunDocs.licenseSecondPageUri.isNotEmpty()) {
                showFullScreenUri = uiState.gunDocs.licenseSecondPageUri
            } else {
                licenseSecondPageLauncher.launch("image/*")
            }
        },
        onDocFirstPageEdit = {
            docFirstPageLauncher.launch("image/*")
        },
        onDocSecondPageEdit = {
            docSecondPageLauncher.launch("image/*")
        },
        onLicenseFirstPageEdit = {
            licenseFirstPageLauncher.launch("image/*")
        },
        onLicenseSecondPageEdit = {
            licenseSecondPageLauncher.launch("image/*")
        }
    )
    HFSnackBarHost(
        hostState = snackBarHostState,
        modifier = Modifier.statusBarsPadding()
    )

    showFullScreenUri?.let {
        FullScreenImage(
            imageUri = Uri.parse(showFullScreenUri),
            onDismiss = { showFullScreenUri = null }
        )
    }
}

@Composable
private fun WeaponDocsScreen(
    modifier: Modifier = Modifier,
    state: WeaponDocsScreenState,
    onBackClick: () -> Unit = {},
    onDocFirstPageClick: () -> Unit = {},
    onDocSecondPageClick: () -> Unit = {},
    onLicenseFirstPageClick: () -> Unit = {},
    onLicenseSecondPageClick: () -> Unit = {},
    onDocFirstPageEdit: () -> Unit = {},
    onDocSecondPageEdit: () -> Unit = {},
    onLicenseFirstPageEdit: () -> Unit = {},
    onLicenseSecondPageEdit: () -> Unit = {}
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
                modifier = modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {

                InfoCard(
                    text = stringResource(id = CoreStringRes.local_documents_message)
                )
                Spacer(modifier = Modifier.height(24.dp))

                DocumentTab(
                    name = stringResource(id = CoreStringRes.roha_rspa),
                    photos = listOf(
                        Uri.parse(state.gunDocs.docFirstPageUri),
                        Uri.parse(state.gunDocs.docSecondPageUri)
                    ),
                    onClick = { if (it == 0) onDocFirstPageClick() else onDocSecondPageClick() },
                    onLongClick = { if (it == 0) onDocFirstPageEdit() else onDocSecondPageEdit() },
                )
                Spacer(modifier = Modifier.height(24.dp))
                DocumentTab(
                    name = stringResource(id = CoreStringRes.buying_license), photos = listOf(
                        Uri.parse(state.gunDocs.licenseFirstPageUri),
                        Uri.parse(state.gunDocs.licenseSecondPageUri)
                    ),
                    onClick = { if (it == 0) onLicenseFirstPageClick() else onLicenseSecondPageClick() },
                    onLongClick = { if (it == 0) onLicenseFirstPageEdit() else onLicenseSecondPageEdit() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DocumentTab(
    modifier: Modifier = Modifier,
    name: String,
    photos: List<Uri>,
    onClick: (Int) -> Unit = {},
    onLongClick: (Int) -> Unit = {},
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
        items(photos) { photo ->
            Card(
                modifier = modifier
                    .size(100.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = { onLongClick(photos.indexOf(photo)) },
                            onTap = { onClick(photos.indexOf(photo)) }
                        )
                    },
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.primary)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = photo,
                        placeholder = painterResource(id = CoreDrawableRes.default_photo_placeholder),
                        error = painterResource(id = CoreDrawableRes.default_photo_placeholder)
                    ),
                    contentScale = ContentScale.Crop,
                    contentDescription = ""
                )
            }
        }
    }
}

@Composable
fun rememberImagePickerLauncher(
    context: Context,
    onImagePicked: (Uri) -> Unit
): ManagedActivityResultLauncher<String, Uri?> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { contentUri ->
            val permanentUri = copyFileToInternalStorage(context, contentUri, "weapon_docs")
            onImagePicked(permanentUri)
        }
    }
}

fun copyFileToInternalStorage(context: Context, uri: Uri, subDir: String): Uri {
    val contentResolver = context.contentResolver
    val fileExtension = getFileExtension(contentResolver, uri)
    val fileName = "doc_${System.currentTimeMillis()}.$fileExtension"

    val directory = File(context.filesDir, subDir).apply { mkdirs() }
    val file = File(directory, fileName)

    contentResolver.openInputStream(uri)?.use { input ->
        FileOutputStream(file).use { output ->
            input.copyTo(output)
        }
    }

    return Uri.fromFile(file)
}

fun getFileExtension(contentResolver: ContentResolver, uri: Uri): String {
    val mimeType = contentResolver.getType(uri)
    return MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "jpg"
}


@Preview(apiLevel = 34)
@Composable
private fun DocumentsPreviewDark() {
    HitFactorTheme(
        darkTheme = true
    ) {
        Surface {
            WeaponDocsScreen(
                state = WeaponDocsScreenState(),
            )
        }
    }
}