package com.sprtcoding.salita.dashboard_activity.activity.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sprtcoding.salita.database.AppDatabase
import com.sprtcoding.salita.database.contract.IAssessmentContract
import com.sprtcoding.salita.database.entities.AssessmentEntities
import com.sprtcoding.salita.database.repository.AssessmentRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AssessmentViewModel(application: Application): AndroidViewModel(application) {
    private val _assessmentSuccess = MutableLiveData<Result<List<AssessmentEntities?>>>()
    val assessmentSuccess: MutableLiveData<Result<List<AssessmentEntities?>>> get() = _assessmentSuccess

    private val _wrongAnswerCount = MutableLiveData<Result<Int>>()
    val wrongAnswerCount: MutableLiveData<Result<Int>> get() = _wrongAnswerCount

    private val assessmentRepository: AssessmentRepository

    init {
        val assessmentDao = AppDatabase.getDatabase(application).assessmentDao()
        assessmentRepository = AssessmentRepository(assessmentDao)
    }

    fun getAssessment(playerId: Int) {
        viewModelScope.launch {
            assessmentRepository.getAssessment(playerId, object : IAssessmentContract.Assessment {
                override fun onGetAssessment(assessmentList: List<AssessmentEntities?>) {
                    _assessmentSuccess.postValue(Result.success(assessmentList))
                }
            })
        }
    }

    fun getTotalQuestion(playerId: Int, desc: String): Deferred<Int?> {
        return viewModelScope.async {
            assessmentRepository.getTotalQuestion(playerId, desc)
        }
    }

    fun getWrongAnswerCount(playerId: Int, assessmentId: Int) {
        viewModelScope.launch {
            val wrongAnswerCount = assessmentRepository.getWrongCount(playerId, assessmentId)
            _wrongAnswerCount.postValue(Result.success(wrongAnswerCount))
        }
    }
}