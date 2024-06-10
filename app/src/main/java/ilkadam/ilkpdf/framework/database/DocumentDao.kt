package ilkadam.ilkpdf.framework.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DocumentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDocument(document: DocumentEntity)

    @Query("SELECT * FROM document ORDER BY id DESC")
    suspend fun getDocuments(): List<DocumentEntity>

    @Delete
    suspend fun removeDocument(document: DocumentEntity)
}