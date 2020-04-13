package codes.malukimuthusi.newsdemoapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.room.Room.databaseBuilder
import codes.malukimuthusi.newsdemoapp.dataDomain.Article

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
    * Returns a  PagedList of Articles.
    * */
    @Query("SELECT * FROM articles_table")
    fun getAllArticles(): DataSource.Factory<Int, ArticleDB>


    /*
    * Insert an Article.
    * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(vararg articleDB: ArticleDB)
}
