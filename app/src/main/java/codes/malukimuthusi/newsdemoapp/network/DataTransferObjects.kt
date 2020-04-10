package codes.malukimuthusi.newsdemoapp.network

import codes.malukimuthusi.newsdemoapp.dataDomain.Article
import codes.malukimuthusi.newsdemoapp.dataDomain.ArticleSource
import codes.malukimuthusi.newsdemoapp.database.ArticleDB
import codes.malukimuthusi.newsdemoapp.database.ArticleSourceDB

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
    fun asDatabaseModel(): Array<ArticleDB> {
        return articles.map {
            ArticleDB(
                source = it.source.asDatabaseModel(),
                author = it.author,
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                content = it.content
            )
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
    * Convert to a Data Domain Model
    *
    * */
    fun asDataDomainModel(): ArticleSource {
        return ArticleSource(
            id = id,
            name = name
        )
    }

    /*
    * Convert to a Database Model.
    *
    * */
    fun asDatabaseModel(): ArticleSourceDB {
        return ArticleSourceDB(
            sourceID = id,
            sourceName = name
        )
    }

}
