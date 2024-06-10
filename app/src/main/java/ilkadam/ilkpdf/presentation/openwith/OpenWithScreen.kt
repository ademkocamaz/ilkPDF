package ilkadam.ilkpdf.presentation.openwith

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState
import ilkadam.ilkpdf.presentation.library.LibraryViewModel

@Composable
fun OpenWithScreen(uri: Uri) {
    val libraryViewModel: LibraryViewModel = hiltViewModel()
    libraryViewModel.addDocument(uri)

    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Local(uri),
        isZoomEnable = true,
    )

    /*LaunchedEffect(key1 = pdfState.error) {
        pdfState.error?.let {
            Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }*/

    VerticalPDFReader(
        state = pdfState,
        modifier = Modifier
            .fillMaxSize()
    )
}