package `in`.hridayan.driftly.settings.domain.repository

import `in`.hridayan.driftly.core.utils.constants.SettingsKeys
import `in`.hridayan.driftly.settings.domain.model.SettingsItem
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun isSettingEnabled(key: SettingsKeys): Flow<Boolean>
    suspend fun toggleSetting(key: SettingsKeys)
    suspend fun getSettingsPageList(): List<Pair<SettingsItem, Flow<Boolean>>>
    suspend fun getLookAndFeelPageList(): List<Pair<SettingsItem, Flow<Boolean>>>
    suspend fun getAboutPageList(): List<Pair<SettingsItem, Flow<Boolean>>>
}