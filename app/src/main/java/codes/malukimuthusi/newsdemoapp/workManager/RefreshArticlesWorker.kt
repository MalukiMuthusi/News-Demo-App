package codes.malukimuthusi.newsdemoapp.workManager

import android.content.Context
import androidx.work.*
import codes.malukimuthusi.newsdemoapp.database.ArticleDatabase
import codes.malukimuthusi.newsdemoapp.repository.ArticleRepository
import timber.log.Timber
import java.util.concurrent.TimeUnit

/*
* Worker for refreshing Articles.
*
* */
class RefreshArticlesWorker(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {

    // Unique name of this work.
    companion object {
        const val WORK_NAME = "com.malukimuthusi.codes.RefreshArticlesWorker"
    }

    val articleDao = ArticleDatabase.getDatabase(ctx).articleDao
    val articleRepository = ArticleRepository(articleDao)


    override suspend fun doWork(): Result {
        return try {
            // Refresh Articles

            Timber.d("Periodic Work")
            articleRepository.refreshArticles(articleDao)

            Result.success()
        } catch (throwable: Throwable) {
            Timber.e("Periodic Work: $throwable")
            Result.retry()
        }


    }
}

/*
    * Fetch News Articles Periodicaly.
    *
    * */
suspend fun refreshNewsArticles(ctx: Context) {

    /*
    * Constraints for fetching News Articles.
    *  Must be connected to the network.
    *
    * */
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .build()

    /*
    * Build Work Request.
    *   With Constraints.
    *
    * */
    val refreshArticlesRepetitiveWorkRequest =
        PeriodicWorkRequestBuilder<RefreshArticlesWorker>(16, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

    /*
    * Create Work Manager.
    *
    *   Request Work.
    *
    * */
    Timber.d("Periodic Work request for sync is scheduled")
    WorkManager.getInstance(ctx)
        .enqueueUniquePeriodicWork(
            RefreshArticlesWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            refreshArticlesRepetitiveWorkRequest
        )
        .await()


}