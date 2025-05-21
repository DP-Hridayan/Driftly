package `in`.hridayan.driftly.settings.data.repository

import `in`.hridayan.driftly.settings.data.remote.api.GitHubApi
import `in`.hridayan.driftly.settings.data.remote.mapper.toDomain
import `in`.hridayan.driftly.settings.domain.model.GitHubRelease
import `in`.hridayan.driftly.settings.domain.repository.UpdateRepository
import javax.inject.Inject

class UpdateRepositoryImpl @Inject constructor(
    private val api: GitHubApi
) : UpdateRepository {
    override suspend fun fetchLatestRelease(): GitHubRelease? {
        return try {
            api.fetchLatestRelease().toDomain()
        } catch (e: Exception) {
            null
        }
    }
}