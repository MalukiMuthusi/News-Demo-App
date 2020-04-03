package codes.malukimuthusi.newsdemoapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import codes.malukimuthusi.newsdemoapp.dataDomain.Article
import codes.malukimuthusi.newsdemoapp.database.ArticleDB
import codes.malukimuthusi.newsdemoapp.database.ArticleDao
import codes.malukimuthusi.newsdemoapp.database.ArticleDatabase
import codes.malukimuthusi.newsdemoapp.database.asDataDomainModel
import codes.malukimuthusi.newsdemoapp.network.ArticleService
import codes.malukimuthusi.newsdemoapp.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

/*
* Create repository that will be used as the data source.
*
* The repository fetches data from the local room database.
* It refreshes the local data with data from the News API Service.
* */
class ArticleRepository(private val articleDao: ArticleDao) {

    /*
    * Get a LiveData List of Articles. (as Data Domain Model)
    *
    * Convert the articles to Domain Data Model.
    * */

    val articlesFactory: DataSource.Factory<Int, ArticleDB> = articleDao.getAllArticles()
    val articles = LivePagedListBuilder(articlesFactory, DATABASE_PAGE_SIZE).build()
//    val articles = Transformations.map(articlesAsDB) { it.asDataDomainModel() }

    /*
    * Refresh the Articles in the Database, the offline Cache.
    *
    * Use Retrofit services to get articles.
    * Insert those articles into the database.
    * */
    suspend fun refreshArticles() {
        withContext(Dispatchers.IO)
        {
            Timber.d("refresh videos is called")
            val articles = Network.apiService.getArtilces().await()
            articleDao.insertArticle(*articles.asDatabaseModel())
        }
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
        private const val DATABASE_PAGE_SIZE = 20
    }

}