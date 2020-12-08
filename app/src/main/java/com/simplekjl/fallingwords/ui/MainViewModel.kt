package com.simplekjl.fallingwords.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.simplekjl.fallingwords.data.TranslationRepository
import com.simplekjl.fallingwords.ui.model.MainScreenView
import com.simplekjl.fallingwords.ui.model.ScreenState
import com.simplekjl.fallingwords.ui.model.WordViewEntity
import kotlin.random.Random

class MainViewModel(private val repository: TranslationRepository) : ViewModel() {

    // reference for the list of words
    private val wordList: List<WordViewEntity> by lazy { repository.getSetOfWords() }
    private var score: Int = 0
    private var remainingWords = 15


    private val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState>
        get() = _screenState

    fun startGame() {
        if (remainingWords > 0) {
            remainingWords -= 1
            val random = Random.nextInt(0, 14)
            val showReal = Random.nextBoolean()
            val word: WordViewEntity = if (showReal) {
                wordList[random]
            } else {
                val pos1 = Random.nextInt(0, 14)
                val pos2 = Random.nextInt(0, 14)
                val word1 = wordList[pos1]
                val word2 = wordList[pos2]
                WordViewEntity(word1.word, word2.translation)
            }
            _screenState.value = MainScreenView(
                remainingWords = remainingWords,
                score = score,
                word = word,
                showRealWord = showReal
            )
        } else {
            _screenState.value =
                MainScreenView(remainingWords = 0, score = score, isCompleted = true)
        }
    }

    /**
     * When the user assumes the translation is false
     * @param wasRealWord weather the word in the screen was real or not
     * @param userSelection  false in this case
     */
    fun checkFalse(wasRealWord: Boolean, userSelection: Boolean) {
        if (!wasRealWord && !userSelection) {
            score += 1
        }
    }

    /**
     * When the user assumes the translation is valid
     * @param wasRealWord weather the word in the screen was real or not
     * @param userSelection   true in this case
     */
    fun checkValid(wasRealWord: Boolean, userSelection: Boolean) {
        if (wasRealWord && userSelection) {
            score += 1
        }
    }

    /**
     * When the user misses the word or is let go on purpose
     */
    fun checkPassed(wasRealWord: Boolean) {
        if (wasRealWord) {
            score += 1
        }
    }

    fun restart() {
        score = 0
        remainingWords = 15
        startGame()
    }
}
