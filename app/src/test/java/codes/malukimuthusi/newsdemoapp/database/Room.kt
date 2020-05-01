package codes.malukimuthusi.newsdemoapp.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
//@Config(Sdk = Build.VERSION_CODES.O_MR1)
class ArticlesDaoTest {

    @get:Rule
    var instantExcutorRule = InstantTaskExecutorRule()

    private lateinit var database: ArticleDatabase

    @Before
    fun initDB() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ArticleDatabase::class.java
        ).build()
    }

    @After
    fun closeDB() = database.close()

    @Test
    fun insertArticles() = runBlocking {
        val articleSourceDB = ArticleSourceDB("CNN.COM", "CNN")
        val articleDB = ArticleDB(
            articleSourceDB, "CNN AUTHOR", "BREAKING NEWS",
            "ANOTHER VIRUS BREAKOUT", "image.com", "iiii", "today", "contet"
        )

        database.articleDao.insertArticle(articleDB)

        val article = database.articleDao.getArticleWithUrl("image.com")

        assertEquals(articleDB, article)
    }
}