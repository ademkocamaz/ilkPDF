package ilkadam.ilkpdf.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ilkadam.ilkpdf.data.BookmarkDataSource
import ilkadam.ilkpdf.data.BookmarkRepository
import ilkadam.ilkpdf.data.DocumentDataSource
import ilkadam.ilkpdf.data.DocumentRepository
import ilkadam.ilkpdf.data.OpenDocumentDataSource
import ilkadam.ilkpdf.framework.InMemoryOpenDocumentDataSource
import ilkadam.ilkpdf.framework.RoomBookmarkDataSource
import ilkadam.ilkpdf.framework.RoomDocumentDataSource
import ilkadam.ilkpdf.framework.database.AppDatabase
import ilkadam.ilkpdf.framework.database.BookmarkDao
import ilkadam.ilkpdf.framework.database.DocumentDao
import ilkadam.ilkpdf.interactor.AddBookmark
import ilkadam.ilkpdf.interactor.AddDocument
import ilkadam.ilkpdf.interactor.GetBookmarks
import ilkadam.ilkpdf.interactor.GetDocuments
import ilkadam.ilkpdf.interactor.GetOpenDocument
import ilkadam.ilkpdf.framework.Interactor
import ilkadam.ilkpdf.interactor.RemoveBookmark
import ilkadam.ilkpdf.interactor.RemoveDocument
import ilkadam.ilkpdf.interactor.SetOpenDocument
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
    fun provideBookmarkDao(appDatabase: AppDatabase): BookmarkDao = appDatabase.bookmarkDao()

    @Provides
    fun provideDocumentDao(appDatabase: AppDatabase): DocumentDao = appDatabase.documentDao()

    @Provides
    fun provideBookmarkDataSource(bookmarkDao: BookmarkDao): BookmarkDataSource =
        RoomBookmarkDataSource(bookmarkDao)

    @Provides
    fun provideDocumentDataSource(
        documentDao: DocumentDao,
        @ApplicationContext appContext: Context
    ): DocumentDataSource = RoomDocumentDataSource(documentDao, appContext)

    @Provides
    fun provideOpenDocumentDataSource(): OpenDocumentDataSource = InMemoryOpenDocumentDataSource()

    @Provides
    fun provideBookmarkRepository(bookmarkDataSource: BookmarkDataSource): BookmarkRepository =
        BookmarkRepository(bookmarkDataSource)

    @Provides
    fun provideDocumentRepository(
        documentDataSource: DocumentDataSource,
        openDocumentDataSource: OpenDocumentDataSource
    ): DocumentRepository = DocumentRepository(
        documentDataSource,
        openDocumentDataSource
    )

    @Provides
    fun provideInteractor(
        documentRepository: DocumentRepository,
        bookmarkRepository: BookmarkRepository
    ): Interactor =
        Interactor(
            addBookmark = AddBookmark(bookmarkRepository),
            getBookmarks = GetBookmarks(bookmarkRepository),
            removeBookmark = RemoveBookmark(bookmarkRepository),
            addDocument = AddDocument(documentRepository),
            getDocuments = GetDocuments(documentRepository),
            removeDocument = RemoveDocument(documentRepository),
            getOpenDocument = GetOpenDocument(documentRepository),
            setOpenDocument = SetOpenDocument(documentRepository)
        )


}