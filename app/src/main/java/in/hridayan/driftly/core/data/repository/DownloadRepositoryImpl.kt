package `in`.hridayan.driftly.core.data.repository

import android.content.Context
import `in`.hridayan.driftly.core.domain.model.DownloadState
import `in`.hridayan.driftly.core.domain.repository.DownloadRepository
import `in`.hridayan.driftly.core.utils.copyAndCloseTo
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.contentLength
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class DownloadRepositoryImpl @Inject constructor(
    private val context: Context,
    private val client: HttpClient
) : DownloadRepository {

    private var downloadJob: Job? = null

    override suspend fun downloadApk(
        url: String,
        fileName: String,
        onProgress: (DownloadState) -> Unit
    ) {
        downloadJob = CoroutineScope(Dispatchers.IO).launch {
            try {
                onProgress(DownloadState.Started)

                val file = File(context.cacheDir, fileName)
                val response = client.get(url)

                val contentLength = response.contentLength()
                val totalBytes =
                    if (contentLength != null && contentLength > 0) contentLength else -1L

                response.bodyAsChannel().copyAndCloseTo(
                    output = file.outputStream(),
                    totalBytes = totalBytes,
                    onProgress = { percent ->
                        if (percent >= 0) {
                            onProgress(DownloadState.Progress(percent.toFloat() / 100f))
                        } else {
                            onProgress(DownloadState.Started)
                        }
                    },
                    isCancelled = { !isActive }
                )

                if (isActive) {
                    onProgress(DownloadState.Success(file))
                }
            } catch (e: CancellationException) {
                onProgress(DownloadState.Cancelled)
            } catch (e: Exception) {
                onProgress(DownloadState.Error(e.message ?: "Download failed"))
            }
        }
    }

    override fun cancelDownload() {
        downloadJob?.cancel()
    }
}
