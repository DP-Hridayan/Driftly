package `in`.hridayan.driftly.core.domain.repository

import `in`.hridayan.driftly.core.domain.model.DownloadState

interface DownloadRepository {
    suspend fun downloadApk(
        url: String,
        fileName: String,
        onProgress: (DownloadState) -> Unit
    )

    fun cancelDownload()
}
