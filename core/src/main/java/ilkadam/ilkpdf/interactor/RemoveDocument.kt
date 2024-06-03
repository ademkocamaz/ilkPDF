package ilkadam.ilkpdf.interactor

import ilkadam.ilkpdf.data.DocumentRepository
import ilkadam.ilkpdf.domain.Document

class RemoveDocument(private val documentRepository: DocumentRepository) {
    suspend operator fun invoke(document: Document) = documentRepository.removeDocument(document)
}