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
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.hard.HQ1
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.hard.HQ10
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.hard.HQ11
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.hard.HQ12
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.hard.HQ13
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.hard.HQ14
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.hard.HQ15
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.hard.HQ2
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.hard.HQ3
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.hard.HQ4
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.hard.HQ5
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.hard.HQ6
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.hard.HQ7
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.hard.HQ8
import com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.hard.HQ9
import com.sprtcoding.salita.dashboard_activity.activity.assessment.viewmodel.AssessmentViewModel
import kotlinx.coroutines.launch

class HardAdapter(private val context: Context,
                  private val assessmentViewModel: AssessmentViewModel,
                  private val lifecycleOwner: LifecycleOwner,
                  private val iSelected: IEasyContract.Selected,
                  fragmentManager: FragmentManager
) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT), IButtons {

    private val fragmentsQuestion = arrayOf(
        HQ1(this),
        HQ2(this),
        HQ3(this),
        HQ4(this),
        HQ5(this),
        HQ6(this),
        HQ7(this),
        HQ8(this),
        HQ9(this),
        HQ10(this),
        HQ11(this),
        HQ12(this),
        HQ13(this),
        HQ14(this),
        HQ15(this)
    )

    private val correctAnswer = arrayOf(
        3, 4, 3, 1, 1, 1, 4, 3, 3, 1, 4, 2, 1, 2, 3
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