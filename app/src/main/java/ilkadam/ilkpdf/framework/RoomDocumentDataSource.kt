package ilkadam.ilkpdf.framework

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import dagger.hilt.android.qualifiers.ApplicationContext
import ilkadam.ilkpdf.data.DocumentDataSource
import ilkadam.ilkpdf.domain.Document
import ilkadam.ilkpdf.framework.database.DocumentEntity
import ilkadam.ilkpdf.framework.database.AppDatabase
import ilkadam.ilkpdf.framework.database.DocumentDao
import javax.inject.Inject

class RoomDocumentDataSource @Inject constructor(
    val documentDao: DocumentDao,
    @ApplicationContext val appContext: Context
) : DocumentDataSource {

//    private val documentDao = AppDatabase.getInstance(context = context).documentDao()

    override suspend fun add(document: Document) {
        val details = FileUtil.getDocumentDetails(appContext, document.url)

        documentDao.addDocument(
            DocumentEntity(
                document.url,
                details.name,
                details.size,
                details.thumbnail
            )
        )
    }

    override suspend fun readAll(): List<Document> = documentDao.getDocuments().map {
        Document(it.uri, it.title, it.size, it.thumbnailUri)
    }

    override suspend fun remove(document: Document) = documentDao.removeDocument(
        DocumentEntity(
            document.url,
            document.name,
            document.size,
            document.thumbnail
        )
    )
}