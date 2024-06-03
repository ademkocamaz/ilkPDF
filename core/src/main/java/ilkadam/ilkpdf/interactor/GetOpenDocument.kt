package ilkadam.ilkpdf.interactor

import ilkadam.ilkpdf.data.DocumentRepository

class GetOpenDocument(private val documentRepository: DocumentRepository) {
    operator fun invoke() = documentRepository.getOpenDocument()
}