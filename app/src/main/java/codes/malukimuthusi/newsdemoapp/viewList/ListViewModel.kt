package codes.malukimuthusi.newsdemoapp.viewList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import codes.malukimuthusi.newsdemoapp.database.ArticleDao
import codes.malukimuthusi.newsdemoapp.repository.ArticleRepository
import kotlinx.coroutines.*

class ListViewModel(application: Application, databaseDAO: ArticleDao) :
    AndroidViewModel(application) {
    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     *
     * Since we pass viewModelJob, you can cancel all coroutines launched by uiScope by calling
     * viewModelJob.cancel()
     */
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /*
    * create a repository object.
    *
    * */
    private val articleRepository = ArticleRepository(databaseDAO)

    /*
    * init is called imediately when this viewModel is created.
    *
    * */
    init {
        /*
        * Refresh the database.
        *
        * The database will always be populated with items.
        * */
        viewModelScope.launch {
            articleRepository.refreshArticles()
        }
    }

    /*
    * Live data of a list of articles.
    *
    * The Fragment will observe this data.
    * This is the data submitted to the RecyclerView Adapter.
    *  submitList(articles)
    * */
    val articles = articleRepository.articles

    override fun onCleared() {
        super.onCleared()

        /*
        * Cancel all jobs started by this viewModel.
        *
        * */
        viewModelScope.cancel()
    }
}
