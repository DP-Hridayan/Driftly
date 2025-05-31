package `in`.hridayan.driftly.settings.data.local.repository

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import `in`.hridayan.driftly.core.domain.repository.AttendanceRepository
import `in`.hridayan.driftly.core.domain.repository.SubjectRepository
import `in`.hridayan.driftly.settings.domain.model.BackupData
import `in`.hridayan.driftly.settings.domain.model.BackupOption
import `in`.hridayan.driftly.settings.domain.repository.BackupAndRestoreRepository
import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class BackupAndRestoreRepositoryImpl @Inject constructor(
    private val json: Json,
    private val attendanceRepository: AttendanceRepository,
    private val subjectRepository: SubjectRepository,
    private val settingsRepository: SettingsRepository,
    @ApplicationContext private val context: Context
) : BackupAndRestoreRepository {

    override suspend fun backupDataToFile(uri: Uri, option: BackupOption): Boolean =
        withContext(Dispatchers.IO) {
            try {
                val backupData = getBackupData(option)
                context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                    val jsonData = json.encodeToString(BackupData.serializer(), backupData)
                    outputStream.write(jsonData.toByteArray())
                }
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

    override suspend fun restoreDataFromFile(uri: Uri): Boolean = withContext(Dispatchers.IO) {
        try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val jsonString = inputStream?.bufferedReader()?.readText() ?: return@withContext false
            val restoredData = json.decodeFromString(BackupData.serializer(), jsonString)

            saveRestoredData(restoredData)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private suspend fun getBackupData(option: BackupOption): BackupData {
        val settings =
            if (option == BackupOption.SETTINGS_ONLY || option == BackupOption.SETTINGS_AND_DATABASE)
                getSettingsMap() else null
        val attendance =
            if (option == BackupOption.DATABASE_ONLY || option == BackupOption.SETTINGS_AND_DATABASE)
                attendanceRepository.getAllAttendancesOnce() else null
        val subjects =
            if (option == BackupOption.DATABASE_ONLY || option == BackupOption.SETTINGS_AND_DATABASE)
                subjectRepository.getAllSubjectsOnce() else null
        return BackupData(settings, attendance, subjects)
    }

    private fun getSettingsMap(): Map<String, String?> {
        val prefs = settingsRepository.getAllDefaultSettings()
        return prefs.mapValues { it.value?.toString() }
    }

    private suspend fun saveRestoredData(data: BackupData) {
        data.attendance?.let {
            attendanceRepository.deleteAllAttendances()
            attendanceRepository.insertAllAttendances(it)
        }
        data.subjects?.let {
            subjectRepository.deleteAllSubjects()
            subjectRepository.insertAllSubjects(it)
        }
        data.settings?.let { restoreSettings(it) }
    }

    private fun restoreSettings(settings: Map<String, String?>) {
        val prefs = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        val editor = prefs.edit().clear()
        settings.forEach { (key, value) ->
            if (value == null) {
                editor.remove(key)
            } else {
                editor.putString(key, value)
            }
        }
        editor.apply()
    }
}