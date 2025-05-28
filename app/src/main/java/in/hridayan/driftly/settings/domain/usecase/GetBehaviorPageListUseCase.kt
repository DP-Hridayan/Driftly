package `in`.hridayan.driftly.settings.domain.usecase

import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository
import `in`.hridayan.driftly.settings.data.local.model.PreferenceGroup

class GetBehaviorPageListUseCase(private val repo: SettingsRepository) {
    suspend operator fun invoke(): List<PreferenceGroup> = repo.getBehaviorPageList()
}
