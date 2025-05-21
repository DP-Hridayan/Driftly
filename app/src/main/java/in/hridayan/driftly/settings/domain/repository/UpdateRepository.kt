package `in`.hridayan.driftly.settings.domain.repository

import `in`.hridayan.driftly.settings.domain.model.GitHubRelease

interface UpdateRepository {
    suspend fun fetchLatestRelease(): GitHubRelease?
}