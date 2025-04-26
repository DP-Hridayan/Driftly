package `in`.hridayan.driftly.core.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import `in`.hridayan.driftly.core.data.model.AttendanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(attendance: AttendanceEntity)

    @Update
    suspend fun updateAttendance(attendance: AttendanceEntity)

    @Delete
    suspend fun deleteAttendance(attendance: AttendanceEntity)

    @Query("SELECT * FROM attendance")
    suspend fun getAllAttendances(): List<AttendanceEntity>

    @Query("SELECT * FROM attendance WHERE subjectId = :subjectId")
    fun getAttendanceForSubjectFlow(subjectId: Int): Flow<List<AttendanceEntity>>

    @Query("DELETE FROM attendance WHERE subjectId = :subjectId AND date = :date")
    suspend fun deleteBySubjectAndDate(subjectId: Int, date: String)

    @Query("SELECT COUNT(*) FROM attendance WHERE subjectId = :subjectId AND status = 'PRESENT'")
    fun getPresentCount(subjectId: Int): Flow<Int>

    @Query("SELECT COUNT(*) FROM attendance WHERE subjectId = :subjectId AND status = 'ABSENT'")
    fun getAbsentCount(subjectId: Int): Flow<Int>

    @Query("SELECT COUNT(*) FROM attendance WHERE subjectId = :subjectId")
    fun getTotalCount(subjectId: Int): Flow<Int>

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
}

