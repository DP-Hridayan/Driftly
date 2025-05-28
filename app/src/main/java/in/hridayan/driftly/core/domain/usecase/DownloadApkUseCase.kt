package `in`.hridayan.driftly.core.domain.usecase

import `in`.hridayan.driftly.core.domain.model.DownloadState
import `in`.hridayan.driftly.core.domain.repository.DownloadRepository
import javax.inject.Inject

class DownloadApkUseCase @Inject constructor(
    private val repo: DownloadRepository
) {
    suspend operator fun invoke(
        url: String,
        fileName: String,
        onProgress: (DownloadState) -> Unit
    ) {
        repo.downloadApk(url, fileName, onProgress)
    }

    fun cancel() {
        repo.cancelDownload()
    }
}
