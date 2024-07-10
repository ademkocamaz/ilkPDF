package ilkadam.ilkpdf.core.data

import ilkadam.ilkpdf.core.domain.Document
import ilkadam.ilkpdf.framework.database.DocumentEntity

interface DocumentDataSource {
    suspend fun add(document: Document)
    suspend fun readAll(): List<Document>
    suspend fun remove(document: Document)
    suspend fun get(document: Document): DocumentEntity
}