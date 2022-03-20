package com.example.geoquiz

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

const val EXTRA_ANSWER_SHOW = "com.example.geoquiz.answer_show"
const val EXTRA_CHEAT_INDEX = "com.example.geoquiz.this_index"
const val EXTRA_NEW_NUMBER_OF_HINTS = "com.example.geoquiz.new_number_of_hints"
private const val EXTRA_ANSWER_IS_TRUE = "com.example.geoquiz.answer_is_true"
private const val EXTRA_CURRENT_INDEX = "com.example.geoquiz.current_index"
private const val EXTRA_NUMBER_OF_HINTS = "com.example.geoquiz.number_of_hints"

class CheatActivity : AppCompatActivity() {

    private lateinit var answerTextView: TextView
    private lateinit var numberOfHintsTextView: TextView
    private lateinit var showAnswerButton: Button

    private var answerIsTrue = false
    private var cheatIndex = 0
    private var numberOfHints = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        cheatIndex = intent.getIntExtra(EXTRA_CURRENT_INDEX, 0)
        numberOfHints = intent.getIntExtra(EXTRA_NUMBER_OF_HINTS, 0)
        answerTextView = findViewById(R.id.answer_text_view)
        numberOfHintsTextView = findViewById(R.id.number_of_hints_text_view)
        numberOfHintsTextView.text = "There are still hints left: ${3 - numberOfHints}"
        showAnswerButton = findViewById(R.id.show_answer_button)
        showAnswerButton.setOnClickListener {
            numberOfHints += 1
            checkNumbersOfHints()
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerText)
            setAnswerShowResult(true, cheatIndex, numberOfHints)
        }
        checkNumbersOfHints()
    }

    private fun setAnswerShowResult(isAnswerShow: Boolean, cheatIndex: Int, numberOfHints: Int) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOW, isAnswerShow)
            putExtra(EXTRA_CHEAT_INDEX, cheatIndex)
            putExtra(EXTRA_NEW_NUMBER_OF_HINTS, numberOfHints)
        }
        setResult(Activity.RESULT_OK, data)
    }

    private fun checkNumbersOfHints() {
        if (numberOfHints >= 3) {
            showAnswerButton.isEnabled = false
            numberOfHintsTextView.text = "There are still hints left: 0"
        } else {
            numberOfHintsTextView.text = "There are still hints left: ${3 - numberOfHints}"
        }
    }

    companion object {
        fun newIntent(
            packageContext: Context,
            answerIsTrue: Boolean,
            currentIndex: Int,
            numberOfHints: Int
        ): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
                putExtra(EXTRA_CURRENT_INDEX, currentIndex)
                putExtra(EXTRA_NUMBER_OF_HINTS, numberOfHints)
            }
        }
    }
}
