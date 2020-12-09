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

    fun shuffleWords() {
        if (remainingWords > 0) {
            remainingWords -= 1
            val randomPair = getRandomValues()
            val word: WordViewEntity = if (randomPair.second) {
                wordList[randomPair.first]
            } else {
                createMixedWord(randomPair.first)
            }
            _screenState.value = MainScreenView(
                remainingWords = remainingWords,
                score = score,
                word = word,
                showRealWord = randomPair.second
            )
        } else {
            _screenState.value =
                MainScreenView(remainingWords = 0, score = score, isCompleted = true)
        }
    }

    private fun createMixedWord(position: Int): WordViewEntity {
        return WordViewEntity(wordList[position].word, wordList[position + 1].translation)
    }

    /**
     * handy method to get a random int and a boolean to play the game
     */
    private fun getRandomValues(): Pair<Int, Boolean> {
        val random = Random.nextInt(0, 14)
        val showReal = Random.nextBoolean()
        return Pair(random, showReal)
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
        shuffleWords() // will produce a new state
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
        shuffleWords() // will produce a new state
    }

    /**
     * When the user misses the word or is let go on purpose
     */
    fun checkPassed(wasRealWord: Boolean) {
        if (wasRealWord) {
            score += 1
        }
        shuffleWords() // will produce a new state
    }

    fun restart() {
        score = 0
        remainingWords = 15
        shuffleWords()
    }
}
