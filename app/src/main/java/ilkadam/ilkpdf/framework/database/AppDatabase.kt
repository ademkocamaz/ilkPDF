package ilkadam.ilkpdf.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DocumentEntity::class],
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
    abstract fun documentDao(): DocumentDao
}