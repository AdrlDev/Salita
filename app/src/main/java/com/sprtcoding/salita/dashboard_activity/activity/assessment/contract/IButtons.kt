package com.sprtcoding.salita.dashboard_activity.activity.assessment.contract

import android.widget.RelativeLayout

interface IButtons {
    fun onClicked(btnId: Int, questionNo: Int, buttonList: List<RelativeLayout?>)
}