package codes.malukimuthusi.newsdemoapp.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import codes.malukimuthusi.newsdemoapp.dataDomain.Article
import codes.malukimuthusi.newsdemoapp.dataDomain.ArticleSource
import java.util.*

/*
* This  file contains room database entities.
*   1. ArticleDB
*   2. ArticleDB Source
* */

/*
* ArticleDB Source is an Embedded entity that holds the id of an article
* and the name of the article.
*
* ArticleSourceDB is not a table in the database.
*
* Its properties are created as table columns in Article Table.
*
* */
data class ArticleSourceDB(
    val sourceID: String?,
    val sourceName: String
) {
    fun asDataDomainModel(): ArticleSource {
        return ArticleSource(
            id = sourceID,
            name = sourceName
        )

    }
}

/*
* ArticleDB is a table in the database.
*
* It has columns from Article properties and Article source properties.
* */
@Entity(tableName = "articles_table")
data class ArticleDB(

    /*
    * Embed the article source.
    *
    * This enables room to resolve @source by creating two columns in this table.
    * */
    @Embedded val source: ArticleSourceDB,
    val author: String?,
    val title: String,
    val description: String?,
    @PrimaryKey() val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?,
    val date: Long = Calendar.getInstance().time.time,
    val primaryID: Long = 0L

) {
    /*
    * Function to convert ArticleDB Entity into a dataDomain Model
    * */
    fun asDataDomainModel(): Article {
        return Article(
            author = author,
            source = source.asDataDomainModel(),
            title = title,
            description = description,
            url = url,
            urlToImage = urlToImage,
            publishedAt = publishedAt,
            content = content
        )

    }
}

/*
* Convert a list of Database articles to a list of Data Domain Articles
*
* */
fun List<ArticleDB>.asDataDomainModel(): List<Article> {
    return map {
        Article(
            author = it.author,
            source = it.source.asDataDomainModel(),
            title = it.title,
            description = it.description,
            url = it.url,
            urlToImage = it.urlToImage,
            publishedAt = it.publishedAt,
            content = it.content

        )
    }
}
