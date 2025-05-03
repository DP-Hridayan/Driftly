package `in`.hridayan.driftly.settings.domain.usecase

import `in`.hridayan.driftly.core.utils.constants.SettingsKeys
import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository

class ToggleSettingUseCase(private val repo: SettingsRepository) {
    suspend operator fun invoke(key: SettingsKeys) = repo.toggleSetting(key)
}