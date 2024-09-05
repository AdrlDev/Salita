package com.sprtcoding.salita.contract

interface IEasyContract {
    interface View {
        fun onScoreUpdated(score: Int)
    }
    interface Selected {
        fun onSelect(level: Int, qNum: Int, selectedAnswer: Int, score: Int)
    }
}