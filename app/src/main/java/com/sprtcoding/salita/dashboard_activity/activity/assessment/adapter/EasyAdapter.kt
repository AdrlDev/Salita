package com.sprtcoding.salita.dashboard_activity.activity.assessment.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.PagerAdapter
import com.sprtcoding.salita.R
import com.sprtcoding.salita.contract.IEasyContract
import com.sprtcoding.salita.dashboard_activity.activity.assessment.viewmodel.AssessmentViewModel
import kotlinx.coroutines.launch

class EasyAdapter(private val context: Context,
                  private val assessmentViewModel: AssessmentViewModel,
                  private val lifecycleOwner: LifecycleOwner,
                  private val iSelected: IEasyContract.Selected) :
    PagerAdapter()  {

    private val questions = arrayOf(
        R.drawable.letter_g,
        "B",
        "5",
        R.drawable.letter_u,
        R.drawable.question_mark_symbol
    )

    private val answer1List = arrayOf(
        "F","A","L","G"
    )

    private val answer2List = intArrayOf(
        R.drawable.number_2,
        R.drawable.letter_m,
        R.drawable.letter_c,
        R.drawable.letter_b
    )

    private val answer3List = intArrayOf(
        R.drawable.number_5,
        R.drawable.letter_f,
        R.drawable.letter_y,
        R.drawable.letter_e
    )

    private val answer4List = arrayOf(
        "V","P","U","D"
    )

    private val answer5List = arrayOf(
        "%","?","#","*"
    )

    private val answers = arrayOf(
        4, 4, 1, 3, 2
    )

    override fun getCount(): Int {
        return questions.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }

    @SuppressLint("KotlinNullnessAnnotation", "MissingInflatedId")
    @NonNull
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = layoutInflater.inflate(R.layout.easy_layout, container, false)

        val imgQuestion = v.findViewById<ImageView>(R.id.img_question)
        val tvQuestion = v.findViewById<TextView>(R.id.tv_question)
        val tvAnswerSelection1 = v.findViewById<TextView>(R.id.tv_answer_1)
        val tvAnswerSelection2 = v.findViewById<TextView>(R.id.tv_answer_2)
        val tvAnswerSelection3 = v.findViewById<TextView>(R.id.tv_answer_3)
        val tvAnswerSelection4 = v.findViewById<TextView>(R.id.tv_answer_4)
        val imgAnswerSelection1 = v.findViewById<ImageView>(R.id.img_answer_1)
        val imgAnswerSelection2 = v.findViewById<ImageView>(R.id.img_answer_2)
        val imgAnswerSelection3 = v.findViewById<ImageView>(R.id.img_answer_3)
        val imgAnswerSelection4 = v.findViewById<ImageView>(R.id.img_answer_4)
        val btnSelection1 = v.findViewById<RelativeLayout>(R.id.btn_selection_1)
        val btnSelection2 = v.findViewById<RelativeLayout>(R.id.btn_selection_2)
        val btnSelection3 = v.findViewById<RelativeLayout>(R.id.btn_selection_3)
        val btnSelection4 = v.findViewById<RelativeLayout>(R.id.btn_selection_4)

        val question = questions[position]

        if (question is Int) {
            // If the question is an image (drawable resource)
            imgQuestion.setImageResource(question)
            imgQuestion.visibility = View.VISIBLE
            tvQuestion.visibility = View.GONE
        } else if (question is String) {
            // If the question is a string
            tvQuestion.text = question
            tvQuestion.visibility = View.VISIBLE
            imgQuestion.visibility = View.GONE
        }

        assessmentViewModel.selectedAnswerEntities.observe(lifecycleOwner) {
            it.onSuccess {
                selected ->
                if(selected != null) {
                    resetButtonColors(btnSelection1, btnSelection2, btnSelection3, btnSelection4)  // Reset before setting new color
                    changeButtonColors(btnSelection1, btnSelection2, btnSelection3, btnSelection4, position, selected.selected)
                }
            }
        }

        when (position) {
            0 -> {
                checkButtons(true, tvAnswerSelection1, tvAnswerSelection2,
                    tvAnswerSelection3, tvAnswerSelection4, imgAnswerSelection1,
                    imgAnswerSelection2, imgAnswerSelection3, imgAnswerSelection4)
                buttonListener(btnSelection1, btnSelection2, btnSelection3, btnSelection4, position)
                tvAnswerSelection1.text = answer1List[0]
                tvAnswerSelection2.text = answer1List[1]
                tvAnswerSelection3.text = answer1List[2]
                tvAnswerSelection4.text = answer1List[3]
            }
            1 -> {
                checkButtons(false, tvAnswerSelection1, tvAnswerSelection2,
                    tvAnswerSelection3, tvAnswerSelection4, imgAnswerSelection1,
                    imgAnswerSelection2, imgAnswerSelection3, imgAnswerSelection4)
                buttonListener(btnSelection1, btnSelection2, btnSelection3, btnSelection4, position)
                imgAnswerSelection1.setImageResource(answer2List[0])
                imgAnswerSelection2.setImageResource(answer2List[1])
                imgAnswerSelection3.setImageResource(answer2List[2])
                imgAnswerSelection4.setImageResource(answer2List[3])
            }
            2 -> {
                checkButtons(false, tvAnswerSelection1, tvAnswerSelection2,
                    tvAnswerSelection3, tvAnswerSelection4, imgAnswerSelection1,
                    imgAnswerSelection2, imgAnswerSelection3, imgAnswerSelection4)
                buttonListener(btnSelection1, btnSelection2, btnSelection3, btnSelection4, position)
                imgAnswerSelection1.setImageResource(answer3List[0])
                imgAnswerSelection2.setImageResource(answer3List[1])
                imgAnswerSelection3.setImageResource(answer3List[2])
                imgAnswerSelection4.setImageResource(answer3List[3])
            }
            3 -> {
                checkButtons(true, tvAnswerSelection1, tvAnswerSelection2,
                    tvAnswerSelection3, tvAnswerSelection4, imgAnswerSelection1,
                    imgAnswerSelection2, imgAnswerSelection3, imgAnswerSelection4)
                buttonListener(btnSelection1, btnSelection2, btnSelection3, btnSelection4, position)
                tvAnswerSelection1.text = answer4List[0]
                tvAnswerSelection2.text = answer4List[1]
                tvAnswerSelection3.text = answer4List[2]
                tvAnswerSelection4.text = answer4List[3]
            }
            4 -> {
                checkButtons(true, tvAnswerSelection1, tvAnswerSelection2,
                    tvAnswerSelection3, tvAnswerSelection4, imgAnswerSelection1,
                    imgAnswerSelection2, imgAnswerSelection3, imgAnswerSelection4)
                buttonListener(btnSelection1, btnSelection2, btnSelection3, btnSelection4, position)
                tvAnswerSelection1.text = answer5List[0]
                tvAnswerSelection2.text = answer5List[1]
                tvAnswerSelection3.text = answer5List[2]
                tvAnswerSelection4.text = answer5List[3]
            }
        }

        container.addView(v)

        return v
    }

    private fun changeButtonColors(btnSelection1: RelativeLayout?, btnSelection2: RelativeLayout?, btnSelection3: RelativeLayout?, btnSelection4: RelativeLayout?, position: Int, selectedAnswer: Int) {
        if (checkAnswers(selectedAnswer, position)) {
            when (selectedAnswer) {
                1 -> btnSelection1?.backgroundTintList = ContextCompat.getColorStateList(context, R.color._blue)
                2 -> btnSelection2?.backgroundTintList = ContextCompat.getColorStateList(context, R.color._blue)
                3 -> btnSelection3?.backgroundTintList = ContextCompat.getColorStateList(context, R.color._blue)
                4 -> btnSelection4?.backgroundTintList = ContextCompat.getColorStateList(context, R.color._blue)
            }
        } else {
            when (selectedAnswer) {
                1 -> btnSelection1?.backgroundTintList = ContextCompat.getColorStateList(context, R.color._red)
                2 -> btnSelection2?.backgroundTintList = ContextCompat.getColorStateList(context, R.color._red)
                3 -> btnSelection3?.backgroundTintList = ContextCompat.getColorStateList(context, R.color._red)
                4 -> btnSelection4?.backgroundTintList = ContextCompat.getColorStateList(context, R.color._red)
            }
        }
    }

    private fun resetButtonColors(btnSelection1: RelativeLayout, btnSelection2: RelativeLayout, btnSelection3: RelativeLayout, btnSelection4: RelativeLayout) {
        btnSelection1.backgroundTintList = ContextCompat.getColorStateList(context, R.color._dark_blue_500)
        btnSelection2.backgroundTintList = ContextCompat.getColorStateList(context, R.color._dark_blue_500)
        btnSelection3.backgroundTintList = ContextCompat.getColorStateList(context, R.color._dark_blue_500)
        btnSelection4.backgroundTintList = ContextCompat.getColorStateList(context, R.color._dark_blue_500)
    }

    private fun buttonListener(btnSelection1: RelativeLayout?, btnSelection2: RelativeLayout?, btnSelection3: RelativeLayout?, btnSelection4: RelativeLayout?, position: Int) {
        btnSelection1?.setOnClickListener {
            lifecycleOwner.lifecycleScope.launch {
                val isSelected = assessmentViewModel.isAlreadySelected(position).await()
                if(isSelected) {
                    showDialog(position, 1) { success ->
                        if(success) {
                            resetButtonColors(btnSelection1, btnSelection2!!, btnSelection3!!, btnSelection4!!)  // Reset before setting new color
                            changeButtonColors(btnSelection1, btnSelection2, btnSelection3, btnSelection4, position, 1)
                        }
                    }
                } else {
                    resetButtonColors(btnSelection1, btnSelection2!!, btnSelection3!!, btnSelection4!!)  // Reset before setting new color
                    changeButtonColors(btnSelection1, btnSelection2, btnSelection3, btnSelection4, position, 1)
                    if(checkAnswers(1, position)) {
                        iSelected.onSelect(position, position, 1, 1)
                    } else {
                        iSelected.onSelect(position, position, 1, 0)
                    }
                }
            }
        }

        btnSelection2?.setOnClickListener {
            lifecycleOwner.lifecycleScope.launch {
                val isSelected = assessmentViewModel.isAlreadySelected(position).await()
                if(isSelected) {
                    showDialog(position, 2) { success ->
                        if(success) {
                            resetButtonColors(btnSelection1!!, btnSelection2, btnSelection3!!, btnSelection4!!)  // Reset before setting new color
                            changeButtonColors(btnSelection1, btnSelection2, btnSelection3, btnSelection4, position, 2)
                        }
                    }
                } else {
                    resetButtonColors(btnSelection1!!, btnSelection2, btnSelection3!!, btnSelection4!!)  // Reset before setting new color
                    changeButtonColors(btnSelection1, btnSelection2, btnSelection3, btnSelection4, position, 2)
                    if(checkAnswers(2, position)) {
                        iSelected.onSelect(position, position, 2, 1)
                    } else {
                        iSelected.onSelect(position, position, 2, 0)
                    }
                }
            }
        }

        btnSelection3?.setOnClickListener {
            lifecycleOwner.lifecycleScope.launch {
                val isSelected = assessmentViewModel.isAlreadySelected(position).await()
                if(isSelected) {
                    showDialog(position, 3) { success ->
                        if(success) {
                            resetButtonColors(btnSelection1!!, btnSelection2!!, btnSelection3, btnSelection4!!)  // Reset before setting new color
                            changeButtonColors(btnSelection1, btnSelection2, btnSelection3, btnSelection4, position, 3)
                        }
                    }
                } else {
                    resetButtonColors(btnSelection1!!, btnSelection2!!, btnSelection3, btnSelection4!!)  // Reset before setting new color
                    changeButtonColors(btnSelection1, btnSelection2, btnSelection3, btnSelection4, position, 3)
                    if(checkAnswers(3, position)) {
                        iSelected.onSelect(position, position, 3, 1)
                    } else {
                        iSelected.onSelect(position, position, 3, 0)
                    }
                }
            }
        }

        btnSelection4?.setOnClickListener {
            lifecycleOwner.lifecycleScope.launch {
                val isSelected = assessmentViewModel.isAlreadySelected(position).await()
                if(isSelected) {
                    showDialog(position, 4) { success ->
                        if(success) {
                            resetButtonColors(btnSelection1!!, btnSelection2!!, btnSelection3!!, btnSelection4)  // Reset before setting new color
                            changeButtonColors(btnSelection1, btnSelection2, btnSelection3, btnSelection4, position, 4)
                        }
                    }
                } else {
                    resetButtonColors(btnSelection1!!, btnSelection2!!, btnSelection3!!, btnSelection4)  // Reset before setting new color
                    changeButtonColors(btnSelection1, btnSelection2, btnSelection3, btnSelection4, position, 4)
                    if(checkAnswers(4, position)) {
                        iSelected.onSelect(position, position, 4, 1)
                    } else {
                        iSelected.onSelect(position, position, 4, 0)
                    }
                }
            }
        }
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

    private fun checkAnswers(selectedAnswer: Int, answerPosition: Int) : Boolean {
        return answers[answerPosition] == selectedAnswer
    }

    private fun checkButtons(
        isText: Boolean,
        tvAnswerSelection1: TextView,
        tvAnswerSelection2: TextView,
        tvAnswerSelection3: TextView,
        tvAnswerSelection4: TextView,
        imgAnswerSelection1: ImageView,
        imgAnswerSelection2: ImageView,
        imgAnswerSelection3: ImageView,
        imgAnswerSelection4: ImageView
    ) {
        if(isText) {
            tvAnswerSelection1.visibility = View.VISIBLE
            tvAnswerSelection2.visibility = View.VISIBLE
            tvAnswerSelection3.visibility = View.VISIBLE
            tvAnswerSelection4.visibility = View.VISIBLE
            imgAnswerSelection1.visibility = View.GONE
            imgAnswerSelection2.visibility = View.GONE
            imgAnswerSelection3.visibility = View.GONE
            imgAnswerSelection4.visibility = View.GONE
        } else {
            tvAnswerSelection1.visibility = View.GONE
            tvAnswerSelection2.visibility = View.GONE
            tvAnswerSelection3.visibility = View.GONE
            tvAnswerSelection4.visibility = View.GONE
            imgAnswerSelection1.visibility = View.VISIBLE
            imgAnswerSelection2.visibility = View.VISIBLE
            imgAnswerSelection3.visibility = View.VISIBLE
            imgAnswerSelection4.visibility = View.VISIBLE
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}