package `in`.hridayan.driftly.core.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_SUBJECT_TABLE_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE subjects ADD COLUMN savedMonth INTEGER")
        db.execSQL("ALTER TABLE subjects ADD COLUMN savedYear INTEGER")
    }
}

val MIGRATION_SUBJECT_TABLE_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE subjects ADD COLUMN room TEXT")
    }
}