package ru.fav.plantdiary.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.fav.plantdiary.data.db.converters.PhotoConverter
import ru.fav.plantdiary.data.db.dao.PlantDao
import ru.fav.plantdiary.data.db.dao.UserDao
import ru.fav.plantdiary.data.db.entities.PlantEntity
import ru.fav.plantdiary.data.db.entities.UserEntity

@Database(
    entities = [UserEntity::class, PlantEntity::class],
    version = 2
)
@TypeConverters(PhotoConverter::class)

abstract class PlantDiaryDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val plantDao: PlantDao


    companion object {
        const val DB_LOG_KEY = "InceptionDB"
    }
}