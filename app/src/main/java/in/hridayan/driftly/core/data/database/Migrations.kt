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

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            ALTER TABLE subjects 
            ADD COLUMN classType TEXT NOT NULL DEFAULT 'NONE'
            """.trimIndent()
        )
    }
}

val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE subjects ADD COLUMN daysOfWeek TEXT")
    }
}

val MIGRATION_6_7 = object : Migration(6, 7) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `subject_notes` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `subjectId` INTEGER NOT NULL,
                `date` TEXT NOT NULL,
                `note` TEXT NOT NULL,
                `createdAt` INTEGER NOT NULL DEFAULT 0
            )
            """.trimIndent()
        )
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_subject_notes_subjectId_date` ON `subject_notes` (`subjectId`, `date`)")
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_subject_notes_subjectId` ON `subject_notes` (`subjectId`)")
    }
}