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
    fun getAllArticles(): LiveData<List<ArticleDB>>

    /*
    * Insert an Article.
    * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(vararg articleDB: ArticleDB)
}
