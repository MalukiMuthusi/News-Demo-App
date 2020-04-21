package codes.malukimuthusi.newsdemoapp.viewList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import codes.malukimuthusi.newsdemoapp.database.ArticleDao
import codes.malukimuthusi.newsdemoapp.repository.ArticleRepository

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
    var articles = articleRepository.getArticles()


}

