package `in`.hridayan.driftly.settings.domain.usecase

import `in`.hridayan.driftly.settings.data.local.model.SettingsItem
import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetHighContrastDarkThemeSettingUseCase(private val repo: SettingsRepository) {
    suspend operator fun invoke(): Pair<SettingsItem, Flow<Boolean>> = repo.getHighContrastDarkThemeSetting()
}
