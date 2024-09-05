package com.sprtcoding.salita.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sprtcoding.salita.database.dao.AssessmentDao
import com.sprtcoding.salita.database.dao.PlayerDao
import com.sprtcoding.salita.database.dao.SelectedDao
import com.sprtcoding.salita.database.entities.PlayerEntities
import com.sprtcoding.salita.database.entities.SelectedAnswerEntities
import com.sprtcoding.salita.database.entities.AssessmentEntities
import com.sprtcoding.salita.database.entities.WrongEntities

@Database(entities = [PlayerEntities::class, SelectedAnswerEntities::class, AssessmentEntities::class,
                     WrongEntities::class],
    version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun assessmentDao(): AssessmentDao
    abstract fun selectedDao(): SelectedDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                // Create the database using only the database name
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "salita_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}