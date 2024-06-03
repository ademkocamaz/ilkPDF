package ilkadam.ilkpdf.data

import ilkadam.ilkpdf.domain.Bookmark
import ilkadam.ilkpdf.domain.Document

interface BookmarkDataSource {
    suspend fun add(document: Document, bookmark: Bookmark)
    suspend fun read(document: Document): List<Bookmark>
    suspend fun remove(document: Document, bookmark: Bookmark)
}