package `in`.hridayan.driftly.settings.domain.usecase

import `in`.hridayan.driftly.settings.domain.model.SettingsItem
import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository

class GetAllSettingsUseCase(private val repo: SettingsRepository) {
    suspend operator fun invoke(): List<Pair<SettingsItem, Boolean>> = repo.getAllSettings()
}
