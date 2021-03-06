package codes.malukimuthusi.newsdemoapp.repository

import android.app.Application
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import codes.malukimuthusi.newsdemoapp.dataDomain.Article
import codes.malukimuthusi.newsdemoapp.database.ArticleDao
import codes.malukimuthusi.newsdemoapp.database.ArticleDatabase
import codes.malukimuthusi.newsdemoapp.database.asDataDomainModel
import codes.malukimuthusi.newsdemoapp.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.net.UnknownHostException

/*
* Create repository that will be used as the data source.
*
* The repository fetches data from the local room database.
* It refreshes the local data with data from the News API Service.
* */
class ArticleRepository(private val articleDao: ArticleDao) {

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
        private const val DATABASE_PAGE_SIZE = 20
    }

    /*
    * Return Paged LiveData List
    *  Live<PagedList<Article>>
    *
    * */
    private val dataSourceFactory =
        articleDao.getAllArticles().mapByPage { it.asDataDomainModel() }

    // Configuration for creating a Paged List
    private val pagedListConfig = PagedList.Config.Builder()
        .setPageSize(DATABASE_PAGE_SIZE)
        .setEnablePlaceholders(true)
        .setInitialLoadSizeHint(60)
        .build()

    // Boundary callBack object
    private val boundaryCallBack = PagedListBoundaryCallBack()

    // Live Paged List
    val articles = LivePagedListBuilder(dataSourceFactory, pagedListConfig)
        .setBoundaryCallback(boundaryCallBack)
        .build()

    // delete all articles
    suspend fun deleteAllArticles() {
        withContext(Dispatchers.IO) {
            articleDao.deleteAllArticles()
        }
    }


}

/*
* Refresh the Articles in the Database, the offline Cache.
*
* Use Retrofit services to get articles.
* Insert those articles into the database.
* */
class RefreshArticles(private val articleDao: ArticleDao) {

    suspend fun refreshArticles() {
        withContext(Dispatchers.IO)
        {
            try {
                Timber.d("refresh videos is called")
                val articles = Network.apiService.getArtilces().await()
                // * spread operator used.
                articleDao.insertArticle(*articles.asDatabaseModel())
            } catch (noInternet: UnknownHostException) {
                Timber.e("No network connection error $noInternet")
            } catch (e: Throwable) {
                Timber.e("Network Error $e")
            }

        }
    }
}

/*
   * Boundary Call Back.
   *
   * When there are zero non-viewed articles in the database fetch articles from database
   *
   * */
class PagedListBoundaryCallBack : PagedList.BoundaryCallback<Article>() {
    private val scope = CoroutineScope(Dispatchers.Default)
    private val application = Application()
    val dao = ArticleDatabase.getDatabase(application).articleDao
    private val refreshArticles = RefreshArticles(dao)

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()

        scope.launch {
            refreshArticles.refreshArticles()
        }
    }

}