package ilkadam.ilkpdf.presentation.library

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ilkadam.ilkpdf.core.domain.Document
import ilkadam.ilkpdf.core.interactor.Interactor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val interactor: Interactor
) : ViewModel() {

    init {
        loadDocuments()
    }

    //val documents: MutableLiveData<List<Document>> = MutableLiveData()
    val documents = mutableStateOf<List<Document>>(listOf())

    private fun loadDocuments() {
        viewModelScope.launch {
            val docs = interactor.getDocuments()
            if (docs.isNotEmpty()) {
                documents.value = docs
            } else {
                documents.value = listOf()
            }
        }
    }

    fun addDocument(uri: Uri) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                interactor.addDocument(Document(uri.toString(), "", 0, ""))
            }
            loadDocuments()
        }
    }

    fun removeDocument(document: Document) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                interactor.removeDocument(document)
            }
            loadDocuments()
        }
    }
}