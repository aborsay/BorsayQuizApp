package xyz.borsay.borsayquizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class FinishedQuiz : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finished_quiz)

        val tvUserName:TextView = findViewById(R.id.tvUserName)
        val tvUserScore: TextView = findViewById(R.id.tvUserScore)

        val userScore = "Your Score is " +
                "${intent.getStringExtra(Constants.CORRECT_ANSWERS)} " +
                "out of ${intent.getStringExtra(Constants.TOTAL_QUESTIONS)}"
        val userName = intent.getStringExtra(Constants.USER_NAME).toString()
        tvUserScore.text = userScore
        tvUserName.text = userName

        val exitApp: Button = findViewById(R.id.exitApp)
        exitApp.setOnClickListener {
            this.finishAffinity()

        }


    }
}