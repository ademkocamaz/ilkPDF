package ilkadam.ilkpdf.interactor

import ilkadam.ilkpdf.data.BookmarkRepository
import ilkadam.ilkpdf.domain.Bookmark
import ilkadam.ilkpdf.domain.Document

class RemoveBookmark(private val bookmarkRepository: BookmarkRepository) {
    suspend operator fun invoke(document: Document, bookmark: Bookmark) =
        bookmarkRepository.removeBookmark(document, bookmark)
}