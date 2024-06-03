package ilkadam.ilkpdf.framework.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ilkadam.ilkpdf.domain.Bookmark

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(bookmark: BookmarkEntity)

    @Query("SELECT * FROM bookmark WHERE document_uri=:documentUri")
    suspend fun getBookmarks(documentUri: String): List<BookmarkEntity>

    @Delete
    suspend fun removeBookmark(bookmark: BookmarkEntity)
}