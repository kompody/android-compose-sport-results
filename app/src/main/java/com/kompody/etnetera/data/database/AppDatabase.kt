package com.kompody.etnetera.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kompody.etnetera.data.database.dao.ResultDao
import com.kompody.etnetera.data.database.model.ResultDbModel

@Database(
    entities = [
        ResultDbModel::class
    ],
    version = 1,
    exportSchema = false,
    views = []
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun resultDao(): ResultDao
}