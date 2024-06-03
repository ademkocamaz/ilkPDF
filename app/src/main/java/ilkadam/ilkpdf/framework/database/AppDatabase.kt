package ilkadam.ilkpdf.framework.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [BookmarkEntity::class, DocumentEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    /*companion object {

        private const val DATABASE_NAME = "ilkpdf.db"

        private var instance: AppDatabase? = null

        private fun create(context: Context): AppDatabase =
            Room.databaseBuilder(
                context = context,
                AppDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()

        fun getInstance(context: Context): AppDatabase =
            (instance ?: create(context)).also {
                instance = it
            }
    }*/

    abstract fun bookmarkDao(): BookmarkDao
    abstract fun documentDao(): DocumentDao
}