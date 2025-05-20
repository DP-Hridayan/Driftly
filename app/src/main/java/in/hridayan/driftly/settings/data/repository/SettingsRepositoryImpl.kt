package `in`.hridayan.driftly.settings.data.repository

import `in`.hridayan.driftly.settings.data.SettingsKeys
import `in`.hridayan.driftly.settings.data.model.SettingsDataStore
import `in`.hridayan.driftly.settings.data.SettingsProvider
import `in`.hridayan.driftly.settings.domain.model.SettingsItem
import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val dataStore: SettingsDataStore
) : SettingsRepository {

    override suspend fun isSettingEnabled(key: SettingsKeys): Flow<Boolean> =
        dataStore.isEnabled(key)

    override suspend fun toggleSetting(key: SettingsKeys) = dataStore.toggle(key)

    override suspend fun getSettingsPageList(): List<Pair<SettingsItem, Flow<Boolean>>> {
        return SettingsProvider.settingsPageList.map {
            it to dataStore.isEnabled(it.key)
        }
    }

    override suspend fun getLookAndFeelPageList(): List<Pair<SettingsItem, Flow<Boolean>>> {
        return SettingsProvider.lookAndFeelPageList.map {
            it to dataStore.isEnabled(it.key)
        }
    }

    override suspend fun getAboutPageList(): List<Pair<SettingsItem, Flow<Boolean>>> {
        return SettingsProvider.aboutPageList.map {
            it to dataStore.isEnabled(it.key)
        }
    }

    override suspend fun getDynamicColorSetting(): Pair<SettingsItem, Flow<Boolean>> {
        return SettingsProvider.dynamicColorSetting to dataStore.isEnabled(SettingsKeys.DYNAMIC_COLORS)
    }

    override suspend fun getHighContrastDarkThemeSetting(): Pair<SettingsItem, Flow<Boolean>> {
        return SettingsProvider.highContrastDarkThemeSetting to dataStore.isEnabled(SettingsKeys.HIGH_CONTRAST_DARK_MODE)
    }
}
