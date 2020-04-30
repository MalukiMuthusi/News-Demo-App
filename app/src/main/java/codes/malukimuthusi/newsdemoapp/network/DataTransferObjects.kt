package codes.malukimuthusi.newsdemoapp.network

import codes.malukimuthusi.newsdemoapp.database.ArticleDB
import codes.malukimuthusi.newsdemoapp.database.ArticleSourceDB
import java.util.*

/*
* This file holds the data Transfer Objects.
*
* They are responsible for parsing responses from the server, or formatting objects to send to
* the server.
*
* You convert them to data Domain Objects before using them
* */

/*
* The first level response of the Network results looks like.
*
*   {
*       "status": "ok",
*       "totalResults": 38,
*       "articles": []
*   }
*
* */
data class ApiResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleNetwork>
) {

    /*
    * Convert the response into a Database Model
    *
    * */
    fun asDatabaseModel(dateDefault: Long = Calendar.getInstance().time.time): Array<ArticleDB> {
        return articles.map { articleNetwork ->
            articleNetwork.asArticleDB(dateDefault)
        }.toTypedArray()
    }
}

/*
* Articles
*
*  */
data class ArticleNetwork(
    val source: ArticleSourceNetwork,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
) {
    fun asArticleDB(dateDefault: Long = Calendar.getInstance().time.time): ArticleDB {
        return ArticleDB(
            source.asSourceDB(),
            author,
            title,
            description,
            url,
            urlToImage,
            publishedAt,
            content,
            date = dateDefault
        )
    }
}

/*
* Article Source.
*
* */
data class ArticleSourceNetwork(
    val id: String?,
    val name: String
) {

    /*
    * Convert to a Database Model.
    *
    * */
    fun asSourceDB(): ArticleSourceDB {
        return ArticleSourceDB(
            sourceID = id,
            sourceName = name
        )
    }

}
