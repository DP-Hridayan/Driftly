package `in`.hridayan.driftly.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import `in`.hridayan.driftly.settings.data.SettingsDataStore
import `in`.hridayan.driftly.settings.data.repository.SettingsRepositoryImpl
import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository
import `in`.hridayan.driftly.settings.domain.usecase.GetLookAndFeelPageListUseCase
import `in`.hridayan.driftly.settings.domain.usecase.GetSettingsPageListUseCase
import `in`.hridayan.driftly.settings.domain.usecase.ToggleSettingUseCase

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Provides
    fun provideSettingsDataStore(@ApplicationContext context: Context): SettingsDataStore =
        SettingsDataStore(context)

    @Provides
    fun provideSettingsRepository(dataStore: SettingsDataStore): SettingsRepository =
        SettingsRepositoryImpl(dataStore)

    @Provides
    fun provideToggleSettingUseCase(repo: SettingsRepository): ToggleSettingUseCase =
        ToggleSettingUseCase(repo)

    @Provides
    fun provideGetSettingsPageListUseCase(repo: SettingsRepository): GetSettingsPageListUseCase=
        GetSettingsPageListUseCase(repo)

    @Provides
    fun provideGetLookAndFeelPageListUseCase(repo: SettingsRepository): GetLookAndFeelPageListUseCase=
        GetLookAndFeelPageListUseCase(repo)
}