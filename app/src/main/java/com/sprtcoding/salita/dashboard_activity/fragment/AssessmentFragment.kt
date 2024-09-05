package com.sprtcoding.salita.dashboard_activity.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.sprtcoding.salita.R
import com.sprtcoding.salita.dashboard_activity.activity.AssessmentHistory
import com.sprtcoding.salita.dashboard_activity.activity.assessment.EasyMode
import com.sprtcoding.salita.dashboard_activity.activity.assessment.HardMode
import com.sprtcoding.salita.dashboard_activity.activity.assessment.ModerateMode
import com.sprtcoding.salita.dashboard_activity.activity.assessment.viewmodel.AssessmentViewModel
import com.sprtcoding.salita.database.entities.PlayerEntities
import com.sprtcoding.salita.helpers.Utils
import com.sprtcoding.salita.helpers.Utils.showExitConfirmationDialog
import kotlinx.coroutines.launch

class AssessmentFragment : Fragment() {
    private lateinit var view: View
    private lateinit var btnEasy: LinearLayout
    private lateinit var btnModerate: LinearLayout
    private lateinit var btnHard: LinearLayout
    private lateinit var btnHistory: MaterialButton
    private lateinit var btnExit: MaterialButton
    private lateinit var assessmentViewModel: AssessmentViewModel
    private var id = 0
    private var saveName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_assessment, container, false)

        initViews()
        init()
        afterInit()

        return view
    }

    override fun onResume() {
        super.onResume()
        saveName = Utils.getName(requireContext())
    }

    private fun initViews() {
        btnEasy = view.findViewById(R.id.btn_easy)
        btnModerate = view.findViewById(R.id.btn_moderate)
        btnHard = view.findViewById(R.id.btn_hard)
        btnHistory = view.findViewById(R.id.btn_history)
        btnExit = view.findViewById(R.id.btn_exit)
    }

    private fun init() {
        assessmentViewModel = ViewModelProvider(this)[AssessmentViewModel::class.java]

        saveName = Utils.getName(requireContext())
    }

    private fun afterInit() {
        val name = saveName ?: ""
        assessmentViewModel.getPlayer(name)

        assessmentViewModel.playerEntities.observe(viewLifecycleOwner) {
            it.onSuccess { player ->
                if (player != null) {
                    val playerId = player.id
                    id = playerId ?: 0
                    if(id == 0) {
                        btnHistory.visibility = View.GONE
                    } else {
                        btnHistory.visibility = View.VISIBLE
                    }
                }
            }
        }

        btnHistory.setOnClickListener {
            startActivity(Intent(requireContext(), AssessmentHistory::class.java)
                .putExtra("playerId", id))
        }

        btnHard.setOnClickListener {
            if(saveName != null) {
                this.lifecycleScope.launch {
                    val isAlreadyTakeAssessment = assessmentViewModel.isAlreadyTakeAssessment(id, "hard").await()
                    if(isAlreadyTakeAssessment!!) {
                        val (dialog, buttons) = Utils.alertDialog(layoutInflater, requireContext(), "Warning",
                            "You have already take this assessment. Do you want to retake it?")
                        val btnRetake = buttons.first
                        val btnClose = buttons.second

                        btnRetake.setOnClickListener {
                            dialog.dismiss()
                            startActivity(Intent(requireContext(), HardMode::class.java)
                                .putExtra("name", name))
                        }

                        btnClose.setOnClickListener {
                            dialog.dismiss()
                        }
                    } else {
                        startActivity(Intent(requireContext(), HardMode::class.java)
                            .putExtra("name", name))
                    }
                }

            } else {
                val (dialog, buttons) = Utils.customAlertDialog(layoutInflater, requireContext())

                val buttonCancel = buttons.first
                val buttonOk = buttons.second

                buttonCancel.setOnClickListener {
                    // Handle cancel button click
                    dialog.dismiss()
                }

                buttonOk.setOnClickListener {
                    val pName = dialog.findViewById<EditText>(R.id.et_name)?.text.toString()
                    checkPlayer(pName, dialog)
                }
            }
        }

        btnModerate.setOnClickListener {
            if(saveName != null) {
                this.lifecycleScope.launch {
                    val isAlreadyTakeAssessment = assessmentViewModel.isAlreadyTakeAssessment(id, "moderate").await()
                    if(isAlreadyTakeAssessment!!) {
                        val (dialog, buttons) = Utils.alertDialog(layoutInflater, requireContext(), "Warning",
                            "You have already take this assessment. Do you want to retake it?")
                        val btnRetake = buttons.first
                        val btnClose = buttons.second

                        btnRetake.setOnClickListener {
                            dialog.dismiss()
                            startActivity(Intent(requireContext(), ModerateMode::class.java)
                                .putExtra("name", name))
                        }

                        btnClose.setOnClickListener {
                            dialog.dismiss()
                        }
                    } else {
                        startActivity(Intent(requireContext(), ModerateMode::class.java)
                            .putExtra("name", name))
                    }
                }

            } else {
                val (dialog, buttons) = Utils.customAlertDialog(layoutInflater, requireContext())

                val buttonCancel = buttons.first
                val buttonOk = buttons.second

                buttonCancel.setOnClickListener {
                    // Handle cancel button click
                    dialog.dismiss()
                }

                buttonOk.setOnClickListener {
                    val pName = dialog.findViewById<EditText>(R.id.et_name)?.text.toString()
                    checkPlayer(pName, dialog)
                }
            }
        }

        btnEasy.setOnClickListener {
            if(name != "") {
                this.lifecycleScope.launch {
                    val isAlreadyTakeAssessment = assessmentViewModel.isAlreadyTakeAssessment(id, "easy").await()
                    if(isAlreadyTakeAssessment!!) {
                        val (dialog, buttons) = Utils.alertDialog(layoutInflater, requireContext(), "Warning",
                            "You have already take this assessment. Do you want to retake it?")
                        val btnRetake = buttons.first
                        val btnClose = buttons.second

                        btnRetake.setOnClickListener {
                            dialog.dismiss()
                            startActivity(Intent(requireContext(), EasyMode::class.java)
                                .putExtra("name", name))
                        }

                        btnClose.setOnClickListener {
                            dialog.dismiss()
                        }
                    } else {
                        startActivity(Intent(requireContext(), EasyMode::class.java)
                            .putExtra("name", name))
                    }
                }

            } else {
                val (dialog, buttons) = Utils.customAlertDialog(layoutInflater, requireContext())

                val buttonCancel = buttons.first
                val buttonOk = buttons.second

                buttonCancel.setOnClickListener {
                    // Handle cancel button click
                    dialog.dismiss()
                }

                buttonOk.setOnClickListener {
                    val pName = dialog.findViewById<EditText>(R.id.et_name)?.text.toString()
                    checkPlayer(pName, dialog)
                }
            }
        }

        btnExit.setOnClickListener {
            showExitConfirmationDialog(requireContext())
        }
    }

    private fun checkPlayer(playerName: String, dialog: AlertDialog) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val exists = assessmentViewModel.isPlayerExists(playerName).await()
                if (exists) {
                    Toast.makeText(requireContext(), "Player exists", Toast.LENGTH_SHORT).show()
                } else {
                    if(playerName.isNotEmpty()) {
                        val playerEntities = PlayerEntities(
                            null,
                            playerName,
                            null
                        )
                        assessmentViewModel.addPlayer(playerEntities)
                        context?.let { Utils.saveName(it, playerName) }
                        assessmentViewModel.addSuccess.observe(viewLifecycleOwner) { message ->
                            // Handle OK button click event
                            message.onSuccess {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                startActivity(Intent(requireContext(), EasyMode::class.java)
                                    .putExtra("name", playerName))
                                dialog.dismiss()
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), "Please enter your name", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error checking player", Toast.LENGTH_SHORT).show()
            }
        }
    }

}