package `in`.hridayan.driftly.settings.data.remote.api

import `in`.hridayan.driftly.settings.data.remote.dto.GitHubReleaseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class GitHubApi @Inject constructor(
    private val client: HttpClient
) {
    private val releasesUrl = "https://api.github.com/repos/DP-Hridayan/Driftly/releases"
    private val latestReleaseUrl = "https://api.github.com/repos/DP-Hridayan/Driftly/releases/latest"

    suspend fun fetchLatestRelease(includePrerelease: Boolean): GitHubReleaseDto {
        return if (includePrerelease) {
            val allReleases: List<GitHubReleaseDto> = client.get(releasesUrl).body()
            allReleases.first()
        } else {
            client.get(latestReleaseUrl).body()
        }
    }
}
