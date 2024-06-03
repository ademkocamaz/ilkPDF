package ilkadam.ilkpdf.presentation.reader

import android.app.Application
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ilkadam.ilkpdf.domain.Bookmark
import ilkadam.ilkpdf.domain.Document
import ilkadam.ilkpdf.framework.Interactor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ReaderViewModel @Inject constructor(
    val interactors: Interactor,
    val application: Application
) : ViewModel() {

    companion object {
        private const val DOCUMENT_ARG = "document"
        fun createArguments(document: Document) = bundleOf(
            DOCUMENT_ARG to document
        )
    }

    val document = MutableLiveData<Document>()
    val bookmarks = MediatorLiveData<List<Bookmark>>().apply {
        addSource(document) { document ->
            viewModelScope.launch {
                postValue(interactors.getBookmarks(document))
            }
        }
    }

    private fun getFileDescriptor(uri: Uri) =
        application.contentResolver.openFileDescriptor(uri, "r")!!

    private fun isCurrentPageBookmarked() =
        bookmarks.value?.any { it.page == currentPage.value?.index } == true

    private suspend fun isInLibrary(document: Document) = interactors.getDocuments().any {
        it.url == document.url
    }

    val renderer = MediatorLiveData<PdfRenderer>().apply {
        addSource(document) {
            try {
                val pdfRenderer = PdfRenderer(getFileDescriptor(Uri.parse(it.url)))
                value = pdfRenderer
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
    }

    val currentPage = MediatorLiveData<PdfRenderer.Page>()
    val hasPreviousPage: LiveData<Boolean> = currentPage.map {
        it.index > 0
    }
    val hasNextPage: LiveData<Boolean> = currentPage.map {
        renderer.value.let { renderer -> it.index < renderer!!.pageCount - 1 }
    }

    val isBookmarked = MediatorLiveData<Boolean>().apply {
        addSource(document) { value = isCurrentPageBookmarked() }
        addSource(currentPage) { value = isCurrentPageBookmarked() }
        addSource(bookmarks) { value = isCurrentPageBookmarked() }
    }

    val isInLibrary = MediatorLiveData<Boolean>().apply {
        addSource(document) {
            viewModelScope.launch {
                postValue(isInLibrary(it))
            }
        }
    }

    fun loadArguments(arguments: Bundle?) {
        if (arguments == null) {
            return
        }

        currentPage.apply {
            addSource(renderer) { renderer ->
                viewModelScope.launch {
                    val document = document.value
                    if (document != null) {
                        val bookmarks = interactors.getBookmarks(document).lastOrNull()?.page ?: 0
                        postValue(renderer.openPage(bookmarks))
                    }
                }
            }

            val documentFromArguments = arguments.get(DOCUMENT_ARG) as Document? ?: Document.EMPTY

            val lastOpenDocument = interactors.getOpenDocument()

            document.value = when {
                documentFromArguments != Document.EMPTY -> documentFromArguments
                documentFromArguments == Document.EMPTY && lastOpenDocument != Document.EMPTY -> lastOpenDocument
                else -> Document.EMPTY
            }

            document.value?.let {
                interactors.setOpenDocument(it)
            }
        }
    }

    fun openDocument(uri: Uri) {
        document.value = Document(uri.toString(), "", 0, "")
        document.value?.let { interactors.setOpenDocument(it) }
    }

    private fun openPage(page: Int) = renderer.value?.let {
        currentPage.value = it.openPage(page)
    }

    fun openBookmark(bookmark: Bookmark) {
        openPage(bookmark.page)
    }

    fun nextPage() = currentPage.value?.let { openPage(it.index.plus(1)) }
    fun previousPage() = currentPage.value?.let { openPage(it.index.minus(1)) }

    fun reopenPage() = openPage(currentPage.value?.index ?: 0)

    fun toggleBookmark() {
        val currentPage = currentPage.value?.index ?: return
        val document = document.value ?: return
        val bookmark = bookmarks.value?.firstOrNull { it.page == currentPage }

        viewModelScope.launch {
            if (bookmark == null) {
                interactors.addBookmark(document, Bookmark(page = currentPage))
            } else {
                interactors.removeBookmark(document, bookmark)
            }

            bookmarks.postValue(interactors.getBookmarks(document))
        }
    }

    fun toggleInLibrary() {
        val document = document.value ?: return
        viewModelScope.launch {
            if (isInLibrary.value == true) {
                interactors.removeDocument(document)
            } else {
                interactors.addDocument(document)
            }
            isInLibrary.postValue(isInLibrary(document))
        }
    }

}