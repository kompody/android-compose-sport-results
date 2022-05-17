package com.kompody.etnetera.data.database.dao

import androidx.room.*
import com.kompody.etnetera.data.database.Tables
import com.kompody.etnetera.data.database.model.ResultDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(model: ResultDbModel): Long

    @Update
    suspend fun updateResult(model: ResultDbModel)

    @Query("SELECT * FROM ${Tables.RESULTS}")
    suspend fun getResults(): List<ResultDbModel>

    @Query("SELECT * FROM ${Tables.RESULTS}")
    fun flowResults(): Flow<List<ResultDbModel>>

    @Query("DELETE FROM ${Tables.RESULTS} WHERE id = :id")
    suspend fun deleteResult(id: Long)
}