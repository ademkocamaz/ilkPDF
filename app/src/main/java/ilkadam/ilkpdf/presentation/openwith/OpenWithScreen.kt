package ilkadam.ilkpdf.presentation.openwith

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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