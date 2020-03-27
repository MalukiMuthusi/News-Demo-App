package codes.malukimuthusi.newsdemoapp.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/*
* Retrofit Service to fetch News Article
*
* */
private const val BASEURL = "https://newsapi.org/v2/top-headlines"

interface ArticleService {
    @GET("")
    fun getArtilces(): Deferred<ApiResponse>
}

/*
* Build Moshi object.
*
* Retrofit uses moshi to parse json response string to kotlin objects.
* */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
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
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val apiService = retrofit.create(ArticleService::class.java)
}