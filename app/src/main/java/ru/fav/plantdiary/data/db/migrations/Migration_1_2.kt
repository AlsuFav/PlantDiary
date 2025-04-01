package ru.fav.plantdiary.data.db.migrations

import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.fav.plantdiary.data.db.PlantDiaryDatabase

class Migration_1_2: Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        try {
            db.execSQL("ALTER TABLE plants ADD COLUMN care_instructions TEXT")
        }catch (ex: Exception) {
            Log.e(PlantDiaryDatabase.DB_LOG_KEY, "Error while 1_2 migration: ${ex.message}")
        }
    }

}