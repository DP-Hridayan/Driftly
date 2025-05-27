package `in`.hridayan.driftly.settings.domain.repository

import `in`.hridayan.driftly.settings.data.local.SettingsKeys
import `in`.hridayan.driftly.settings.data.local.model.SettingsItem
import `in`.hridayan.driftly.settingsv2.PreferenceGroup
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun setBoolean(key: SettingsKeys, value: Boolean)
    fun getBoolean(key: SettingsKeys): Flow<Boolean>
    suspend fun toggleSetting(key: SettingsKeys)
    suspend fun setInt(key: SettingsKeys, value: Int)
    fun getInt(key: SettingsKeys): Flow<Int>
    suspend fun setFloat(key: SettingsKeys, value: Float)
    fun getFloat(key: SettingsKeys): Flow<Float>

    suspend fun getLookAndFeelPageList(): List<Pair<SettingsItem, Flow<Boolean>>>
    suspend fun getAboutPageList(): List<Pair<SettingsItem, Flow<Boolean>>>
    suspend fun getDynamicColorSetting(): Pair<SettingsItem, Flow<Boolean>>
    suspend fun getHighContrastDarkThemeSetting(): Pair<SettingsItem, Flow<Boolean>>
    suspend fun getBehaviorPageList(): List<PreferenceGroup>

    suspend fun getSettingsPageList(): List<PreferenceGroup>

}