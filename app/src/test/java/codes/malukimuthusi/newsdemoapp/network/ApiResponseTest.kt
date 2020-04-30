package codes.malukimuthusi.newsdemoapp.network

import codes.malukimuthusi.newsdemoapp.database.ArticleDB
import codes.malukimuthusi.newsdemoapp.database.ArticleSourceDB
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

class ApiResponseTest {

    // given a network api response, convert the result into a database data model.
    val articleSourceNetwork = ArticleSourceNetwork("cITECIZEN.COM", "CITEZEN")
    val articleNetwork = ArticleNetwork(
        articleSourceNetwork, "maluki",
        "news", "description", "url",
        "urlToImage", "publishedAt", "content"
    )
    val listOfArticles = listOf<ArticleNetwork>(articleNetwork)
    val apiResponse = ApiResponse("ok", 2, listOfArticles)


    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
    }


    @Test
    fun asDataDomain() {
        val articleSourceDB = ArticleSourceDB("cITECIZEN.COM", "CITEZEN")
        val articleDB = ArticleDB(
            articleSourceDB, "maluki", "news", "description",
            "url", "urlToImage", "publishedAt", "content", 200L
        )

        val arrayOfDBArticles = Array(1) { articleDB }

        assertThat(arrayOfDBArticles, `is`(apiResponse.asDatabaseModel(200L)))

    }
}