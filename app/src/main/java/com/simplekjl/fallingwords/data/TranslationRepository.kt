package com.simplekjl.fallingwords.data

import com.simplekjl.fallingwords.ui.model.WordViewEntity

/**
 * Repository pattern for extension in the future
 */
interface TranslationRepository {
    fun getSetOfWords(): List<WordViewEntity>
    fun getAllWords(): List<WordViewEntity>
    fun getWord(search: String): WordViewEntity
}
