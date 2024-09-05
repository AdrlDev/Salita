package com.sprtcoding.salita.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sprtcoding.salita.database.entities.PlayerEntities

@Dao
interface PlayerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(playerEntities: PlayerEntities)

    @Query("SELECT COUNT(*) > 0 FROM player WHERE playerName = :name")
    suspend fun isPlayerExists(name: String): Boolean

    @Query("SELECT * FROM player WHERE playerName = :name")
    suspend fun getPlayer(name: String): PlayerEntities

    @Query("DELETE FROM player")
    suspend fun clearPlayer()

}