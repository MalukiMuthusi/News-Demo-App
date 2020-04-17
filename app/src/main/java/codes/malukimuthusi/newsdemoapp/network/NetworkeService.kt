package codes.malukimuthusi.newsdemoapp.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/*
* Retrofit Service to fetch News Article
*
* */
private const val BASEURL = "https://newsapi.org/v2/"

// Retrofit turns HTTP API into Kotlin interface
interface ArticleNetworkService {
    @GET("top-headlines")
    fun getArtilces(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = "24bb98441b3240d98aaf3f0f966eb444"
    ): Deferred<ApiResponse>
}

/*
* Build Moshi object.
*
* Retrofit uses moshi to parse json response string to kotlin objects.
* */
private val mosh = Moshi.Builder()
    .build()

/*
* Create entry for service access.
*
* */
object Network {
    /*
    * Configure Retrofit to parse JSON and use Coroutines.
    *
    * */
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASEURL)
        .addConverterFactory(MoshiConverterFactory.create(mosh))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    // retrofit generates an implementation of ArticleNetworkService interface.
    val apiService = retrofit.create(ArticleNetworkService::class.java)
}