package ilkadam.ilkpdf.core.data

import ilkadam.ilkpdf.core.domain.Document

interface DocumentDataSource {
    suspend fun add(document: Document)
    suspend fun readAll(): List<Document>
    suspend fun remove(document: Document)
}