package ru.fav.plantdiary.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.fav.plantdiary.data.db.entities.PlantEntity

@Dao
interface PlantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePlant(plant: PlantEntity)

    @Query("SELECT * FROM plants WHERE user_id = :userId")
    suspend fun getPlantsByUserId(userId: String): List<PlantEntity>

    @Query("SELECT * FROM plants WHERE id = :plantId LIMIT 1")
    suspend fun getPlantById(plantId: String): PlantEntity?

    @Delete
    suspend fun deletePlant(wish: PlantEntity)
}