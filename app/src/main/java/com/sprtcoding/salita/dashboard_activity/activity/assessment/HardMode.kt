package com.sprtcoding.salita.dashboard_activity.activity.assessment

import android.app.AlertDialog
import android.os.Bundle
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.sprtcoding.salita.R
import com.sprtcoding.salita.contract.IEasyContract
import com.sprtcoding.salita.dashboard_activity.activity.assessment.adapter.HardAdapter
import com.sprtcoding.salita.dashboard_activity.activity.assessment.viewmodel.AssessmentViewModel
import com.sprtcoding.salita.database.entities.AssessmentEntities
import com.sprtcoding.salita.database.entities.SelectedAnswerEntities
import com.sprtcoding.salita.database.entities.WrongEntities
import com.sprtcoding.salita.helpers.Utils
import kotlinx.coroutines.launch

class HardMode : AppCompatActivity() {
    private lateinit var vpHard: ViewPager
    private lateinit var btnNext: ImageButton
    private lateinit var btnPrev: ImageButton
    private lateinit var hardAdapter: HardAdapter
    private lateinit var assessmentViewModel: AssessmentViewModel
    private var name: String = ""
    private var id: Int = 0
    private var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hard_mode)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        init()
        afterInit()
    }

    private fun initViews() {
        vpHard = findViewById(R.id.vp_hard)
        btnNext = findViewById(R.id.btn_next)
        btnPrev = findViewById(R.id.btn_prev)
    }

    private fun init() {
        assessmentViewModel = ViewModelProvider(this)[AssessmentViewModel::class.java]

        name = intent.getStringExtra("name")!!

        assessmentViewModel.getPlayer(name)

        assessmentViewModel.playerEntities.observe(this) {
            it.onSuccess { player ->
                if (player != null) {
                    id = player.id!!
                } else {
                    // Handle the case where player is null, e.g., show an error message
                    Toast.makeText(this, "Player not found", Toast.LENGTH_SHORT).show()
                }
            }
        }

        hardAdapter = HardAdapter(this, assessmentViewModel,
            this,
            object : IEasyContract.Selected {
                override fun onSelect(level: Int, qNum: Int, selectedAnswer: Int, score: Int) {
                    val selectedAnswerEntities = SelectedAnswerEntities(
                        null,
                        id,
                        "hard",
                        level,
                        selectedAnswer,
                        qNum,
                        score
                    )
                    assessmentViewModel.addSelectedAnswer(selectedAnswerEntities)
                }
            },
            supportFragmentManager)
        vpHard.adapter = hardAdapter
    }

    private fun afterInit() {
        // Set initial visibility of btnPrev based on initial page position
        // Initially, hide the prev button since we're on the first page
        btnPrev.visibility = ViewPager.GONE

        assessmentViewModel.getSelectedAnswer(0)

        vpHard.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                // Show/hide prev button
                btnPrev.visibility = if (position == 0) ViewPager.GONE else ViewPager.VISIBLE

                // Change next button image based on last page
                if (position == hardAdapter.count - 1) {
                    btnNext.setImageResource(R.drawable.check)  // Change to check icon
                } else {
                    btnNext.setImageResource(R.drawable.next)   // Change back to next icon
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        btnNext.setOnClickListener {
            val nextItem = vpHard.currentItem + 1
            if (nextItem < hardAdapter.count) {
                vpHard.currentItem = nextItem
            } else {
                // Save score, user name, and ID to SharedPreferences
                //saveScoreToSharedPreferences(userName, userId, score)
                //finish()  // Finish the activity or perform any other action
                this.lifecycleScope.launch {
                    val scoreDeferred = assessmentViewModel.getScore().await()
                    val wrongAnswer = assessmentViewModel.getSelectedWrongAnswer().await()
                    score = scoreDeferred ?: 0
                    val wrong = wrongAnswer ?: emptyList()

                    if(score >= hardAdapter.count - 3) {
                        val (dialog, btnOk) = Utils.congratsDialog(layoutInflater, this@HardMode, "Congrats",
                            score, "Congrats. you got a highest score!")

                        btnOk.setOnClickListener {
                            saveAssessment(score, wrong, dialog)
                        }
                    } else {
                        val (dialog, btnOk) = Utils.congratsDialog(layoutInflater, this@HardMode, "Oh Noo..",
                            score, "Better luck next time. you got a lowest score!")

                        btnOk.setOnClickListener {
                            saveAssessment(score, wrong, dialog)
                        }
                    }
                }
            }
        }

        btnPrev.setOnClickListener {
            val prevItem = vpHard.currentItem - 1
            if (prevItem >= 0) {
                vpHard.currentItem = prevItem
            }
        }
    }

    private fun saveAssessment(score: Int, wrong: List<SelectedAnswerEntities>, dialog: AlertDialog) {
        val totalQ = hardAdapter.count
        val assessmentData = AssessmentEntities(
            null,
            id,
            "hard",
            totalQ,
            score
        )

        assessmentViewModel.setAssessment(assessmentData)
        assessmentViewModel.isAssessmentAdd.observe(this) { result ->
            result.onSuccess { assessmentId ->
                for(selected: SelectedAnswerEntities in wrong) {
                    val wrongData = WrongEntities(
                        null,
                        id,
                        assessmentId.toInt(),
                        "hard",
                        selected.assessmentLevel,
                        selected.selected
                    )

                    assessmentViewModel.setWrongAnswer(wrongData)
                }
                assessmentViewModel.clearSelected()
                Toast.makeText(this, "Assessment save successfully.", Toast.LENGTH_SHORT).show()
                dialog.show()
                finish()
            }
        }
    }
}