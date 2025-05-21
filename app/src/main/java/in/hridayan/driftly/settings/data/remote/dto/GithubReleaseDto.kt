package `in`.hridayan.driftly.settings.data.remote.dto

import kotlinx.serialization.SerialName

@Serializable
data class GitHubReleaseDto(
    @SerialName("tag_name")
    val tagName: String
)