package codes.malukimuthusi.newsdemoapp.workManager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import codes.malukimuthusi.newsdemoapp.database.ArticleDatabase
import codes.malukimuthusi.newsdemoapp.repository.RefreshArticles
import timber.log.Timber

/*
* Worker for refreshing Articles.
*
* */
class RefreshArticlesWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    // Unique name of this work.
    companion object {
        const val WORK_NAME = "com.malukimuthusi.codes.RefreshArticlesWorker"
    }


    override suspend fun doWork(): Result {
        return try {
            // Refresh Articles
            val refreshArticles =
                RefreshArticles(ArticleDatabase.getDatabase(applicationContext).articleDao)

            refreshArticles.refreshArticles()
            Result.success()
        } catch (throwable: Throwable) {
            Timber.e(throwable)
            Result.failure()
        }


    }
}