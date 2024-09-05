package com.sprtcoding.salita.database.contract

import com.sprtcoding.salita.database.entities.AssessmentEntities
import com.sprtcoding.salita.database.entities.PlayerEntities
import com.sprtcoding.salita.database.entities.SelectedAnswerEntities

interface IAssessmentContract {
    fun onPlayerAdd(success: Boolean, message: String)
    interface ISelected {
        fun onSelectedAnswerAdd(success: Boolean)
    }
    interface IPlayer {
        fun onPlayer(playerEntities: PlayerEntities?)
    }
    interface SelectedAnswer {
        fun onSelect(selectedAnswerEntities: SelectedAnswerEntities?)
    }
    interface Assessment {
        fun onGetAssessment(assessmentList: List<AssessmentEntities?>)
    }
}