package codes.malukimuthusi.newsdemoapp.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

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


    /*
    * Delete Old News Articles.
    *
    * Articles earlier than 24 hours
    *
    * */
    @Query("DELETE FROM articles_table WHERE date >= :time")
    fun deleteOldArticles(time: Long)

    /*
    * Delete all articles.
    *
    * */
    @Query("DELETE  FROM articles_table")
    fun deleteAllArticles()

    /*
    * Get article with this url
    * */
    @Query("SELECT * FROM articles_table WHERE url is :url")
    fun getArticleWithUrl(url: String): ArticleDB
}
