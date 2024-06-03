package ilkadam.ilkpdf.presentation.library

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ilkadam.ilkpdf.domain.Document
import ilkadam.ilkpdf.framework.Interactor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    val interactors: Interactor
) : ViewModel() {
    val documents: MutableLiveData<List<Document>> = MutableLiveData()

    fun loadDocuments() {
        viewModelScope.launch {
            documents.postValue(interactors.getDocuments())
        }
    }

    fun addDocument(uri: Uri) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                interactors.addDocument(Document(uri.toString(), "", 0, ""))
            }
            loadDocuments()
        }
    }

    fun setOpenDocument(document: Document) {
        viewModelScope.launch {
            interactors.setOpenDocument(document)
        }
    }
}