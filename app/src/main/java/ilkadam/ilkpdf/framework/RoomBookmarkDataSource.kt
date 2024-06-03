package ilkadam.ilkpdf.framework

import android.content.Context
import ilkadam.ilkpdf.data.BookmarkDataSource
import ilkadam.ilkpdf.domain.Bookmark
import ilkadam.ilkpdf.domain.Document
import ilkadam.ilkpdf.framework.database.BookmarkEntity
import ilkadam.ilkpdf.framework.database.AppDatabase
import ilkadam.ilkpdf.framework.database.BookmarkDao
import javax.inject.Inject

class RoomBookmarkDataSource @Inject constructor(
    val bookmarkDao: BookmarkDao
) : BookmarkDataSource {

//    private val bookmarkDao = AppDatabase.getInstance(context = context).bookmarkDao()

    override suspend fun add(document: Document, bookmark: Bookmark) {
        bookmarkDao.addBookmark(
            BookmarkEntity(
                documentUri = document.url,
                page = bookmark.page
            )
        )
    }

    override suspend fun read(document: Document): List<Bookmark> =
        bookmarkDao.getBookmarks(document.url).map {
            Bookmark(it.id, it.page)
        }


    override suspend fun remove(document: Document, bookmark: Bookmark) =
        bookmarkDao.removeBookmark(
            BookmarkEntity(
                id = bookmark.id,
                documentUri = document.url,
                page = bookmark.page
            )
        )
}