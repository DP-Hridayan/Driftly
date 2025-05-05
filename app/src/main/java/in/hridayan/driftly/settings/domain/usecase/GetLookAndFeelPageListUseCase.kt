package `in`.hridayan.driftly.settings.domain.usecase

import `in`.hridayan.driftly.settings.domain.model.SettingsItem
import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetLookAndFeelPageListUseCase(private val repo: SettingsRepository) {
    suspend operator fun invoke(): List<Pair<SettingsItem, Flow<Boolean>>> = repo.getLookAndFeelPageList()
}

