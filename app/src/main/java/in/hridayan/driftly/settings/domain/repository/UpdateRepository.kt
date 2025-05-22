package `in`.hridayan.driftly.settings.domain.repository

import `in`.hridayan.driftly.settings.domain.model.UpdateResult

interface UpdateRepository {
    suspend fun fetchLatestRelease(includePrerelease:Boolean): UpdateResult
}