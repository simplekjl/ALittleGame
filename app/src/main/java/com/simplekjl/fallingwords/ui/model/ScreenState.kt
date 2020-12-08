package com.simplekjl.fallingwords.ui.model

import kotlin.random.Random

sealed class ScreenState

/**
 * contract for our UI
 */
data class MainScreenView(
    var remainingWords: Int = 15,
    var score: Int = 0,
    var word: WordViewEntity? = null,
    var isCompleted: Boolean = false,
    val showRealWord: Boolean = Random.nextBoolean(),
    val hideControls: Boolean = false,
    val isRestartBtnVisible: Boolean = false
) : ScreenState()


