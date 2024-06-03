package ilkadam.ilkpdf.interactor

import ilkadam.ilkpdf.data.DocumentRepository
import ilkadam.ilkpdf.domain.Document

class SetOpenDocument(private val documentRepository: DocumentRepository) {
    operator fun invoke(document: Document) = documentRepository.setOpenDocument(document)
}