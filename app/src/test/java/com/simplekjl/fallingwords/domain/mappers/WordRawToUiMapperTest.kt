package com.simplekjl.fallingwords.domain.mappers

import com.google.common.truth.Truth.assertThat
import com.simplekjl.fallingwords.data.model.Word
import com.simplekjl.fallingwords.ui.model.WordViewEntity
import org.junit.Test

internal class WordRawToUiMapperTest {

    private val mapperToTest = WordRawToUiMapper()

    @Test
    fun mapToUi() {
        val list = listOf(Word("word", "one"), Word("wordTwo", "two"))
        val result = mapperToTest.mapToUi(list[1])
        assertThat(result).isInstanceOf(WordViewEntity::class.java)
    }

    @Test
    fun mapListRawToUi() {
        val list = listOf(Word("word", "one"), Word("wordTwo", "two"))
        val result = mapperToTest.mapListRawToUi(list)
        assertThat(result[0]).isInstanceOf(WordViewEntity::class.java)
    }


}
