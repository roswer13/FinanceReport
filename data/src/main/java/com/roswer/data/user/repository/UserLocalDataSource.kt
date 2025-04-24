package com.roswer.data.user.repository

import com.roswer.data.databese.dao.UserDao
import com.roswer.data.databese.entity.UserEntity
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