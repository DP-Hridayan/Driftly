package `in`.hridayan.driftly.settings.data.remote.api

import `in`.hridayan.driftly.settings.data.remote.dto.GitHubReleaseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class GitHubApi @Inject constructor(
    private val client: HttpClient
) {
    suspend fun fetchLatestRelease(): GitHubReleaseDto {
        return client.get("https://api.github.com/repos/DP-Hridayan/Driftly/releases/latest").body()
    }
}