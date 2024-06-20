package ilkadam.ilkpdf.presentation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState

@Composable
fun TestScreen() {

    val uri = remember { mutableStateOf<Uri?>(null) }
    val pickPDFLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()) {

            it?.let {
                uri.value = it
            }

        }
    LaunchedEffect(key1 = Unit) {
        pickPDFLauncher.launch(arrayOf("application/pdf"))
    }
    uri.value?.let {
        val pdfState = rememberVerticalPdfReaderState(
            resource = ResourceType.Local(it),
            isZoomEnable = true
        )
        VerticalPDFReader(
            state = pdfState,
            modifier = Modifier
                .fillMaxSize()
        )
    }

}