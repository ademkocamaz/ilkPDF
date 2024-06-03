package ilkadam.ilkpdf.framework

import ilkadam.ilkpdf.data.OpenDocumentDataSource
import ilkadam.ilkpdf.domain.Document

class InMemoryOpenDocumentDataSource : OpenDocumentDataSource {

    private var openDocument: Document = Document.EMPTY

    override fun setOpenDocument(document: Document) {
        openDocument = document
    }

    override fun getOpenDocument() = openDocument
}