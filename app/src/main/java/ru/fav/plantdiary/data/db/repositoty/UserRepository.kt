package ru.fav.plantdiary.data.db.repositoty

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.fav.plantdiary.data.db.dao.UserDao
import ru.fav.plantdiary.data.db.entities.UserEntity

class UserRepository (
    private val userDao: UserDao,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun saveUserData(user: UserEntity) {
        return withContext(ioDispatcher) {
            userDao.saveUserData(user = user)
        }
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return withContext(ioDispatcher) {
            userDao.getUserByEmail(email)
        }
    }

    suspend fun getUserById(id: String): UserEntity? {
        return withContext(ioDispatcher) {
            userDao.getUserById(id)
        }
    }

    suspend fun authenticateUser(email: String, password: String): UserEntity? {
        return withContext(ioDispatcher) {
            userDao.authenticateUser(email, password)
        }
    }

    suspend fun deleteUserById(id: String) {
        return withContext(ioDispatcher) {
            userDao.deleteUserById(id)
        }
    }

    suspend fun updateUser(user: UserEntity) {
        return withContext(ioDispatcher) {
            userDao.updateUser(user)
        }
    }
}