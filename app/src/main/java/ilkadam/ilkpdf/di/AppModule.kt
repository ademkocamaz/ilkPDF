package ilkadam.ilkpdf.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ilkadam.ilkpdf.data.DocumentDataSource
import ilkadam.ilkpdf.data.DocumentRepository
import ilkadam.ilkpdf.framework.RoomDocumentDataSource
import ilkadam.ilkpdf.framework.database.AppDatabase
import ilkadam.ilkpdf.framework.database.DocumentDao
import ilkadam.ilkpdf.interactor.AddDocument
import ilkadam.ilkpdf.interactor.GetDocuments
import ilkadam.ilkpdf.interactor.Interactor
import ilkadam.ilkpdf.interactor.RemoveDocument
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "ilkpdf"
        )
            //.fallbackToDestructiveMigration()
            .build()


    @Provides
    fun provideDocumentDao(appDatabase: AppDatabase): DocumentDao = appDatabase.documentDao()

    @Provides
    fun provideDocumentDataSource(
        documentDao: DocumentDao,
        @ApplicationContext appContext: Context
    ): DocumentDataSource = RoomDocumentDataSource(documentDao, appContext)

    @Provides
    fun provideDocumentRepository(
        documentDataSource: DocumentDataSource,
    ): DocumentRepository = DocumentRepository(
        documentDataSource
    )

    @Provides
    fun provideInteractor(
        documentRepository: DocumentRepository,
    ): Interactor =
        Interactor(
            addDocument = AddDocument(documentRepository),
            getDocuments = GetDocuments(documentRepository),
            removeDocument = RemoveDocument(documentRepository)
        )


}