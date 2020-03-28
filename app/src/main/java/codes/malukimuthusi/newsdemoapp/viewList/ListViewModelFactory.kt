package codes.malukimuthusi.newsdemoapp.viewList

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import codes.malukimuthusi.newsdemoapp.database.ArticleDao

/*
* Factory for creating ListViewModel with parameters.
*
* It creates ListViewModel providing database.
* */
class ListViewModelFactory(val application: Application, val articleDao: ArticleDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListViewModel(application, articleDao) as T
        }
        throw IllegalArgumentException("Unable to construct viewModel")
    }
}