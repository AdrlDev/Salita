package com.sprtcoding.salita.dashboard_activity.activity.assessment.adapter

import android.app.AlertDialog
import android.content.Context
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.sprtcoding.salita.R
import com.sprtcoding.salita.contract.IEasyContract
import com.sprtcoding.salita.dashboard_activity.activity.assessment.contract.IButtons
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.moderate.MQuestion1
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.moderate.MQuestion10
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.moderate.MQuestion2
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.moderate.MQuestion3
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.moderate.MQuestion4
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.moderate.MQuestion5
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.moderate.MQuestion6
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.moderate.MQuestion7
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.moderate.MQuestion8
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.moderate.MQuestion9
import com.sprtcoding.salita.dashboard_activity.activity.assessment.viewmodel.AssessmentViewModel
import kotlinx.coroutines.launch

class ModerateAdapter(private val context: Context,
                      private val assessmentViewModel: AssessmentViewModel,
                      private val lifecycleOwner: LifecycleOwner,
                      private val iSelected: IEasyContract.Selected,
                      fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT), IButtons {

    private val fragmentsQuestion = arrayOf(
        MQuestion1(this),
        MQuestion2(this),
        MQuestion3(this),
        MQuestion4(this),
        MQuestion5(this),
        MQuestion6(this),
        MQuestion7(this),
        MQuestion8(this),
        MQuestion9(this),
        MQuestion10(this)
    )

    private val correctAnswer = arrayOf(
        3, 2, 2, 3, 1, 4, 3, 4, 2, 1
    )

    override fun getCount(): Int = fragmentsQuestion.size

    override fun getItem(position: Int): Fragment {
        return fragmentsQuestion[position]
    }

    override fun onClicked(btnId: Int, questionNo: Int, buttonList: List<RelativeLayout?>) {
        when (btnId) {
            R.id.btn_selection_1 -> {
                btnClicked(1, buttonList, questionNo)
            }
            R.id.btn_selection_2 -> {
                btnClicked(2, buttonList, questionNo)
            }
            R.id.btn_selection_3 -> {
                btnClicked(3, buttonList, questionNo)
            }
            R.id.btn_selection_4 -> {
                btnClicked(4, buttonList, questionNo)
            }
        }
    }

    private fun btnClicked(selected: Int, buttonList: List<RelativeLayout?>, questionNo: Int) {
        lifecycleOwner.lifecycleScope.launch {
            val isSelected = assessmentViewModel.isAlreadySelected(questionNo).await()
            if(isSelected) {
                showDialog(questionNo, selected) { success ->
                    if(success) {
                        resetButtonColors(buttonList[0]!!, buttonList[1]!!, buttonList[2]!!, buttonList[3]!!)
                        changeButtonColors(buttonList[0]!!, buttonList[1]!!, buttonList[2]!!, buttonList[3]!!, questionNo, selected)
                    }
                }
            } else {
                resetButtonColors(buttonList[0]!!, buttonList[1]!!, buttonList[2]!!, buttonList[3]!!)
                changeButtonColors(buttonList[0]!!, buttonList[1]!!, buttonList[2]!!, buttonList[3]!!, questionNo, selected)
                if(checkAnswers(selected, questionNo)) {
                    iSelected.onSelect(questionNo, questionNo, selected, 1)
                } else {
                    iSelected.onSelect(questionNo, questionNo, selected, 0)
                }
            }
        }
    }

    private fun resetButtonColors(btnSelection1: RelativeLayout, btnSelection2: RelativeLayout, btnSelection3: RelativeLayout, btnSelection4: RelativeLayout) {
        btnSelection1.backgroundTintList = ContextCompat.getColorStateList(context, R.color._dark_blue_500)
        btnSelection2.backgroundTintList = ContextCompat.getColorStateList(context, R.color._dark_blue_500)
        btnSelection3.backgroundTintList = ContextCompat.getColorStateList(context, R.color._dark_blue_500)
        btnSelection4.backgroundTintList = ContextCompat.getColorStateList(context, R.color._dark_blue_500)
    }

    private fun changeButtonColors(btnSelection1: RelativeLayout, btnSelection2: RelativeLayout, btnSelection3: RelativeLayout, btnSelection4: RelativeLayout, position: Int, selectedAnswer: Int) {
        if (checkAnswers(selectedAnswer, position)) {
            when (selectedAnswer) {
                1 -> btnSelection1.backgroundTintList = ContextCompat.getColorStateList(context, R.color._blue)
                2 -> btnSelection2.backgroundTintList = ContextCompat.getColorStateList(context, R.color._blue)
                3 -> btnSelection3.backgroundTintList = ContextCompat.getColorStateList(context, R.color._blue)
                4 -> btnSelection4.backgroundTintList = ContextCompat.getColorStateList(context, R.color._blue)
            }
        } else {
            when (selectedAnswer) {
                1 -> btnSelection1.backgroundTintList = ContextCompat.getColorStateList(context, R.color._red)
                2 -> btnSelection2.backgroundTintList = ContextCompat.getColorStateList(context, R.color._red)
                3 -> btnSelection3.backgroundTintList = ContextCompat.getColorStateList(context, R.color._red)
                4 -> btnSelection4.backgroundTintList = ContextCompat.getColorStateList(context, R.color._red)
            }
        }
    }

    private fun checkAnswers(selectedAnswer: Int, answerPosition: Int) : Boolean {
        return correctAnswer[answerPosition - 1] == selectedAnswer
    }

    private fun showDialog(position: Int, selectedAnswer: Int, callback: (Boolean) -> Unit) {
        AlertDialog.Builder(context).apply {
            setTitle("Change Answer")
            setMessage("Do you really want to change answer to Option $selectedAnswer?")
            setPositiveButton("Yes") { _, _ ->
                if(checkAnswers(selectedAnswer, position)) {
                    assessmentViewModel.updateSelectedAnswerAndScore(position, selectedAnswer, 1)
                } else {
                    assessmentViewModel.updateSelectedAnswerAndScore(position, selectedAnswer, 0)
                }
                callback(true)
            }
            setNegativeButton("No", null)
            show()
        }
    }

}