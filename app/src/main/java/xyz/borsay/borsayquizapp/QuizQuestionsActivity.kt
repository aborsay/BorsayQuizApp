package xyz.borsay.borsayquizapp

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import org.w3c.dom.Text
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener{
    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mCorrectPosition: Int = 0
    private var mGuessedCorrect: Boolean = false
    private var mSelectedOptionPosition: Int = 0
    private  lateinit var tvOptionOne : TextView
    private lateinit var tvOptionTwo  : TextView
    private lateinit var tvOptionThree : TextView
    private lateinit var tvOptionFour : TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var  tvProgress: TextView
    private lateinit var ivCurrentImage:  ImageView
    private lateinit var tvCurrentQuestion: TextView
    private lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mQuestionsList = Constants.getQuestions()
        mQuestionsList!!.shuffle()


        tvCurrentQuestion = findViewById(R.id.tvCurrentQuestion)
        tvOptionFour = findViewById(R.id.tvOptionFour)
        tvOptionFour.setOnClickListener(this)
        tvOptionThree = findViewById(R.id.tvOptionThree)
        tvOptionThree.setOnClickListener(this)
        tvOptionTwo = findViewById(R.id.tvOptionTwo)
        tvOptionTwo.setOnClickListener(this)
        tvOptionOne = findViewById(R.id.tvOptionOne)
        tvOptionOne.setOnClickListener(this)
        tvProgress = findViewById(R.id.tvProgress)
        ivCurrentImage = findViewById(R.id.ivCurrentImage)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnSubmit.setOnClickListener(this)
        btnSubmit.isEnabled = false
        progressBar = findViewById(R.id.progressBar)
        progressBar.progress = 1
        progressBar.max = mQuestionsList!!.size

        //  questionsList.forEach(){
        setQuestion()
          //  break;
        //}


    }


    private fun setQuestion(  ){
        val question: Question = mQuestionsList!![mCurrentPosition-1]

        val options = arrayListOf(
            Pair(1, question.optionOne),
            Pair(2, question.optionTwo),
            Pair(3, question.optionThree),
            Pair(4, question.optionFour) )

        options.shuffle()


        mCorrectPosition = question.correctAnswer

        tvOptionOne.text  = options[0].second
        tvOptionOne.tag = options[0].first

        tvOptionTwo.text  = options[1].second
        tvOptionTwo.tag = options[1].first
        tvOptionThree.text  = options[2].second
        tvOptionThree.tag = options[2].first
        tvOptionFour.text  = options[3].second
        tvOptionFour.tag = options[3].first
        progressBar.progress = mCurrentPosition
        tvProgress.text = "$mCurrentPosition/${progressBar.max}"
        ivCurrentImage.setImageResource(question.image)
        if (question != null) {
            tvCurrentQuestion.text = question.question
        }

        if(mCurrentPosition == mQuestionsList!!.size)
            btnSubmit.text = "FINISH"
        else
            btnSubmit.text = "SUBMIT"

    }



    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        tvOptionOne?.let{
            options.add(0, it)
        }
        tvOptionTwo?.let{
            options.add(1, it)
        }
        tvOptionThree?.let{
            options.add(2, it)
        }
        tvOptionFour?.let{
            options.add(3, it)
        }

        for(option in options){
            if(mSelectedOptionPosition==0)
                option.setTextColor(getColor(R.color.tvQuestionNotSelected))
            else
                option.setTextColor(getColor(R.color.light_black))

            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_box
            )

        }
    }


    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int){

        mSelectedOptionPosition = selectedOptionNum
        defaultOptionsView()

        tv.setTextColor(getColor(R.color.black))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_box)



    }
    override fun onClick(view: View?) {
        btnSubmit.isEnabled = true
        when(view?.id){
            R.id.tvOptionOne ->{
                if(!mGuessedCorrect)
                    selectedOptionView(tvOptionOne,1)
            }
            R.id.tvOptionTwo ->{
                if(!mGuessedCorrect)
                    selectedOptionView(tvOptionTwo,2)
            }
            R.id.tvOptionThree ->{
                if(!mGuessedCorrect)
                    selectedOptionView(tvOptionThree,3)
            }
            R.id.tvOptionFour ->{
                if(!mGuessedCorrect)
                    selectedOptionView(tvOptionFour,4)
            }
            R.id.btnSubmit ->{
                if(mSelectedOptionPosition == 0 ){
                    mCurrentPosition++

                    when{
                        mCurrentPosition <= mQuestionsList!!.size ->{
                            mSelectedOptionPosition=0
                            mGuessedCorrect = false
                            defaultOptionsView()
                            setQuestion()
                        }
                    }
                }else{
                    mGuessedCorrect = false
                    when(mSelectedOptionPosition){
                        1 ->{
                            if(tvOptionOne.tag.toString().toInt() == mCorrectPosition )
                                mGuessedCorrect = true
                        }
                        2 ->{
                            if(tvOptionTwo.tag.toString().toInt() == mCorrectPosition )
                                mGuessedCorrect = true
                        }
                        3 ->{
                            if(tvOptionThree.tag.toString().toInt() == mCorrectPosition )
                                mGuessedCorrect = true
                        }
                        4-> {
                            if(tvOptionFour.tag.toString().toInt() == mCorrectPosition )
                                mGuessedCorrect = true
                        }
                    }
                    answerView(mSelectedOptionPosition)
                }
            }
        }
    }

    private fun answerView(answer: Int){
        when( answer){
            1 ->{
                tvOptionOne.background = ContextCompat.getDrawable(
                    this,
                    if(!mGuessedCorrect) R.drawable.incorrect_option_box
                    else R.drawable.correct_option_box )
            }
            2 ->{
                tvOptionTwo.background = ContextCompat.getDrawable(
                    this,
                    if(!mGuessedCorrect) R.drawable.incorrect_option_box
                    else R.drawable.correct_option_box )
            }
            3 ->{
                tvOptionThree.background = ContextCompat.getDrawable(
                    this,
                    if(!mGuessedCorrect) R.drawable.incorrect_option_box
                    else R.drawable.correct_option_box )
            }
            4 ->{
                tvOptionFour.background = ContextCompat.getDrawable(
                    this,
                    if(!mGuessedCorrect) R.drawable.incorrect_option_box
                    else R.drawable.correct_option_box )
            }
        }
        if(mGuessedCorrect){
            mSelectedOptionPosition=0
            if(mCurrentPosition < mQuestionsList!!.size)
                btnSubmit.text = "Correct, go to Next Question!"
            else{
                btnSubmit.text = "Correct, You have finished!"
            }
        }else{
            btnSubmit.text = "Incorrect, please try again!"
        }

    }
}