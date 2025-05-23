package `in`.hridayan.driftly.settings.data.repository

import `in`.hridayan.driftly.settings.data.SettingsKeys
import `in`.hridayan.driftly.settings.data.SettingsProvider
import `in`.hridayan.driftly.settings.data.model.SettingsDataStore
import `in`.hridayan.driftly.settings.data.model.SettingsItem
import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val dataStore: SettingsDataStore
) : SettingsRepository {

    override suspend fun setBoolean(key: SettingsKeys, value: Boolean) =
        dataStore.setBoolean(key, value)
    override fun getBoolean(key: SettingsKeys): Flow<Boolean> = dataStore.booleanFlow(key)
    override suspend fun toggleSetting(key: SettingsKeys) = dataStore.toggle(key)
    override suspend fun setInt(key: SettingsKeys, value: Int) = dataStore.setInt(key, value)
    override  fun getInt(key: SettingsKeys): Flow<Int> = dataStore.intFlow(key)
    override suspend fun setFloat(key: SettingsKeys, value: Float) = dataStore.setFloat(key, value)
    override  fun getFloat(key: SettingsKeys): Flow<Float> = dataStore.floatFlow(key)

    override suspend fun getSettingsPageList(): List<Pair<SettingsItem, Flow<Boolean>>> {
        return SettingsProvider.settingsPageList.map {
            it to dataStore.booleanFlow(it.key)
        }
    }

    override suspend fun getLookAndFeelPageList(): List<Pair<SettingsItem, Flow<Boolean>>> {
        return SettingsProvider.lookAndFeelPageList.map {
            it to dataStore.booleanFlow(it.key)
        }
    }

    override suspend fun getAboutPageList(): List<Pair<SettingsItem, Flow<Boolean>>> {
        return SettingsProvider.aboutPageList.map {
            it to dataStore.booleanFlow(it.key)
        }
    }

    override suspend fun getDynamicColorSetting(): Pair<SettingsItem, Flow<Boolean>> {
        return SettingsProvider.dynamicColorSetting to dataStore.booleanFlow(SettingsKeys.DYNAMIC_COLORS)
    }

    override suspend fun getHighContrastDarkThemeSetting(): Pair<SettingsItem, Flow<Boolean>> {
        return SettingsProvider.highContrastDarkThemeSetting to dataStore.booleanFlow(SettingsKeys.HIGH_CONTRAST_DARK_MODE)
    }
}
