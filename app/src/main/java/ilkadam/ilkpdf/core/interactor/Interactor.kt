package ilkadam.ilkpdf.core.interactor

data class Interactor(
    val addDocument: AddDocument,
    val getDocuments: GetDocuments,
    val removeDocument: RemoveDocument
)