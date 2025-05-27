package `in`.hridayan.driftly.core.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `in`.hridayan.driftly.core.data.repository.AttendanceRepositoryImpl
import `in`.hridayan.driftly.core.data.repository.SubjectRepositoryImpl
import `in`.hridayan.driftly.core.domain.repository.AttendanceRepository
import `in`.hridayan.driftly.core.domain.repository.SubjectRepository
import `in`.hridayan.driftly.settings.data.remote.api.GitHubApi
import `in`.hridayan.driftly.settings.data.remote.repository.UpdateRepositoryImpl
import `in`.hridayan.driftly.settings.domain.repository.UpdateRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindSubjectRepository(
        subjectRepositoryImpl: SubjectRepositoryImpl
    ): SubjectRepository

    @Binds
    @Singleton
    abstract fun bindAttendanceRepository(
        attendanceRepositoryImpl: AttendanceRepositoryImpl
    ): AttendanceRepository
}
