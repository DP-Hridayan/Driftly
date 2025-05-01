package `in`.hridayan.driftly.settings.domain.repository

import `in`.hridayan.driftly.settings.domain.model.SettingsItem

interface SettingsRepository{
    suspend fun isSettingEnabled(key: String): Boolean
    suspend fun toggleSetting(key: String)
    suspend fun getAllSettings(): List<Pair<SettingsItem, Boolean>>
}