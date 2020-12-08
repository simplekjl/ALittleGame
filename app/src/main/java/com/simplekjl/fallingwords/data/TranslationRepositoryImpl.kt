package com.simplekjl.fallingwords.data

import com.simplekjl.fallingwords.data.model.Word
import kotlin.random.Random

class TranslationRepositoryImpl(private val words: List<Word>) : TranslationRepository {
    /**
     * return randomly a set of 15 words to play the game
     */
    override fun getSetOfWords(): List<Word> {
        val start = Random.nextInt(0, words.size - 15)
        return words.subList(start, words.size)
    }

    /**
     * returns all the words at once
     */
    override fun getAllWords(): List<Word> {
        return words
    }

    /**
     * get a specific word
     * @throws NoSuchElementException
     */
    override fun getWord(search: String): Word {
        // can be improved with a map but this depends on the design of more models
        return words.first { it.value == search }

    }

}
