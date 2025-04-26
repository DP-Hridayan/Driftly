package `in`.hridayan.driftly.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import `in`.hridayan.driftly.core.data.database.AttendanceDao
import `in`.hridayan.driftly.core.data.database.SubjectDao
import `in`.hridayan.driftly.core.data.database.SubjectDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SubjectDatabase =
        Room.databaseBuilder(
            context,
            SubjectDatabase::class.java,
            "attendance_app_db"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideSubjectDao(db: SubjectDatabase): SubjectDao = db.subjectDao()

    @Provides
    fun provideAttendanceDao(db: SubjectDatabase): AttendanceDao = db.attendanceDao()
}
