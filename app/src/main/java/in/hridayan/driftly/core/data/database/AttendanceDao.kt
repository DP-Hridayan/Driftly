package `in`.hridayan.driftly.core.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import `in`.hridayan.driftly.core.data.model.AttendanceEntity
import `in`.hridayan.driftly.core.domain.model.AttendanceStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(attendance: AttendanceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAttendances(attendances: List<AttendanceEntity>)

    @Update
    suspend fun updateAttendance(attendance: AttendanceEntity)

    @Delete
    suspend fun deleteAttendance(attendance: AttendanceEntity)

    @Query("DELETE FROM attendance")
    suspend fun deleteAllAttendances()

    @Query("DELETE FROM attendance WHERE subjectId = :subjectId")
    suspend fun deleteAllAttendanceForSubject(subjectId: Int)

    @Query("SELECT * FROM attendance")
    suspend fun getAllAttendancesOnce(): List<AttendanceEntity>

    @Query("SELECT * FROM attendance WHERE subjectId = :subjectId")
    fun getAttendanceForSubjectFlow(subjectId: Int): Flow<List<AttendanceEntity>>

    @Query("DELETE FROM attendance WHERE subjectId = :subjectId AND date = :date")
    suspend fun deleteBySubjectAndDate(subjectId: Int, date: String)

    @Query("SELECT COUNT(*) FROM attendance WHERE status = :status")
    fun getTotalCountByStatus(status: AttendanceStatus): Flow<Int>

    @Query("SELECT COUNT(*) FROM attendance WHERE subjectId = :subjectId AND status = :status")
    fun getCountBySubjectAndStatus(subjectId: Int, status: AttendanceStatus): Flow<Int>

    @Query(
        """
        SELECT COUNT(*) FROM attendance 
        WHERE subjectId = :subjectId 
        AND status = 'PRESENT' 
        AND strftime('%Y', date) = printf('%04d', :year)
        AND strftime('%m', date) = printf('%02d', :month)
    """
    )
    fun getPresentCountForMonth(subjectId: Int, year: Int, month: Int): Flow<Int>

    @Query(
        """
        SELECT COUNT(*) FROM attendance 
        WHERE subjectId = :subjectId 
        AND status = 'ABSENT' 
        AND strftime('%Y', date) = printf('%04d', :year)
        AND strftime('%m', date) = printf('%02d', :month)
    """
    )
    fun getAbsentCountForMonth(subjectId: Int, year: Int, month: Int): Flow<Int>

    @Query(
        """
        SELECT COUNT(*) FROM attendance 
        WHERE subjectId = :subjectId 
        AND strftime('%Y', date) = printf('%04d', :year)
        AND strftime('%m', date) = printf('%02d', :month)
    """
    )
    fun getTotalCountForMonth(subjectId: Int, year: Int, month: Int): Flow<Int>

    @Query(
        """
    SELECT EXISTS (
        SELECT 1
        FROM subjects s
        LEFT JOIN attendance a ON s.id = a.subjectId AND a.date = :date
        WHERE a.subjectId IS NULL OR a.status = :status
    )
    """
    )
    suspend fun hasUnmarkedAttendanceForDate(
        date: String,
        status: AttendanceStatus = AttendanceStatus.UNMARKED
    ): Boolean

}

