package `in`.hridayan.driftly.settings.domain.repository

import `in`.hridayan.driftly.settings.data.SettingsKeys
import `in`.hridayan.driftly.settings.data.model.SettingsItem
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun setBoolean(key: SettingsKeys, value: Boolean)
    fun getBoolean(key: SettingsKeys): Flow<Boolean>
    suspend fun toggleSetting(key: SettingsKeys)
    suspend fun setInt(key: SettingsKeys, value: Int)
    fun getInt(key: SettingsKeys): Flow<Int>
    suspend fun setFloat(key: SettingsKeys, value: Float)
    fun getFloat(key: SettingsKeys): Flow<Float>
    suspend fun getSettingsPageList(): List<Pair<SettingsItem, Flow<Boolean>>>
    suspend fun getLookAndFeelPageList(): List<Pair<SettingsItem, Flow<Boolean>>>
    suspend fun getAboutPageList(): List<Pair<SettingsItem, Flow<Boolean>>>
    suspend fun getDynamicColorSetting(): Pair<SettingsItem, Flow<Boolean>>
    suspend fun getHighContrastDarkThemeSetting(): Pair<SettingsItem, Flow<Boolean>>
}