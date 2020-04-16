package codes.malukimuthusi.newsdemoapp.viewList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import codes.malukimuthusi.newsdemoapp.database.ArticleDao
import codes.malukimuthusi.newsdemoapp.repository.ArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListViewModel(application: Application, databaseDAO: ArticleDao) :
    AndroidViewModel(application) {
    /*
    * create a repository object.
    *
    * */
    private val articleRepository = ArticleRepository(databaseDAO)

    /*
    * Live data of a Paged list of articles.
    *
    * The Fragment will observe this data.
    * This is the data submitted to the RecyclerView Adapter.
    *  submitList(articles)
    *
    * */
    val articles = articleRepository.articles


    private fun getArticles() {

        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                articleRepository.articles
            }
        }
    }


}
