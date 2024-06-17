package ilkadam.ilkpdf.data

import ilkadam.ilkpdf.domain.Document
import javax.inject.Inject

class DocumentRepository @Inject constructor(
    private val documentDataSource: DocumentDataSource,
) {
    suspend fun addDocument(document: Document) = documentDataSource.add(document)

    suspend fun getDocuments() = documentDataSource.readAll()

    suspend fun removeDocument(document: Document) = documentDataSource.remove(document)
}