package codes.malukimuthusi.newsdemoapp.viewDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import codes.malukimuthusi.newsdemoapp.R

@Suppress("DEPRECATION")
class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

//    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

}
