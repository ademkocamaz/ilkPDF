package ilkadam.ilkpdf.framework

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ilkadam.ilkpdf.data.BookmarkRepository
import ilkadam.ilkpdf.data.DocumentRepository
import ilkadam.ilkpdf.interactor.AddBookmark
import ilkadam.ilkpdf.interactor.AddDocument
import ilkadam.ilkpdf.interactor.GetBookmarks
import ilkadam.ilkpdf.interactor.GetDocuments
import ilkadam.ilkpdf.interactor.GetOpenDocument
import ilkadam.ilkpdf.interactor.RemoveBookmark
import ilkadam.ilkpdf.interactor.RemoveDocument
import ilkadam.ilkpdf.interactor.SetOpenDocument

@HiltAndroidApp
class App : Application()