package com.simplekjl.fallingwords.domain.mappers

import com.simplekjl.fallingwords.data.model.Word
import com.simplekjl.fallingwords.ui.model.WordViewEntity

/**
 * In future work we can add safe mapping as part of the development in case we are unsure about the
 * consistency of the source of data
 */
class WordRawToUiMapper {
    fun mapToUi(word: Word): WordViewEntity {
        return WordViewEntity(word.value, word.translation)
    }

    fun mapListRawToUi(list: List<Word>): List<WordViewEntity> {
        val uiList = mutableListOf<WordViewEntity>()
        list.forEach { uiList.add(mapToUi(it)) }
        return uiList
    }
}
