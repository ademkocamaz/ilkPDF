package ilkadam.ilkpdf.framework

import ilkadam.ilkpdf.interactor.AddBookmark
import ilkadam.ilkpdf.interactor.AddDocument
import ilkadam.ilkpdf.interactor.GetBookmarks
import ilkadam.ilkpdf.interactor.GetDocuments
import ilkadam.ilkpdf.interactor.GetOpenDocument
import ilkadam.ilkpdf.interactor.RemoveBookmark
import ilkadam.ilkpdf.interactor.RemoveDocument
import ilkadam.ilkpdf.interactor.SetOpenDocument

data class Interactor(
    val addBookmark: AddBookmark,
    val getBookmarks: GetBookmarks,
    val removeBookmark: RemoveBookmark,
    val addDocument: AddDocument,
    val getDocuments: GetDocuments,
    val removeDocument: RemoveDocument,
    val getOpenDocument: GetOpenDocument,
    val setOpenDocument: SetOpenDocument
)