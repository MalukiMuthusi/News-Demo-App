package codes.malukimuthusi.newsdemoapp.viewList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import codes.malukimuthusi.newsdemoapp.dataDomain.Article
import codes.malukimuthusi.newsdemoapp.database.ArticleDB
import codes.malukimuthusi.newsdemoapp.databinding.SingleItemBinding

/*
* The adapter.
*
* */
class ArticlesAdapter(private val clickListener: ArticleClickListener) :
    PagedListAdapter<Article, RecyclerView.ViewHolder>(ArticleDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ArticleViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ArticleViewHolder -> {
                holder.bind(getItem(position)!!, clickListener)
            }
        }
    }

}

/*
* Article Item ViewHolder.
*
* Holds the view of a single Article.
* */
class ArticleViewHolder private constructor(private val binding: SingleItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    /*
    * Bind data to the View
    *
    * */
    fun bind(article: Article, clickListener: ArticleClickListener) {
        binding.article = article
        binding.clickListener = clickListener

        binding.executePendingBindings()

    }

    /*
    * Inflate UI.
    *
    * */
    companion object {
        fun from(parent: ViewGroup): ArticleViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SingleItemBinding.inflate(layoutInflater, parent, false)
            return ArticleViewHolder(binding)
        }
    }
}

/*
* This class is used calculate data changes for the ListAdapter.
*
* */
class ArticleDiff : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}

/*
* Click Listener.
*
* Implements a single method, that takes a named lambda.
* */
class ArticleClickListener(val clickListener: (url: String) -> Unit) {
    /*
    * This function will be called when a view is clicked.
    *
    * The item that was clicked passed it's URL to this function.
    * */
    fun onClick(article: Article) {
        return clickListener(article.url)
    }
}