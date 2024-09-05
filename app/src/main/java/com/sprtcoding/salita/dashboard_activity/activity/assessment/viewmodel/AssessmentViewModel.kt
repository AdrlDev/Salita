package com.sprtcoding.salita.dashboard_activity.activity.assessment.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sprtcoding.salita.database.AppDatabase
import com.sprtcoding.salita.database.contract.IAssessmentContract
import com.sprtcoding.salita.database.entities.AssessmentEntities
import com.sprtcoding.salita.database.entities.PlayerEntities
import com.sprtcoding.salita.database.entities.SelectedAnswerEntities
import com.sprtcoding.salita.database.entities.WrongEntities
import com.sprtcoding.salita.database.repository.AssessmentRepository
import com.sprtcoding.salita.database.repository.PlayerRepository
import com.sprtcoding.salita.database.repository.SelectedAnswerRepository
import com.sprtcoding.salita.helpers.Utils
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AssessmentViewModel(application: Application): AndroidViewModel(application) {
    private val _addSuccess = MutableLiveData<Result<String>>()
    val addSuccess: LiveData<Result<String>> get() = _addSuccess

    private val _name = MutableLiveData<Result<String?>>()
    val name: LiveData<Result<String?>> get() = _name

    private val _assessmentSuccess = MutableLiveData<Result<Long>>()
    val isAssessmentAdd: LiveData<Result<Long>> get() = _assessmentSuccess

    private val _playerEntities = MutableLiveData<Result<PlayerEntities?>>()
    val playerEntities: LiveData<Result<PlayerEntities?>> get() = _playerEntities

    private val _selectedAnswerEntities = MutableLiveData<Result<SelectedAnswerEntities?>>()
    val selectedAnswerEntities: LiveData<Result<SelectedAnswerEntities?>> get() = _selectedAnswerEntities

    private val playerRepository: PlayerRepository
    private val selectedAnswerRepository: SelectedAnswerRepository
    private val assessmentRepository: AssessmentRepository

    init {
        val playerDao = AppDatabase.getDatabase(application).playerDao()
        playerRepository = PlayerRepository(playerDao)

        val selectedDao = AppDatabase.getDatabase(application).selectedDao()
        selectedAnswerRepository = SelectedAnswerRepository(selectedDao)

        val assessmentDao = AppDatabase.getDatabase(application).assessmentDao()
        assessmentRepository = AssessmentRepository(assessmentDao)
    }

    fun getName(context: Context) {
        _name.postValue(Result.success(Utils.getName(context)))
    }

    fun setAssessment(assessmentEntities: AssessmentEntities) {
        viewModelScope.launch {
            val id = assessmentRepository.addAssessment(assessmentEntities)
            _assessmentSuccess.postValue(Result.success(id))
        }
    }

    fun getSelectedWrongAnswer(): Deferred<List<SelectedAnswerEntities>?> {
        return viewModelScope.async {
            selectedAnswerRepository.getSelectedWrongAnswer()
        }
    }

    fun setWrongAnswer(wrongEntities: WrongEntities) {
        viewModelScope.launch {
            assessmentRepository.setWrongAnswer(wrongEntities)
        }
    }

    fun getPlayer(name: String) {
        playerRepository.getPlayer(name, object : IAssessmentContract.IPlayer {
            override fun onPlayer(playerEntities: PlayerEntities?) {
                _playerEntities.postValue(Result.success(playerEntities))
            }
        })
    }

    fun getScore(): Deferred<Int?> {
        return viewModelScope.async {
            selectedAnswerRepository.getScore()
        }
    }

    fun updateSelectedAnswerAndScore(position: Int, selectedAnswer: Int, score: Int) {
        viewModelScope.launch {
            selectedAnswerRepository.updateSelectedAnswerAndScore(position, selectedAnswer, score)
        }
    }

    fun isPlayerExists(playerName: String): Deferred<Boolean> {
        return viewModelScope.async {
            playerRepository.isPlayerExists(playerName)
        }
    }

    fun isAlreadyTakeAssessment(playerId: Int, desc: String): Deferred<Boolean?> {
        return viewModelScope.async {
            assessmentRepository.isAlreadyTakeAssessment(playerId, desc)
        }
    }

    fun isAlreadySelected(position: Int): Deferred<Boolean> {
        return viewModelScope.async {
            selectedAnswerRepository.isAlreadySelected(position)
        }
    }

    fun addPlayer(playerEntities: PlayerEntities) {
        viewModelScope.launch {
            try {
                playerRepository.addPlayer(playerEntities, object : IAssessmentContract {
                    override fun onPlayerAdd(success: Boolean, message: String) {
                        if(success) {
                            _addSuccess.postValue(Result.success(message))
                        } else {
                            _addSuccess.postValue(Result.failure(Exception(message)))
                        }
                    }
                })
            } catch (e: Exception) {
                _addSuccess.postValue(Result.failure(e))
            }
        }
    }

    fun getSelectedAnswer(position: Int) {
        viewModelScope.launch {
            try {
                selectedAnswerRepository.getSelectedAnswer(position, object : IAssessmentContract.SelectedAnswer {
                    override fun onSelect(selectedAnswerEntities: SelectedAnswerEntities?) {
                        _selectedAnswerEntities.postValue(Result.success(selectedAnswerEntities))
                    }
                })
            } catch (err: Exception) {
                _selectedAnswerEntities.postValue(Result.failure(Exception(err)))
            }
        }
    }

    fun addSelectedAnswer(selectedAnswerEntities: SelectedAnswerEntities) {
        viewModelScope.launch {
            try {
                selectedAnswerRepository.addSelectedAnswer(selectedAnswerEntities, object : IAssessmentContract.ISelected {
                    override fun onSelectedAnswerAdd(success: Boolean) {

                    }
                })
            } catch (e: Exception) {
                _addSuccess.postValue(Result.failure(e))
            }
        }
    }

    fun clearSelected() {
        viewModelScope.launch {
            selectedAnswerRepository.clearSelected()
        }
    }
}