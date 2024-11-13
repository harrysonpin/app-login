package com.example.test.Repository

import com.example.test.DAO.UserDao
import com.example.test.Model.User

class UserRepository(private val userDao: UserDao) {

    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }

    suspend fun actualizar(user: User) {
        userDao.updateUser(user)
    }

    suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }

    suspend fun getUserByEmailAndPassword(email: String, password: String): User? {
        return userDao.getUserByEmailAndPassword(email, password)
    }

    suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    suspend fun getCurrentUserId(): Int? {
    return userDao.getCurrentUserId()
}
}