package codes.malukimuthusi.newsdemoapp.workManager

import android.app.Application
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import codes.malukimuthusi.newsdemoapp.database.ArticleDao
import codes.malukimuthusi.newsdemoapp.database.ArticleDatabase
import codes.malukimuthusi.newsdemoapp.network.Network
import codes.malukimuthusi.newsdemoapp.repository.ArticleRepository
import kotlinx.coroutines.*
import timber.log.Timber

class ApiWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
    companion object {
        const val WORK_NAME = "com.malukimuthusi.codes.ApiWorker"
    }

    override suspend fun doWork(): Result {
        try {
            // create an instance of repository
            val articleRepository =
                ArticleRepository(ArticleDatabase.getDatabase(applicationContext).articleDao)

            articleRepository.refreshArticles()
            return Result.success()
        } catch (throwable: Throwable) {
            Timber.e(throwable)
            return Result.failure()
        }


    }
}