package ilkadam.ilkpdf.data

import ilkadam.ilkpdf.domain.Document

interface DocumentDataSource {
    suspend fun add(document: Document)
    suspend fun readAll(): List<Document>
    suspend fun remove(document: Document)
}