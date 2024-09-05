package com.sprtcoding.salita.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "assessment",
    foreignKeys = [
        ForeignKey(
            entity = PlayerEntities::class, // Reference to the entity
            parentColumns = ["id"], // Column in the parent entity
            childColumns = ["playerId"], // Column in the current entity
            onDelete = ForeignKey.CASCADE // Optional, defines what happens on delete (e.g., CASCADE, SET NULL, etc.)
        )
    ])
data class AssessmentEntities(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val playerId: Int,
    val assessmentDesc: String,
    val totalQuestions: Int,
    val score: Int
)
