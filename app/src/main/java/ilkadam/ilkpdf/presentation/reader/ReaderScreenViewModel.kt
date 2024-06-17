package ilkadam.ilkpdf.presentation.reader

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPdfReaderState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReaderScreenViewModel @Inject constructor() : ViewModel() {
    val uri = MutableLiveData<Uri>()
    /*val isLoading = mutableStateOf(false)
    lateinit var pdfState: VerticalPdfReaderState*/

    /*fun openDocument(uri: Uri) {
        isLoading.value = true
        viewModelScope.launch {
            pdfState =
                VerticalPdfReaderState(resource = ResourceType.Local(uri), isZoomEnable = true)
            while (!pdfState.isLoaded) {
                delay(100)
            }
            isLoading.value = false
        }
    }*/
}