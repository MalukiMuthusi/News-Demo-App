package codes.malukimuthusi.newsdemoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*
* Create Room Database
*
* */
@Database(entities = [ArticleDB::class], version = 10, exportSchema = false)
abstract class ArticleDatabase : RoomDatabase() {
    abstract val articleDao: ArticleDao

    companion object {
        @Volatile
        private var INSTANCE: ArticleDatabase? = null

        /*
        * Create database.
        *
        * Only creates one instance of the database.
        * */
        fun getDatabase(context: Context): ArticleDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ArticleDatabase::class.java,
                        "news_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}

