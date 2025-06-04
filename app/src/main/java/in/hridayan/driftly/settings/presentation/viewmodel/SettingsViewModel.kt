package `in`.hridayan.driftly.settings.presentation.viewmodel

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import `in`.hridayan.driftly.core.common.constants.NotificationTags
import `in`.hridayan.driftly.core.common.constants.UrlConst
import `in`.hridayan.driftly.notification.scheduler.WorkScheduler
import `in`.hridayan.driftly.notification.createAppNotificationSettingsIntent
import `in`.hridayan.driftly.notification.isNotificationPermissionGranted
import `in`.hridayan.driftly.navigation.AboutScreen
import `in`.hridayan.driftly.navigation.AutoUpdateScreen
import `in`.hridayan.driftly.navigation.BackupAndRestoreScreen
import `in`.hridayan.driftly.navigation.BehaviorScreen
import `in`.hridayan.driftly.navigation.ChangelogScreen
import `in`.hridayan.driftly.navigation.CustomisationScreen
import `in`.hridayan.driftly.navigation.DarkThemeScreen
import `in`.hridayan.driftly.navigation.LookAndFeelScreen
import `in`.hridayan.driftly.navigation.NotificationScreen
import `in`.hridayan.driftly.settings.data.local.SettingsKeys
import `in`.hridayan.driftly.settings.data.local.model.PreferenceGroup
import `in`.hridayan.driftly.settings.domain.model.BackupOption
import `in`.hridayan.driftly.settings.domain.model.NotificationState
import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository
import `in`.hridayan.driftly.settings.domain.usecase.ToggleSettingUseCase
import `in`.hridayan.driftly.settings.presentation.event.SettingsUiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val settingsRepository: SettingsRepository,
    private val toggleSettingUseCase: ToggleSettingUseCase,
) : ViewModel() {
    var settingsPageList by mutableStateOf<List<PreferenceGroup>>(emptyList())
        private set

    var lookAndFeelPageList by mutableStateOf<List<PreferenceGroup>>(emptyList())
        private set

    var darkThemePageList by mutableStateOf<List<PreferenceGroup>>(emptyList())
        private set

    var aboutPageList by mutableStateOf<List<PreferenceGroup>>(emptyList())
        private set

    var autoUpdatePageList by mutableStateOf<List<PreferenceGroup>>(emptyList())
        private set

    var behaviorPageList by mutableStateOf<List<PreferenceGroup>>(emptyList())
        private set

    var backupPageList by mutableStateOf<List<PreferenceGroup>>(emptyList())
        private set

    var notificationsPageList by mutableStateOf<List<PreferenceGroup>>(emptyList())
        private set

    fun loadSettings() {
        viewModelScope.launch {
            val lookAndFeel = settingsRepository.getLookAndFeelPageList()
            val settings = settingsRepository.getSettingsPageList()
            val about = settingsRepository.getAboutPageList()
            val autoUpdate = settingsRepository.getAutoUpdatePageList()
            val behavior = settingsRepository.getBehaviorPageList()
            val darkTheme = settingsRepository.getDarkThemePageList()
            val backup = settingsRepository.getBackupPageList()
            val notifications = settingsRepository.getNotificationsPageList()

            settingsPageList = settings
            autoUpdatePageList = autoUpdate
            lookAndFeelPageList = lookAndFeel
            aboutPageList = about
            behaviorPageList = behavior
            darkThemePageList = darkTheme
            backupPageList = backup
            notificationsPageList = notifications
        }
    }

    private val _uiEvent = MutableSharedFlow<SettingsUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            loadSettings()
            observeNotificationFlags()
        }
    }

    fun onToggle(key: SettingsKeys) {
        viewModelScope.launch {
            toggleSettingUseCase(key)
            loadSettings()
        }
    }

    fun setBoolean(key: SettingsKeys, value: Boolean) {
        viewModelScope.launch {
            settingsRepository.setBoolean(key, value)
        }
    }

    fun getBoolean(key: SettingsKeys): Flow<Boolean> = settingsRepository.getBoolean(key)

    fun setInt(key: SettingsKeys, value: Int) {
        viewModelScope.launch {
            settingsRepository.setInt(key, value)
        }
    }

    fun getInt(key: SettingsKeys): Flow<Int> = settingsRepository.getInt(key)

    fun setFloat(key: SettingsKeys, value: Float) {
        viewModelScope.launch {
            settingsRepository.setFloat(key, value)
        }
    }

    fun getFloat(key: SettingsKeys): Flow<Float> = settingsRepository.getFloat(key)

    fun setString(key: SettingsKeys, value: String) {
        viewModelScope.launch {
            settingsRepository.setString(key, value)
        }
    }

    fun getString(key: SettingsKeys): Flow<String> = settingsRepository.getString(key)

    fun onItemClicked(key: SettingsKeys) {
        viewModelScope.launch {
            when (key) {
                SettingsKeys.LOOK_AND_FEEL -> _uiEvent.emit(
                    SettingsUiEvent.Navigate(LookAndFeelScreen)
                )

                SettingsKeys.CUSTOMISATION -> _uiEvent.emit(
                    SettingsUiEvent.Navigate(CustomisationScreen)
                )

                SettingsKeys.AUTO_UPDATE -> _uiEvent.emit(
                    SettingsUiEvent.Navigate(AutoUpdateScreen)
                )

                SettingsKeys.NOTIFICATION_SETTINGS -> _uiEvent.emit(
                    SettingsUiEvent.Navigate(NotificationScreen)
                )

                SettingsKeys.BEHAVIOR -> _uiEvent.emit(
                    SettingsUiEvent.Navigate(BehaviorScreen)
                )

                SettingsKeys.BACKUP_AND_RESTORE -> _uiEvent.emit(
                    SettingsUiEvent.Navigate(BackupAndRestoreScreen)
                )

                SettingsKeys.ABOUT -> _uiEvent.emit(
                    SettingsUiEvent.Navigate(AboutScreen)
                )

                SettingsKeys.CHANGELOGS -> _uiEvent.emit(
                    SettingsUiEvent.Navigate(ChangelogScreen)
                )

                SettingsKeys.REPORT -> _uiEvent.emit(
                    SettingsUiEvent.OpenUrl(UrlConst.URL_GITHUB_ISSUE_REPORT)
                )

                SettingsKeys.FEATURE_REQUEST -> _uiEvent.emit(
                    SettingsUiEvent.OpenUrl(UrlConst.URL_GITHUB_ISSUE_FEATURE_REQUEST)
                )

                SettingsKeys.LICENSE -> _uiEvent.emit(
                    SettingsUiEvent.OpenUrl(UrlConst.URL_GITHUB_REPO_LICENSE)
                )

                SettingsKeys.GITHUB -> _uiEvent.emit(
                    SettingsUiEvent.OpenUrl(UrlConst.URL_GITHUB_REPO)
                )

                SettingsKeys.LANGUAGE -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    _uiEvent.emit(
                        SettingsUiEvent.LaunchIntent(
                            intent = Intent(Settings.ACTION_APP_LOCALE_SETTINGS).apply {
                                data = "package:${context.packageName}".toUri()
                            }
                        )
                    )
                }

                SettingsKeys.DARK_THEME -> _uiEvent.emit(
                    SettingsUiEvent.Navigate(DarkThemeScreen)
                )

                SettingsKeys.RESET_APP_SETTINGS -> _uiEvent.emit(
                    SettingsUiEvent.ShowDialog(SettingsKeys.RESET_APP_SETTINGS)
                )

                SettingsKeys.BACKUP_APP_SETTINGS -> _uiEvent.emit(
                    SettingsUiEvent.RequestDocumentUriForBackup(BackupOption.SETTINGS_ONLY)
                )

                SettingsKeys.BACKUP_APP_DATABASE -> _uiEvent.emit(
                    SettingsUiEvent.RequestDocumentUriForBackup(BackupOption.DATABASE_ONLY)
                )

                SettingsKeys.BACKUP_APP_DATA -> _uiEvent.emit(
                    SettingsUiEvent.RequestDocumentUriForBackup(BackupOption.SETTINGS_AND_DATABASE)
                )

                SettingsKeys.RESTORE_APP_DATA -> _uiEvent.emit(
                    SettingsUiEvent.RequestDocumentUriForRestore
                )

                else -> {}
            }
        }
    }

    private val _notificationPermissionGranted = MutableStateFlow(
        isNotificationPermissionGranted(context)
    )
    val notificationPermissionGranted: StateFlow<Boolean> = _notificationPermissionGranted

    fun refreshNotificationPermissionState() {
        _notificationPermissionGranted.value = isNotificationPermissionGranted(context)
    }

    fun isItemChecked(key: SettingsKeys): Flow<Boolean> {
        return when (key) {
            SettingsKeys.ENABLE_NOTIFICATIONS -> {
                combine(
                    getBoolean(key),
                    notificationPermissionGranted
                ) { enabled, permissionGranted ->
                    enabled && permissionGranted
                }
            }

            else -> getBoolean(key)
        }
    }

    fun isItemEnabled(key: SettingsKeys): Flow<Boolean> {
        val notificationsEnabled = combine(
            getBoolean(SettingsKeys.ENABLE_NOTIFICATIONS),
            notificationPermissionGranted
        ) { notificationsEnabled, permissionGranted ->
            notificationsEnabled && permissionGranted
        }

        return when (key) {
            SettingsKeys.REMINDER_MARK_ATTENDANCE -> notificationsEnabled
            SettingsKeys.NOTIFY_MISSED_ATTENDANCE -> notificationsEnabled
            SettingsKeys.UPDATE_AVAILABLE_NOTIFICATION -> notificationsEnabled

            else -> flowOf(true)
        }
    }

    fun onBooleanItemClicked(key: SettingsKeys) {
        viewModelScope.launch {
            when (key) {
                SettingsKeys.ENABLE_NOTIFICATIONS -> {
                    if (isNotificationPermissionGranted(context)) onToggle(key)
                    else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        _uiEvent.emit(SettingsUiEvent.RequestPermission(android.Manifest.permission.POST_NOTIFICATIONS))
                    } else {
                        val intent = createAppNotificationSettingsIntent(context)
                        _uiEvent.emit(SettingsUiEvent.LaunchIntent(intent))
                    }
                }

                else -> onToggle(key)
            }
        }
    }

    private fun observeNotificationFlags() {
        viewModelScope.launch {
            combine(
                settingsRepository.getBoolean(SettingsKeys.ENABLE_NOTIFICATIONS),
                settingsRepository.getBoolean(SettingsKeys.REMINDER_MARK_ATTENDANCE),
                settingsRepository.getBoolean(SettingsKeys.NOTIFY_MISSED_ATTENDANCE),
                settingsRepository.getBoolean(SettingsKeys.UPDATE_AVAILABLE_NOTIFICATION),
            ) { enableAll, markReminder, missedNotify, updateAvailable ->
                NotificationState(
                    enableNotifications = enableAll,
                    markAttendance = markReminder,
                    missedAttendance = missedNotify,
                    updateAvailable = updateAvailable
                )
            }.collect { state ->

                if (!state.enableNotifications || !isNotificationPermissionGranted(context)) {
                    WorkScheduler.cancelAllNotificationWork(context)
                } else {

                    if (state.markAttendance) {
                        WorkScheduler.scheduleAttendanceReminder(context)
                    } else {
                        WorkScheduler.cancelNotificationWork(
                            context,
                            NotificationTags.REMINDER_TO_MARK_ATTENDANCE
                        )
                    }

                    if (state.missedAttendance) {
                        WorkScheduler.scheduleMissedAttendanceAlert(context)
                    } else {
                        WorkScheduler.cancelNotificationWork(
                            context,
                            NotificationTags.NOTIFY_WHEN_MISSED_ATTENDANCE
                        )
                    }

                    if (state.updateAvailable) {
                        WorkScheduler.scheduleDailyUpdateCheck(context)
                    } else {
                        WorkScheduler.cancelNotificationWork(
                            context,
                            NotificationTags.NOTIFY_WHEN_UPDATE_AVAILABLE
                        )
                    }
                }
            }
        }
    }
}
