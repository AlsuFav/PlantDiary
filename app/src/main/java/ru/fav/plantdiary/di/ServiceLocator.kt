package ru.fav.plantdiary.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import ru.fav.plantdiary.data.db.PlantDiaryDatabase
import ru.fav.plantdiary.data.db.migrations.Migration_1_2
import ru.fav.plantdiary.data.db.repositoty.PlantRepository
import ru.fav.plantdiary.data.db.repositoty.UserRepository
import ru.fav.plantdiary.utils.Constants

object ServiceLocator {
    private const val DATABASE_NAME = "PlantDiaryDB"

    private var dbInstance: PlantDiaryDatabase? = null
    private var userRepository: UserRepository? = null
    private var plantRepository: PlantRepository? = null
    private var sharedPreferences: SharedPreferences? = null

    private fun initDatabase(ctx: Context) {
        dbInstance = Room.databaseBuilder(ctx, PlantDiaryDatabase::class.java, DATABASE_NAME)
            .addMigrations(Migration_1_2())
            .build()
    }


    fun initDataLayerDependencies(ctx: Context) {
        if (dbInstance == null) {
            initDatabase(ctx)
            dbInstance?.let {
                userRepository = UserRepository(
                    userDao = it.userDao,
                    ioDispatcher = Dispatchers.IO
                )
                plantRepository = PlantRepository(
                    plantDao = it.plantDao,
                    ioDispatcher = Dispatchers.IO
                )
            }
        }

        if (sharedPreferences == null) {
            sharedPreferences = ctx.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
        }
    }

    fun getUserRepository() : UserRepository = userRepository ?: throw IllegalStateException("User repository not initialized")

    fun getPlantRepository() : PlantRepository = plantRepository ?: throw IllegalStateException("Plant repository not initialized")

    fun getSharedPreferences(): SharedPreferences =
        sharedPreferences ?: throw IllegalStateException("SharedPreferences not initialized")
}