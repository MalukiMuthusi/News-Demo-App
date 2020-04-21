package codes.malukimuthusi.newsdemoapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import codes.malukimuthusi.newsdemoapp.dataDomain.Article
import codes.malukimuthusi.newsdemoapp.database.ArticleDao
import codes.malukimuthusi.newsdemoapp.database.asDataDomainModel
import codes.malukimuthusi.newsdemoapp.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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


    // Boundary callBack object
    private val boundaryCallBack = PagedListBoundaryCallBack()


    fun getArticles(): LiveData<PagedList<Article>> {
        val dataSourceFactory =
            articleDao.getAllArticles().mapByPage { it.asDataDomainModel() }

        // Configuration for creating a Paged List
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(DATABASE_PAGE_SIZE)
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(60)
            .build()

        return LivePagedListBuilder(dataSourceFactory, pagedListConfig)
            .setBoundaryCallback(boundaryCallBack)
            .build()
    }

    // delete all articles
    fun deleteAllArticles() {
        articleDao.deleteAllArticles()

    }

    /*
    * Refresh the Articles in the Database, the offline Cache.
    *
    * Use Retrofit services to get articles.
    * Insert those articles into the database.
    * */
    suspend fun refreshArticles(articleDao: ArticleDao) {
        try {
            Timber.d("Refresh articles is called.")
            val articles = Network.apiService.getArtilces().await()
            articleDao.insertArticle(*articles.asDatabaseModel())
        } catch (t: Throwable) {
            Timber.e("Refresh articles Error $t")
        }

    }

    /*
   * Boundary Call Back.
   *
   * When there are zero non-viewed articles in the database fetch articles from database
   *
   * */
    inner class PagedListBoundaryCallBack :
        PagedList.BoundaryCallback<Article>() {

        override fun onZeroItemsLoaded() {
            super.onZeroItemsLoaded()
            CoroutineScope(Dispatchers.IO).launch {
                refreshArticles(articleDao)
            }
        }

    }


}


