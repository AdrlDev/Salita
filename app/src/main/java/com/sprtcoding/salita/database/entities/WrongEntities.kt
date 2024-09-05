package com.sprtcoding.salita.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "wrong_answers",
    foreignKeys = [
        ForeignKey(
            entity = PlayerEntities::class, // Reference to the entity
            parentColumns = ["id"], // Column in the parent entity
            childColumns = ["playerId"], // Column in the current entity
            onDelete = ForeignKey.CASCADE // Optional, defines what happens on delete (e.g., CASCADE, SET NULL, etc.)
        ),
        ForeignKey(
            entity = AssessmentEntities::class, // Reference to the entity
            parentColumns = ["id"], // Column in the parent entity
            childColumns = ["assessmentId"], // Column in the current entity
            onDelete = ForeignKey.CASCADE // Optional, defines what happens on delete (e.g., CASCADE, SET NULL, etc.)
        )
    ])
data class WrongEntities(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val playerId: Int,
    val assessmentId: Int,
    val assessmentDesc: String,
    val level: Int,
    val selectedAnswer: Int,
)
