package com.kompody.etnetera.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kompody.etnetera.data.database.Tables

@Entity(
    tableName = Tables.RESULTS
)
class ResultDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val place: Int,
    val duration: Float,
    val date: Long,
    val type: String
)