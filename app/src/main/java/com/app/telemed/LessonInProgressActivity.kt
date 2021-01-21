package com.app.telemed

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.app.telemed.databinding.LessonInProgressFragmentBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class LessonInProgressActivity : AppCompatActivity() {

    private val IS_DIALOG_SHOWING: String = "IS_DIALOG_SHOWING"
    private val SHOW_VIDEO: String = "SHOW_VIDEO"
    private val ASK_QUESTION: String = "ASK_QUESTION"

    lateinit var binding: LessonInProgressFragmentBinding
    var isDialogShownig = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LessonInProgressFragmentBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setListeners()
        restoreState(savedInstanceState)
    }

    private fun restoreState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            binding.askQuestionButton.isActivated = it.getBoolean(ASK_QUESTION)
            binding.showVideoButton.isActivated = it.getBoolean(SHOW_VIDEO)
            if(it.getBoolean(IS_DIALOG_SHOWING, false))
                showDialog()
        } ?: run{
            binding.askQuestionButton.isActivated = !binding.askQuestionButton.isActivated
        }
        manageShowButton(binding.showVideoButton.isActivated)
        manageQuestionButton(binding.askQuestionButton.isActivated)
    }

    private fun setListeners() {
        with(binding){
            backButton.setOnClickListener { showDialog() }
            makeHeartBeat()
            showVideoButton.setOnClickListener {
                it.isActivated = !it.isActivated
                manageShowButton(it.isActivated)
            }
            askQuestionButton.setOnClickListener {
                it.isActivated = !it.isActivated
                manageQuestionButton(it.isActivated)
            }
            conversationGoesOnLayout.setVisible(false)
        }
    }

    private fun showDialog() {
        showAlertDialogButtonClicked(binding.root, {
                finish()
            }, {
                isDialogShownig = false
            })
        isDialogShownig = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(SHOW_VIDEO, binding.showVideoButton.isActivated)
        outState.putBoolean(ASK_QUESTION, binding.askQuestionButton.isActivated)
        outState.putBoolean(IS_DIALOG_SHOWING, isDialogShownig)
        super.onSaveInstanceState(outState)
    }

    private fun manageShowButton(activated: Boolean) {
        if(activated){
            binding.cameraText.text = getString(R.string.show_yourself)
        }else {
            binding.cameraText.text = getString(R.string.dont_show_yourself)
        }
    }

    private fun manageQuestionButton(b: Boolean) {
        if(b){
            binding.askText.text = if(BuildConfig.IS_REHUB)
                getString(R.string.question_to_instructor)
            else
                getString(R.string.question_to_coach)
        }else {
            binding.askText.text = if(BuildConfig.IS_REHUB)
                getString(R.string.question_from_instructor)
            else
                getString(R.string.question_from_coach)
        }
    }

    private fun makeHeartBeat() {
        lifecycleScope.launch {
            val rate = 60
            while (true){
                binding.heartIcon.animate().scaleX(0.5f).scaleY(0.5f)
                    .withEndAction {
                        binding.heartIcon.animate().scaleX(1f).scaleY(1f)
                            .start()
                    }
                    .start()
                binding.heartText.text = (rate + Random.nextInt(-5, 5)).toString()
                delay(1000)
            }
        }
    }
}