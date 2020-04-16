package codes.malukimuthusi.newsdemoapp.viewList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import codes.malukimuthusi.newsdemoapp.R
import codes.malukimuthusi.newsdemoapp.database.ArticleDatabase
import codes.malukimuthusi.newsdemoapp.databinding.ListFragmentBinding

class ListFragment : Fragment() {


//    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*
        * Inflate the UI.
        *
        * */
        val binding = ListFragmentBinding.inflate(inflater)


        /*
       * Create a viewModel Object.
       *
       * The view model factory will create the viewModel.
       * By implementing viewModel Factory, we supply data to the viewModel when it is being created.
       * */
        val application = requireNotNull(this.activity).application
        val articleDao = ArticleDatabase.getDatabase(application).articleDao
        val viewModel by viewModels<ListViewModel> {
            ListViewModelFactory(
                application,
                articleDao
            )
        }
//        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel::class.java)

        /*
        * Bind ViewModel to lifecyle of this Fragment.
        *
        * */
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        /*
        * Create an Adapter.
        *
        * */
        val adapter = ArticlesAdapter(
            ArticleClickListener { }
        )
        binding.articleList.adapter = adapter


        /*
        * Observe Changes in PagedList of Articles.
        *
        * When the Articles change update UI.
        * */
        viewModel.articles.observe(viewLifecycleOwner, Observer {
            it.let {
                adapter.submitList(it)
            }
        })

        // display drawer when menu icon clicked
        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(binding.navigationDrawer)
        }

        /*
        * Delete all articles.
        *
        * When delete icon clicked on the menu
        *
        * */
//        binding.topAppBar.setOnMenuItemClickListener {
//            when (it.itemId) {
//                R.id.delete_all -> {
//                    viewModel.deleteAllArticles()
//                    true
//                }
//                else -> false
//            }
//        }

        binding.navigationDrawer.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.delete_all -> {
                    viewModel.deleteAllArticles()
                    true
                }
                else -> false
            }
        }
        /*
        * Return a UI View.
        *
        * */
        return binding.root
    }


}
