package com.simplekjl.fallingwords.domain.mappers

import com.simplekjl.fallingwords.ui.model.WordViewEntity
import org.junit.Test
import kotlin.random.Random

internal class WordRawToUiMapperTest {

    @Test
    fun mapToUi() {

    }

    @Test
    fun mapListRawToUi() {
    }

    private fun createListOfRandomWords(): List<WordViewEntity> {
        val list = mutableListOf<WordViewEntity>()
        for (i in 0..16) {
            list.add(WordViewEntity(Random.nextInt().toString(), ""))
        }
        return list
    }
}
