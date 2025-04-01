package ru.fav.plantdiary.data.db.repositoty

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.fav.plantdiary.data.db.dao.PlantDao
import ru.fav.plantdiary.data.db.entities.PlantEntity

class PlantRepository (
    private val plantDao: PlantDao,
    private val ioDispatcher: CoroutineDispatcher,
    ) {
    suspend fun savePlant(plant: PlantEntity) {
        return withContext(ioDispatcher) {
            plantDao.savePlant(plant)
        }
    }

    suspend fun getPlantsByUserId(id: String): List<PlantEntity> {
        return withContext(ioDispatcher) {
            plantDao.getPlantsByUserId(id)
        }
    }

    suspend fun getPlantById(id: String): PlantEntity? {
        return withContext(ioDispatcher) {
            plantDao.getPlantById(id)
        }
    }

    suspend fun deletePlant(plant: PlantEntity) {
        return withContext(ioDispatcher) {
            plantDao.deletePlant(plant)
        }
    }
}