package com.ansengarie.githubuser_sub3.data.local.room

import androidx.room.*
import com.ansengarie.githubuser_sub3.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM GithubUser where favorite = 1")
    fun getFavoritedUser(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)


    @Query("SELECT EXISTS(SELECT * FROM GithubUser WHERE login = :username AND favorite = 1)")
    suspend fun isUserFavorited(username: String): Boolean
}