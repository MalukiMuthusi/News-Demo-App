package codes.malukimuthusi.newsdemoapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Room.databaseBuilder

/*
* Database Access Object. DAO
*
* Transforms functions into database Queries.
* */
@Dao
interface ArticleDao {
    /*
    * Get a list of all articles.
    *
    * Returns a LiveData List of Articles.
    * */
    @Query("SELECT * FROM articles_table")
    fun getAllArticles(): LiveData<ArticleDB>

    /*
    * Insert an Article.
    * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(articleDB: ArticleDB)
}

/*
* Create Room Database
*
* */
@Database(entities = [ArticleDB::class], version = 1)
abstract class ArticleDatabase() : RoomDatabase() {
    abstract val articleDao: ArticleDao
}

private lateinit var INSTANCE: ArticleDatabase
/*
* Create database.
*
* Only creates one instance of the database.
* */
fun getDatabase(context: Context): ArticleDatabase {
    synchronized(ArticleDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "news_db"
            )
                .build()
        }
    }
    return INSTANCE
}