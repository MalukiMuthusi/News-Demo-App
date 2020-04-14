package codes.malukimuthusi.newsdemoapp.repository

import android.app.Application
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import codes.malukimuthusi.newsdemoapp.dataDomain.Article
import codes.malukimuthusi.newsdemoapp.database.ArticleDao
import codes.malukimuthusi.newsdemoapp.database.ArticleDatabase
import codes.malukimuthusi.newsdemoapp.database.asDataDomainModel
import codes.malukimuthusi.newsdemoapp.network.Network
import kotlinx.coroutines.*
import timber.log.Timber

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
    * Return Paged Live List
    *  Live<PagedList<Article>>
    *
    * */
    private val dataSourceFactory =
        articleDao.getAllArticles().mapByPage() { it.asDataDomainModel() }

    // create a page configuration
    private val pagedListConfig = PagedList.Config.Builder()
        .setPageSize(DATABASE_PAGE_SIZE)
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(60)
        .build()

    // Boundary callBack object
    private val boundaryCallBack = PagedListBoundaryCallBack()

    // Live Paged List
    val articles = LivePagedListBuilder(dataSourceFactory, pagedListConfig)
        .setBoundaryCallback(boundaryCallBack)
        .build()


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
            Timber.d("refresh videos is called")
            val articles = Network.apiService.getArtilces().await()

            // * spread operator used.
            articleDao.insertArticle(*articles.asDatabaseModel())
        }
    }
}

/*
   * Bounday Call Back.
   *
   * When there are zero non-viewed articles in the database fetch articles from database
   *
   * */
class PagedListBoundaryCallBack : PagedList.BoundaryCallback<Article>() {

    val jobScope = Job()
    val scope = CoroutineScope(jobScope)
    val application = Application()
    val dao = ArticleDatabase.getDatabase(application).articleDao
    private val refreshArticles = RefreshArticles(dao)

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()

        scope.launch {
            refreshArticles.refreshArticles()
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: Article) {
        super.onItemAtEndLoaded(itemAtEnd)
    }

    override fun onItemAtFrontLoaded(itemAtFront: Article) {
        super.onItemAtFrontLoaded(itemAtFront)
    }
}