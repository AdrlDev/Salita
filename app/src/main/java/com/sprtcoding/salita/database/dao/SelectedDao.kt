package com.sprtcoding.salita.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sprtcoding.salita.database.entities.SelectedAnswerEntities

@Dao
interface SelectedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSelectedAnswer(selectedAnswerEntities: SelectedAnswerEntities)

    @Query("SELECT * FROM selected_answer WHERE assessmentLevel = :position")
    suspend fun getSelectedAnswer(position: Int): SelectedAnswerEntities?

    @Query("SELECT * FROM selected_answer WHERE score = 0")
    suspend fun getSelectedWrongAnswer(): List<SelectedAnswerEntities>?

    @Query("SELECT COUNT(*) > 0 FROM selected_answer WHERE assessmentLevel = :position")
    suspend fun isAlreadySelected(position: Int): Boolean

    @Query("UPDATE selected_answer SET selected = :selectedAnswer, score = :score WHERE assessmentLevel = :position")
    suspend fun updateSelectedAnswerAndScore(position: Int, selectedAnswer: Int, score: Int)

    @Query("SELECT SUM(score) FROM selected_answer")
    suspend fun getTotalScore(): Int?

    @Query("DELETE FROM selected_answer")
    suspend fun clearSelectedAnswer()
}