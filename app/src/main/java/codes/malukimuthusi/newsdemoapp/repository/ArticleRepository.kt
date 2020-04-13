package codes.malukimuthusi.newsdemoapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.toLiveData
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
    * Return Paged Live List
    *  Live<PagedList<Article>>
    *
    * */
    private val dataSourceFactory = articleDao.getAllArticles().mapByPage() { it.asDataDomainModel() }

    // create a page configuration
    private val pagedListConfig = PagedList.Config.Builder()
        .setPageSize(DATABASE_PAGE_SIZE)
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(60)
        .build()

    // Live Paged List
    val articles = LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()


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

            // * spread operator used.
            articleDao.insertArticle(*articles.asDatabaseModel())
        }
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
        private const val DATABASE_PAGE_SIZE = 20
    }

}