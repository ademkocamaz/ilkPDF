package ilkadam.ilkpdf.interactor

import ilkadam.ilkpdf.data.DocumentRepository

class GetDocuments(private val documentRepository: DocumentRepository) {
    suspend operator fun invoke()=documentRepository.getDocuments()
}