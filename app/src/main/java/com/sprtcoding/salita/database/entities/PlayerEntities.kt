package com.sprtcoding.salita.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player")
data class PlayerEntities(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val playerName: String,
    val score: Int?
)
