package `in`.hridayan.driftly.settings.domain.usecase

import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository

class ToggleSettingUseCase(private val repo: SettingsRepository) {
    suspend operator fun invoke(key: String) = repo.toggleSetting(key)
}