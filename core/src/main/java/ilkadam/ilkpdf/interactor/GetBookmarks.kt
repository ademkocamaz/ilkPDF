package ilkadam.ilkpdf.interactor

import ilkadam.ilkpdf.data.BookmarkRepository
import ilkadam.ilkpdf.data.DocumentRepository
import ilkadam.ilkpdf.domain.Document

class GetBookmarks(private val bookmarkRepository: BookmarkRepository) {
    suspend operator fun invoke(document: Document) = bookmarkRepository.getBookmarks(document)
}