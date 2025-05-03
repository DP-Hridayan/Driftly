package `in`.hridayan.driftly.settings.domain.usecase

import `in`.hridayan.driftly.settings.domain.model.SettingsItem
import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetAllSettingsUseCase(private val repo: SettingsRepository) {
    suspend operator fun invoke(): List<Pair<SettingsItem, Flow<Boolean>>> = repo.getAllSettings()
}
