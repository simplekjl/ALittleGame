package com.simplekjl.fallingwords

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import com.simplekjl.fallingwords.ui.MainViewModel
import com.simplekjl.fallingwords.ui.model.MainScreenView
import com.simplekjl.fallingwords.ui.model.WordViewEntity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_word.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private var screenHeight = 0f
    private var screenWidth = 0f

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startCountDown()
        mainViewModel.screenState.observe(this, { screenState ->
            when (screenState) {
                is MainScreenView -> {
                    bindView(screenState)
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun bindView(state: MainScreenView) {
        if (!state.isCompleted) {
            onStartAnimation(state.word!!, state.showRealWord)
            counter_words.text = state.remainingWords.toString()
            score_count.text = state.score.toString()
        } else {
            
            startCounter.isVisible = true
            startCounter.text = "The final score is ${state.score} from 15 words"
        }
    }

    private fun startCountDown() {
        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val number = millisUntilFinished / 1000
                startCounter.text = number.toString()
                if (number.toInt() == 0)
                    startCounter.text = getString(R.string.start_label)
            }

            override fun onFinish() {
                mainViewModel.startGame()
                startCounter.isVisible = false
                card_word_guess.isVisible = true
            }
        }.start()

    }

    private fun onStartAnimation(
        word: WordViewEntity,
        showReal: Boolean
    ) {
        // assign the word before starting to get the full width
        word_un.text = word.word
        var userClicked = false
        val limitX = screenWidth - card_word_uns.width
        val translateToX = Random.nextInt(0, limitX.toInt()).toFloat()
        val translateToY = screenHeight - word_trans.x // can' make it disappear  before
        val valueAnimator = ValueAnimator.ofFloat(0f, translateToY)

        card_word_uns.translationX = translateToX // move to new location on x

        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            card_word_uns.translationY = value
        }
        valueAnimator.doOnStart {
            card_word_uns.isVisible = true
            word_trans.text = word.translation
        }
        valueAnimator.doOnEnd {
            // line need it in case we cancel the animation
            if (!userClicked)
                mainViewModel.startGame()
        }

        valueAnimator.duration = DEFAULT_ANIMATION_DURATION
        valueAnimator.start()

        wrong_btn.setOnClickListener {
            mainViewModel.checkFalse(
                wasRealWord = showReal, userSelection = false
            )
            userClicked = true
            valueAnimator.cancel()

            mainViewModel.startGame()

        }
        positive_btn.setOnClickListener {
            mainViewModel.checkValid(
                wasRealWord = showReal, userSelection = true
            )
            userClicked = true
            valueAnimator.cancel()
            mainViewModel.startGame()
        }
    }

    override fun onResume() {
        super.onResume()
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenHeight = displayMetrics.heightPixels.toFloat()
        screenWidth = displayMetrics.widthPixels.toFloat()
    }

    companion object {
        const val DEFAULT_ANIMATION_DURATION = 3500L
    }
}
