package `in`.hridayan.driftly.settings.domain.usecase

import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository
import `in`.hridayan.driftly.settingsv2.PreferenceGroup

class GetSettingsPageListUseCase(private val repo: SettingsRepository) {
    suspend operator fun invoke(): List<PreferenceGroup> = repo.getSettingsPageList()
}
