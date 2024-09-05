package com.sprtcoding.salita.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sprtcoding.salita.database.entities.AssessmentEntities
import com.sprtcoding.salita.database.entities.WrongEntities

@Dao
interface AssessmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssessment(assessmentEntities: AssessmentEntities): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWrongAnswer(wrongEntities: WrongEntities)

    @Query("SELECT COUNT(*) FROM wrong_answers WHERE playerId = :playerId AND assessmentId = :assessmentId")
    suspend fun getWrongAnswerCount(playerId: Int, assessmentId: Int): Int

    @Query("SELECT totalQuestions FROM assessment WHERE playerId = :playerId AND assessmentDesc = :desc")
    suspend fun getTotalQuestion(playerId: Int, desc: String): Int?

    @Query("SELECT * FROM assessment WHERE playerId = :playerId")
    suspend fun getAssessment(playerId: Int): List<AssessmentEntities?>

    @Query("SELECT COUNT(*) > 0 FROM assessment WHERE playerId = :playerId AND assessmentDesc = :desc")
    suspend fun checkIfAlreadyTakeAssessment(playerId: Int, desc: String): Boolean?

    @Query("DELETE FROM assessment")
    suspend fun clearAssessment()
}