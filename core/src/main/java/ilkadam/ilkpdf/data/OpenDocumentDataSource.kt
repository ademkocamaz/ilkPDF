package ilkadam.ilkpdf.data

import ilkadam.ilkpdf.domain.Document

interface OpenDocumentDataSource {
    fun setOpenDocument(document: Document)
    fun getOpenDocument(): Document
}