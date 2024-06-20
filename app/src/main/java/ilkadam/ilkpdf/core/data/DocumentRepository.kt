package ilkadam.ilkpdf.core.data

import ilkadam.ilkpdf.core.domain.Document
import javax.inject.Inject

class DocumentRepository @Inject constructor(
    private val documentDataSource: DocumentDataSource,
) {
    suspend fun addDocument(document: Document) = documentDataSource.add(document)

    suspend fun getDocuments() = documentDataSource.readAll()

    suspend fun removeDocument(document: Document) = documentDataSource.remove(document)
}