package ilkadam.ilkpdf.core.interactor

import ilkadam.ilkpdf.core.data.DocumentRepository

class GetDocuments(private val documentRepository: DocumentRepository) {
    suspend operator fun invoke() = documentRepository.getDocuments()
}