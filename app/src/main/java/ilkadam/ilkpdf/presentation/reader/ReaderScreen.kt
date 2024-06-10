package ilkadam.ilkpdf.presentation.reader

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.provider.DocumentsProvider
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.PermissionState
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.VerticalPdfReaderState
import com.rizzi.bouquet.rememberVerticalPdfReaderState
import ilkadam.ilkpdf.domain.Document
import ilkadam.ilkpdf.framework.PdfRender
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun ReaderScreen(
    navController: NavController,
    readerScreenViewModel: ReaderScreenViewModel
) {
    val documentUri = readerScreenViewModel.uri.observeAsState()
    documentUri.value?.let { uri ->
        val context = LocalContext.current
        //context.contentResolver.takePersistableUriPermission(uri,Intent.FLAG_GRANT_READ_URI_PERMISSION)

        //context.grantUriPermission(context.packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val pdfState = rememberVerticalPdfReaderState(
            resource = ResourceType.Local(uri),
            isZoomEnable = true,
        )

        LaunchedEffect(key1 = pdfState.error) {
            pdfState.error?.let {
                Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }

        VerticalPDFReader(
            state = pdfState,
            modifier = Modifier
                .fillMaxSize()
        )
    }

    /*Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val context = LocalContext.current
        val fileDescriptor =
            context.contentResolver.openFileDescriptor(documentUri, "r")
        val pdfRender = PdfRender(
            fileDescriptor = fileDescriptor!!
        )
        DisposableEffect(key1 = Unit) {
            onDispose {
                pdfRender.close()
            }
        }
        LazyColumn {
            items(count = pdfRender.pageCount) { index ->
                BoxWithConstraints(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val page = pdfRender.pageLists[index]
                    DisposableEffect(key1 = Unit) {
                        page.load()
                        onDispose {
                            page.recycle()
                        }
                    }
                    page.pageContent.collectAsState().value?.asImageBitmap()?.let {
                        Image(
                            bitmap = it,
                            contentDescription = "Pdf page number: $index",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )
                    } ?: Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(
                                page
                                    .heightByWidth(constraints.maxWidth)
                                    .dp
                            )
                    )
                }
            }
        }
    }*/
}