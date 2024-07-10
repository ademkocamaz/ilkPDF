package ilkadam.ilkpdf.presentation.reader

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState

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

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = pdfState.currentPage.toString() + "/" + pdfState.pdfPageCount.toString(),
                        color = Color.DarkGray
                    )
                }
            }
        ) { innerPadding ->
            VerticalPDFReader(
                state = pdfState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)

            )
        }


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