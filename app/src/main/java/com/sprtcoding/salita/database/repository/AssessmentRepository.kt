package com.sprtcoding.salita.database.repository

import com.sprtcoding.salita.database.contract.IAssessmentContract
import com.sprtcoding.salita.database.dao.AssessmentDao
import com.sprtcoding.salita.database.entities.AssessmentEntities
import com.sprtcoding.salita.database.entities.WrongEntities

class AssessmentRepository(private val assessmentDao: AssessmentDao) {
    suspend fun addAssessment(assessmentEntities: AssessmentEntities): Long {
        return assessmentDao.insertAssessment(assessmentEntities);
    }

    suspend fun getAssessment(playerId: Int, iAssessment: IAssessmentContract.Assessment) {
        try {
            val assessment = assessmentDao.getAssessment(playerId)
            iAssessment.onGetAssessment(assessment)
        } catch (err: Exception) {
            iAssessment.onGetAssessment(emptyList())
        }
    }

    suspend fun getTotalQuestion(playerId: Int, desc: String): Int? {
        return assessmentDao.getTotalQuestion(playerId, desc)
    }

    suspend fun getWrongCount(playerId: Int, assessmentId: Int): Int {
        return assessmentDao.getWrongAnswerCount(playerId, assessmentId)
    }

    suspend fun isAlreadyTakeAssessment(playerId: Int, desc: String): Boolean? {
        return assessmentDao.checkIfAlreadyTakeAssessment(playerId, desc)
    }

    suspend fun setWrongAnswer(wrongEntities: WrongEntities) {
        assessmentDao.insertWrongAnswer(wrongEntities)
    }
}