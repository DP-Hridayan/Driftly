package `in`.hridayan.driftly.settings.domain.usecase

import `in`.hridayan.driftly.settings.data.model.SettingsItem
import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetDynamicColorSettingUseCase(private val repo: SettingsRepository) {
    suspend operator fun invoke(): Pair<SettingsItem, Flow<Boolean>> = repo.getDynamicColorSetting()
}
