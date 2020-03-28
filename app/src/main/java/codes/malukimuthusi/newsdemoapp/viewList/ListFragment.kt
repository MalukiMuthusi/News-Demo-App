package codes.malukimuthusi.newsdemoapp.viewList

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import codes.malukimuthusi.newsdemoapp.R
import codes.malukimuthusi.newsdemoapp.database.ArticleDatabase
import codes.malukimuthusi.newsdemoapp.databinding.ListFragmentBinding

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var viewModel: ListViewModel

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
        * Bind ViewModel to lifecyle of this Fragment.
        *
        * */
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        /*
        * Return a UI View.
        *
        * */
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*
       * Create a viewModel Object.
       *
       * The view model factory will create the viewModel.
       * By implementing viewModel Factory, we supply data to the viewModel when it is being created.
       * */
        val application = requireNotNull(this.activity).application
        val articleDao = ArticleDatabase.getDatabase(application).articleDao
        val viewModelFactory = ListViewModelFactory(application, articleDao)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel::class.java)

        /*
        * Observe Changes in List of Articles.
        *
        * When the Articles change update UI.
        * */
        viewModel.articles.observe(viewLifecycleOwner, Observer { TODO("Submit Article List") })
    }

}
