package ru.fav.plantdiary.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity("users", indices = [
    Index(value = ["email"], unique = true)
])
data class UserEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "surname")
    val surname: String,
    @ColumnInfo(name = "password")
    val password: String
)