package com.example.data.user.repository

import com.example.data.databese.dao.UserDao
import com.example.data.databese.entity.UserEntity
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val dao: UserDao
) {
    suspend fun getUser() : Result<UserEntity?> = runCatching {
        dao.getUser()
    }.onFailure {
        // TODO: Add log.
    }
}