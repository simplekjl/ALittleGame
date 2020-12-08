package com.simplekjl.fallingwords.data

import com.simplekjl.fallingwords.data.model.Word

/**
 * Repository pattern for extension in the future
 */
interface TranslationRepository {
    fun getSetOfWords(): List<Word>
    fun getAllWords(): List<Word>
    fun getWord(search : String): Word
}
