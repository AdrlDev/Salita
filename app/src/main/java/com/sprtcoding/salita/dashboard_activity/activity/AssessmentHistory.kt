package com.sprtcoding.salita.dashboard_activity.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.sprtcoding.salita.R
import com.sprtcoding.salita.dashboard_activity.activity.viewmodel.AssessmentViewModel
import com.sprtcoding.salita.database.entities.AssessmentEntities
import com.sprtcoding.salita.helpers.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class AssessmentHistory : AppCompatActivity() {
    private lateinit var btnBack: ImageButton
    private lateinit var barChartScoreEasy: BarChart
    private lateinit var barChartScoreModerate: BarChart
    private lateinit var barChartScoreHard: BarChart
    private lateinit var assessmentViewModel: AssessmentViewModel
    private var playerId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_assessment_history)
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
        barChartScoreEasy = findViewById(R.id.bar_score_easy)
        barChartScoreModerate = findViewById(R.id.bar_score_moderate)
        barChartScoreHard = findViewById(R.id.bar_score_hard)
        btnBack = findViewById(R.id.btn_back)
    }

    private fun init() {
        assessmentViewModel = ViewModelProvider(this)[AssessmentViewModel::class.java]

        playerId = intent.getIntExtra("playerId", 0)
    }

    private fun afterInit() {

        // Trigger data loading for each level
        assessmentViewModel.getAssessment(playerId)

        assessmentViewModel.assessmentSuccess.observe(this) { result ->
            result.onSuccess { assessmentList ->
                if (assessmentList.isNotEmpty()) {
                    // Group assessments by their level
                    val easyAssessments = assessmentList.filter { it?.assessmentDesc?.equals("easy", true) == true }
                    val moderateAssessments = assessmentList.filter { it?.assessmentDesc?.equals("moderate", true) == true }
                    val hardAssessments = assessmentList.filter { it?.assessmentDesc?.equals("hard", true) == true }

                    // Show charts for each level
                    setupBarChart(easyAssessments, "Easy", barChartScoreEasy, playerId)
                    setupBarChart(moderateAssessments, "Moderate", barChartScoreModerate, playerId)
                    setupBarChart(hardAssessments, "Hard", barChartScoreHard, playerId)
                }
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupBarChart(assessments: List<AssessmentEntities?>, level: String, barChart: BarChart, playerId: Int) {
        if (assessments.isNotEmpty()) {

            lifecycleScope.launch {
                val totalQuestions = getTotalQuestions(level)

                val entries = assessments.map { assessment ->
                    val id = assessment?.id?.toFloat() ?: 0f
                    val score = assessment?.score?.toFloat() ?: 0f
                    val scorePercent = Utils.calculatePercentage(score.toInt(), totalQuestions)
                    BarEntry(id, scorePercent.toFloat())
                }

                val desc = level.replaceFirstChar { it.uppercase() }

                val dataSet = BarDataSet(entries, "$desc Scores")
                val colors = entries.map { entry ->
                    when {
                        entry.y >= 90 -> ContextCompat.getColor(this@AssessmentHistory, R.color._green) // High percentage
                        entry.y >= 75 -> ContextCompat.getColor(this@AssessmentHistory, R.color._yellow) // Medium percentage
                        else -> ContextCompat.getColor(this@AssessmentHistory, R.color._red) // Low percentage
                    }
                }
                dataSet.colors = colors

                val barData = BarData(dataSet)
                barChart.description = null
                barChart.data = barData
                barChart.invalidate()

                // Set the click listener for the bar chart
                barChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                    @SuppressLint("SetTextI18n")
                    override fun onValueSelected(e: Entry?, h: Highlight?) {
                        e?.let { score ->
                            val selectedScore = score.y
                            val assessmentId = score.x.toInt()

                            assessmentViewModel.getWrongAnswerCount(playerId, assessmentId)

                            showPieDialog(desc, selectedScore, totalQuestions)

                        }
                    }

                    override fun onNothingSelected() {
                        // Handle case when no bar is selected, if needed
                    }
                })
            }
        }
    }

    private suspend fun getTotalQuestions(level: String): Int {
        return withContext(Dispatchers.IO) {
            when (level.lowercase()) {
                "easy" -> assessmentViewModel.getTotalQuestion(playerId, "easy").await() ?: 0
                "moderate" -> assessmentViewModel.getTotalQuestion(playerId, "moderate").await() ?: 0
                else -> assessmentViewModel.getTotalQuestion(playerId, "hard").await() ?: 0
            }
        }
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    private fun showPieDialog(desc: String?, selectedScore: Float?, totalQuestions: Int) {
        val (dialog, btnOk) = Utils.showScoreDialog(layoutInflater, this@AssessmentHistory
            , "$desc Scores")

        val pieChart = dialog.findViewById<PieChart>(R.id.chart_score_pie)
        val scoreTv = dialog.findViewById<TextView>(R.id.tv_score)

        scoreTv.text = String.format("%.2f%%", selectedScore)

        assessmentViewModel.wrongAnswerCount.observe(this@AssessmentHistory) { result ->
            result.onSuccess { wrong ->
                // Create entries for the PieChart
                val correctAnswersCount = totalQuestions - wrong
                val pieEntries = listOf(
                    PieEntry(correctAnswersCount.toFloat(), "Correct Answers"),
                    PieEntry(wrong.toFloat(), "Wrong Answers")
                )

                // Create a dataset
                val pieDataSet = PieDataSet(pieEntries, "Assessment Results")
                // Set colors for the dataset
                val color = listOf(
                    ContextCompat.getColor(this@AssessmentHistory, R.color._green),  // Correct answers color
                    ContextCompat.getColor(this@AssessmentHistory, R.color._red)     // Wrong answers color
                )
                pieDataSet.colors = color

                val pieData = PieData(pieDataSet)

                pieChart.data = pieData
                pieChart.invalidate() // Refresh the chart

                // Hide labels
                pieChart.setDrawEntryLabels(false)

                pieChart.description.text = "Assessment Results"
                pieChart.isDrawHoleEnabled = false
            }
        }

        btnOk.setOnClickListener {
            dialog.dismiss()
        }
    }
}