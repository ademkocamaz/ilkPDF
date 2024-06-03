package ilkadam.ilkpdf.interactor

import ilkadam.ilkpdf.data.DocumentRepository
import ilkadam.ilkpdf.domain.Document

class AddDocument(private val documentRepository: DocumentRepository) {
    suspend operator fun invoke(document: Document) = documentRepository.addDocument(document)
}