package ilkadam.ilkpdf.core.interactor

import ilkadam.ilkpdf.core.data.DocumentRepository
import ilkadam.ilkpdf.core.domain.Document

class AddDocument(private val documentRepository: DocumentRepository) {
    suspend operator fun invoke(document: Document) = documentRepository.addDocument(document)
}