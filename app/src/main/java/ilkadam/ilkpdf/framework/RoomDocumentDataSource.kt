package ilkadam.ilkpdf.framework

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ilkadam.ilkpdf.core.data.DocumentDataSource
import ilkadam.ilkpdf.core.domain.Document
import ilkadam.ilkpdf.framework.database.DocumentDao
import ilkadam.ilkpdf.framework.database.DocumentEntity
import javax.inject.Inject

class RoomDocumentDataSource @Inject constructor(
    private val documentDao: DocumentDao,
    @ApplicationContext val appContext: Context
) : DocumentDataSource {

//    private val documentDao = AppDatabase.getInstance(context = context).documentDao()

    override suspend fun add(document: Document) {
        val details = FileUtil.getDocumentDetails(appContext, document.url)

        documentDao.addDocument(
            DocumentEntity(
                uri = details.uri.toString(),
                title = details.name,
                size = details.size,
                thumbnailUri = details.thumbnail
            )
        )
    }

    override suspend fun readAll(): List<Document> = documentDao.getDocuments().map {
        Document(it.uri, it.title, it.size, it.thumbnailUri)
    }

    override suspend fun remove(document: Document) {
        documentDao.removeDocument(get(document))
    } /*= documentDao.removeDocument(
        DocumentEntity(
            uri = document.url,
            title = document.name,
            size = document.size,
            thumbnailUri = document.thumbnail
        )
    )*/

    override suspend fun get(document: Document): DocumentEntity =
        documentDao.getDocument(document.url)
}