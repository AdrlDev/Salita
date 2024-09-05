package com.sprtcoding.salita.database.repository

import android.util.Log
import com.sprtcoding.salita.database.contract.IAssessmentContract
import com.sprtcoding.salita.database.dao.SelectedDao
import com.sprtcoding.salita.database.entities.SelectedAnswerEntities
import com.sprtcoding.salita.database.entities.WrongEntities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SelectedAnswerRepository(private val selectedDao: SelectedDao) {
    suspend fun addSelectedAnswer(selectedAnswerEntities: SelectedAnswerEntities, iContract: IAssessmentContract.ISelected) {
        try {
            selectedDao.insertSelectedAnswer(selectedAnswerEntities)
            iContract.onSelectedAnswerAdd(true)
        } catch (err: Exception) {
            iContract.onSelectedAnswerAdd(false)
        }
    }

    suspend fun getSelectedWrongAnswer(): List<SelectedAnswerEntities>? {
        return selectedDao.getSelectedWrongAnswer()
    }

    suspend fun getScore(): Int? {
        return selectedDao.getTotalScore()
    }

    suspend fun updateSelectedAnswerAndScore(position: Int, selectedAnswer: Int, score: Int) {
        selectedDao.updateSelectedAnswerAndScore(position, selectedAnswer, score)
    }

    suspend fun isAlreadySelected(position: Int): Boolean {
        return withContext(Dispatchers.IO) {
            selectedDao.isAlreadySelected(position)
        }
    }

    suspend fun getSelectedAnswer(position: Int, iSelectedAnswer: IAssessmentContract.SelectedAnswer) {
        try {
            val selected = selectedDao.getSelectedAnswer(position)
            iSelectedAnswer.onSelect(selected)
        } catch (err: Exception) {
            iSelectedAnswer.onSelect(null)
        }
    }

    suspend fun clearSelected() {
        try {
            // Clear local database after syncing
            withContext(Dispatchers.IO) { selectedDao.clearSelectedAnswer() }
        } catch (e: Exception) {
            // Handle exceptions
            Log.e("MedsRepository", "Error syncing with FireStore", e)
        }
    }
}