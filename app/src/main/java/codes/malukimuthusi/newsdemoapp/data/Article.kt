package codes.malukimuthusi.newsdemoapp.data

import javax.sql.CommonDataSource

data class Article(
    val source: ArticleSource,
    val author: String?,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
) {

}
