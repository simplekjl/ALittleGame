package com.simplekjl.fallingwords.data

import com.simplekjl.fallingwords.data.model.Word
import com.simplekjl.fallingwords.domain.mappers.WordRawToUiMapper
import com.simplekjl.fallingwords.ui.model.WordViewEntity
import kotlin.random.Random

class TranslationRepositoryImpl(
    private val words: List<Word>,
    private val mapper: WordRawToUiMapper
) : TranslationRepository {
    /**
     * return randomly a set of 15 words to play the game
     */
    override fun getSetOfWords(): List<WordViewEntity> {
        val start = Random.nextInt(0, words.size - 15)
        return mapper.mapListRawToUi(words.subList(start, words.size))
    }

    /**
     * returns all the words at once
     */
    override fun getAllWords(): List<WordViewEntity> {
        return mapper.mapListRawToUi(words)
    }

    /**
     * get a specific word
     * @throws NoSuchElementException
     */
    override fun getWord(search: String): WordViewEntity {
        // can be improved with a map but this depends on the design of more models
        return mapper.mapToUi(words.first { it.value == search })

    }

}
